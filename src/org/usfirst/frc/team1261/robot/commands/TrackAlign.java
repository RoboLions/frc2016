package org.usfirst.frc.team1261.robot.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class TrackAlign extends AutoAlign {

	public static final Joystick JOYSTICK = JoystickDrive.JOYSTICK;
	public static final int CANCEL_AXIS_A = JoystickDrive.THROTTLE_AXIS;
	public static final int CANCEL_AXIS_B = JoystickDrive.ROTATE_AXIS;
	public static final double CANCEL_THRESHOLD = 0.25;

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// Returns true when CANCEL_AXIS_A or CANCEL_AXIS_B are moved beyond the
		// CANCEL_THRESHOLD.
		return (Math.abs(JOYSTICK.getRawAxis(CANCEL_AXIS_A)) > CANCEL_THRESHOLD)
				|| (Math.abs(JOYSTICK.getRawAxis(CANCEL_AXIS_B)) > CANCEL_THRESHOLD);
	}
}
