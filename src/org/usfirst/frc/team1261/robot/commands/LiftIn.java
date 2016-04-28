package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftIn extends Command {

	public static final double POWER = 1.0;

    public LiftIn() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.liftArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.liftArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.liftArm.setWinchMotorPower(POWER);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.liftArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
