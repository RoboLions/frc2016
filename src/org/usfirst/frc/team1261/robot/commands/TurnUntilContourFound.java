package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.RaspberryPi;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnUntilContourFound extends Command {

	public static final double POWER = 0.25;
	public static final double MAXIMUM_ANGLE = 60.0;
	public static final double MINIMUM_CONTOUR_DURATION = 0.25;

	private static final double MINIMUM_CONTOUR_DURATION_MICROSECONDS = MINIMUM_CONTOUR_DURATION * 1000000;
	private double power;
	private boolean contourFound = false;
	private long timeWhenContourFound = 0;

	public enum Direction {
		LEFT, RIGHT, FROM_LEFT, FROM_RIGHT, NONE
	}

	public TurnUntilContourFound(Direction direction) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		requires(Robot.visionTrackingLED);
		switch (direction) {
		case LEFT:
		case FROM_RIGHT:
			power = -POWER;
			break;
		default:
			power = POWER;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.stop();
		Robot.driveTrain.zeroYaw();
		Robot.visionTrackingLED.disable();
		contourFound = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double yaw = Robot.driveTrain.getYaw();
		if (Math.abs(yaw) >= MAXIMUM_ANGLE) {
			power = Math.abs(power) * -Math.signum(yaw);
		}
		Robot.driveTrain.turn(power);
		Robot.visionTrackingLED.enable();
		if (RaspberryPi.isContourFound()) {
    		if (!contourFound) {
    			timeWhenContourFound = Utility.getFPGATime();
    		}
    		contourFound = true;
    	} else {
    		contourFound = false;
    	}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return contourFound && Utility.getFPGATime() - timeWhenContourFound >= MINIMUM_CONTOUR_DURATION_MICROSECONDS;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
		Robot.visionTrackingLED.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
