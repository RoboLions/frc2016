package org.usfirst.frc.team1261.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftArmReleaseTop extends Command {

	public static final double POSITION = 0.0;

	public LiftArmReleaseTop() {
		// No requires(Robot.liftArm) statement because this command can run
		// simultaneously with other LiftArm commands.
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//Robot.liftArm.setTopServoPosition(POSITION);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
