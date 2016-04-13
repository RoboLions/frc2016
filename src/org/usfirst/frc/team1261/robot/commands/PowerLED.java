package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PowerLED extends Command implements Finishable {

	private boolean finished = false;

    public PowerLED() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.visionTrackingLED);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.visionTrackingLED.disable();
    	finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.visionTrackingLED.enable();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.visionTrackingLED.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
