package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousCommand extends Command {
	
	static final double TOLERANCE = 10.0;
	static final double SETPOINT = 500.0;

    public AutonomousCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetDistanceTraveled();
    	Robot.driveTrain.setPIDController(DriveTrain.DriveTrainPIDController.DISTANCE);
    	Robot.driveTrain.getPIDController().setAbsoluteTolerance(TOLERANCE);
    	Robot.driveTrain.getPIDController().setSetpoint(SETPOINT);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // return Robot.driveTrain.getPIDController().onTarget();
    	
    	// Workaround for https://usfirst.collab.net/sf/tracker/do/viewArtifact/projects.wpilib/tracker.4_defects/artf4812
    	return (Math.abs(Robot.driveTrain.getPIDController().getError()) < TOLERANCE);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.disablePIDController();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
