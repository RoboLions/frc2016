package org.usfirst.frc.team1261.robot.commands;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 */
public class TrackElevate extends AutoElevate {

	public static final Joystick JOYSTICK = JoystickShooterArm.JOYSTICK;
	public static final int CANCEL_AXIS = JoystickShooterArm.JOYSTICK_AXIS;
	public static final double CANCEL_THRESHOLD = 0.0;

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// Returns true when CANCEL_AXIS_A or CANCEL_AXIS_B are moved beyond the
		// CANCEL_THRESHOLD.
		return Math.abs(JOYSTICK.getRawAxis(CANCEL_AXIS)) > CANCEL_THRESHOLD;
	}
}
