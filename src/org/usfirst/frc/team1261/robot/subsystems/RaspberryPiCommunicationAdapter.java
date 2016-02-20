package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * A class that retrieves vision tracking data from the Raspberry Pi.
 */
class RaspberryPiCommunicationAdapter {

	static class NoContoursFoundException extends Exception {
		/**
		 * Indicates that the vision processor was unable to identify any
		 * contours representing goals.
		 */
		private static final long serialVersionUID = 8913380034267672587L;

	}

	public static final NetworkTable CONTOUR_TABLE = NetworkTable.getTable("RaspberryPi");

	public static final double TOLERANCE_FACTOR = 0.05;

	public static final double DEFAULT_Y_IMAGE_SIZE = 240;
	public static final double DEFAULT_X_IMAGE_SIZE = 320;
	public static final double Y_IMAGE_SIZE = CONTOUR_TABLE.getNumber("imageSizeY", DEFAULT_Y_IMAGE_SIZE);
	public static final double X_IMAGE_SIZE = CONTOUR_TABLE.getNumber("imageSizeX", DEFAULT_X_IMAGE_SIZE);
	public static final double Y_AXIS_TARGET = Y_IMAGE_SIZE / 2;
	public static final double X_AXIS_TARGET = X_IMAGE_SIZE / 2;
	public static final double Y_AXIS_TOLERANCE = Y_IMAGE_SIZE * TOLERANCE_FACTOR;
	public static final double X_AXIS_TOLERANCE = X_IMAGE_SIZE * TOLERANCE_FACTOR;

	/**
	 * Gets the y-axis offset of the center of the goal from where the shooter
	 * arm is pointing.
	 * 
	 * @return The y-axis offset of the center of the goal in pixels.
	 * @throws NoContoursFoundException
	 *             If no contours representing goals can be identified.
	 */
	public static double getTargetYOffset() throws NoContoursFoundException {
		return Y_AXIS_TARGET - getY();
	}

	/**
	 * Gets the x-axis offset of the center of the goal from where the shooter
	 * arm is pointing.
	 * 
	 * @return The x-axis offset of the center of the goal in pixels.
	 * @throws NoContoursFoundException
	 *             If no contours representing goals can be identified.
	 */
	public static double getTargetXOffset() throws NoContoursFoundException {
		return X_AXIS_TARGET - getX();
	}

	/**
	 * Gets the y-axis position of the center of the goal.
	 * 
	 * @return The y-axis position of the center of the goal in pixels.
	 * @throws NoContoursFoundException
	 *             If no contours representing goals can be identified.
	 */
	public static double getY() throws NoContoursFoundException {
		boolean isContourFound = CONTOUR_TABLE.getBoolean("contourFound", false);
		if (!isContourFound) {
			throw new NoContoursFoundException();
		} else {
			return CONTOUR_TABLE.getNumber("y", 0.0);
		}
	}

	/**
	 * Gets the x-axis position of the center of the goal.
	 * 
	 * @return The x-axis position of the center of the goal in pixels.
	 * @throws NoContoursFoundException
	 *             If no contours representing goals can be identified.
	 */
	public static double getX() throws NoContoursFoundException {
		boolean isContourFound = CONTOUR_TABLE.getBoolean("contourFound", false);
		if (!isContourFound) {
			throw new NoContoursFoundException();
		} else {
			return CONTOUR_TABLE.getNumber("x", 0.0);
		}
	}

	/**
	 * Gets the area of the contour representing the goal.
	 * 
	 * @return The area of the contour representing the goal in pixels.
	 * @throws NoContoursFoundException
	 *             If no contours representing goals can be identified.
	 */
	public static double getArea() throws NoContoursFoundException {
		boolean isContourFound = CONTOUR_TABLE.getBoolean("contourFound", false);
		if (!isContourFound) {
			throw new NoContoursFoundException();
		} else {
			return CONTOUR_TABLE.getNumber("area", 0.0);
		}
	}
}
