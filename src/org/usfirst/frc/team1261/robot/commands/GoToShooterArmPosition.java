package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GoToShooterArmPosition extends Command {

	private double position;
	
    public GoToShooterArmPosition(double position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterArm);
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooterArm.setPIDController(ShooterArm.ShooterArmPIDController.ANGLE);
    	Robot.shooterArm.setSetpoint(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.shooterArm.onTarget();
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
