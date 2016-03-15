package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.StraightDrivingDriveTrainPIDController;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {

	public static final double POWER = 0.70;

    public DriveForward(double duration) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	setTimeout(duration);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.zeroAngle();
    	Robot.driveTrain.setPIDController(new StraightDrivingDriveTrainPIDController(POWER));
    	Robot.driveTrain.setSetpoint(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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
