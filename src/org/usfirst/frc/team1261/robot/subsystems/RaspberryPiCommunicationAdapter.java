package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
class RaspberryPiCommunicationAdapter {

	static class NoContoursFoundException extends Exception {

		/**
		 * 
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

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public static double getTargetYOffset() throws NoContoursFoundException {
		return Y_AXIS_TARGET - getY();
	}

	public static double getTargetXOffset() throws NoContoursFoundException {
		return X_AXIS_TARGET - getX();
	}

	public static double getY() throws NoContoursFoundException {
		boolean isContourFound = CONTOUR_TABLE.getBoolean("contourFound", false);
		if (!isContourFound) {
			throw new NoContoursFoundException();
		} else {
			return CONTOUR_TABLE.getNumber("y", 0.0);
		}
	}

	public static double getX() throws NoContoursFoundException {
		boolean isContourFound = CONTOUR_TABLE.getBoolean("contourFound", false);
		if (!isContourFound) {
			throw new NoContoursFoundException();
		} else {
			return CONTOUR_TABLE.getNumber("x", 0.0);
		}
	}

	public static double getArea() throws NoContoursFoundException {
		boolean isContourFound = CONTOUR_TABLE.getBoolean("contourFound", false);
		if (!isContourFound) {
			throw new NoContoursFoundException();
		} else {
			return CONTOUR_TABLE.getNumber("area", 0.0);
		}
	}
}
