package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

/**
 *
 */
public class AutoIntakeShooter extends FlywheelIn {

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.flywheel.isPhotoGateBlocked();
	}
}
