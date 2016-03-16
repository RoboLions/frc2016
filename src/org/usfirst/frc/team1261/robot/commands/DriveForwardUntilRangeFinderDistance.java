package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardUntilRangeFinderDistance extends Command {

	public static final double DEFAULT_POWER = 0.7;

	private double power = DEFAULT_POWER;
	private double distance;

    public DriveForwardUntilRangeFinderDistance(double distanceFromWall) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	distance = distanceFromWall;
    }

    public DriveForwardUntilRangeFinderDistance(double distanceFromWall, double power) {
    	this(distanceFromWall);
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.drive(power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        try {
        	return (Robot.driveTrain.getRangeFinderDistance() < distance);
        } catch (DriveTrain.RangeFinderNoSignalException e) {
        	return false;
        }
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
