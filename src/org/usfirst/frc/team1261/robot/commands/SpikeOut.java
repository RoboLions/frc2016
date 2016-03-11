package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpikeOut extends Command {

	public static final Value SPIKE_VALUE = Value.kReverse;

	private static int shotCount = 0;
	
    public SpikeOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.spikePuncher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.spikePuncher.setSpikeValue(Value.kOff);
    	System.out.println("Spike fired! This was shot #" + (++shotCount) + ". Shooter arm encoder was at: " + Robot.shooterArm.getAngle());
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.spikePuncher.setSpikeValue(SPIKE_VALUE);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.spikePuncher.setSpikeValue(Value.kOff);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
