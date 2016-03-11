package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardUntilLevel extends Command {

	public static final double POWER = 0.5;

	public static final double PITCH_THRESHOLD = 5.0;
	public static final double ROLL_THRESHOLD = 5.0;

	private boolean onDefense = false;
	private boolean crossedDefense = false;

	public DriveForwardUntilLevel() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.disablePIDController();
		onDefense = false;
		crossedDefense = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveTrain.drive(POWER);
		if (!crossedDefense) {
			double pitch = Robot.driveTrain.getNavX().getPitch();
			double roll = Robot.driveTrain.getNavX().getRoll();
			boolean isLevel = (Math.abs(pitch) <= PITCH_THRESHOLD) || (Math.abs(roll) <= ROLL_THRESHOLD);
			if (!isLevel && !onDefense) {
				// We have just begun to cross the defense.
				onDefense = true;
			} else if (isLevel && onDefense) {
				// We have just finished crossing the defense.
				onDefense = false;
				crossedDefense = true;
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return crossedDefense;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
