package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterArmSetpointUp extends Command {

	public static final double TOLERANCE = 1.0;
	// The reason for this tolerance value is so that if the button is pressed
	// once, but the intake arm does not completely reach the setpoint (e.g. off
	// by 1 encoder unit), and the driver presses the setpoint button again, the
	// intake arm moves to the next setpoint rather than the one that it is a
	// little off from.

	private double setpoint;
	private boolean hasSetpoint = false;

	public ShooterArmSetpointUp() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.shooterArm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooterArm.stop();

		// Initialize values
		double currentPosition = Robot.shooterArm.getAngle();
		hasSetpoint = false;

		// Find nearest higher setpoint
		for (int setpointIndex = 0; setpointIndex < ShooterArm.SETPOINTS.length; setpointIndex++) {
			if (ShooterArm.SETPOINTS[setpointIndex] > currentPosition + TOLERANCE) {
				setpoint = ShooterArm.SETPOINTS[setpointIndex];
				hasSetpoint = true;
				break;
			}
		}

		// Set setpoint if it exists
		if (hasSetpoint) {
			Robot.shooterArm.setPIDController(ShooterArm.ShooterArmPIDController.ANGLE);
			Robot.shooterArm.setSetpoint(setpoint);
		} else {
			cancel();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (hasSetpoint) {
			return Robot.shooterArm.onTarget();
		} else {
			return true;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooterArm.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
