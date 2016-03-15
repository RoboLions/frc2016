package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeArmToLowerLimitSwitch extends Command {

    public IntakeArmToLowerLimitSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intakeArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.intakeArm.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intakeArm.setIntakeArmMotorPower(-.8);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intakeArm.isLowerLimitSwitchHit();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intakeArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
