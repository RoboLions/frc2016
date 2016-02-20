package org.usfirst.frc.team1261.robot.subsystems;

public class RaspberryPiCommunicationAdapter {
	
	public static final double Y_AXIS_TOLERANCE = 0.0;
	
	public class NoContoursFoundException extends Exception {
		private static final long serialVersionUID = 0L;
	}

	private static final UnsupportedOperationException NOT_IMPLEMENTED_EXCEPTION = new UnsupportedOperationException("Not implemented yet");
	
	public static double getTargetYOffset() throws NoContoursFoundException {
		throw NOT_IMPLEMENTED_EXCEPTION;
	}
}
