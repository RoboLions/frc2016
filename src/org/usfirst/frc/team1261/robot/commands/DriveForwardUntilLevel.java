package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.StraightDrivingDriveTrainPIDController;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardUntilLevel extends Command {

	public static final double DEFAULT_POWER = 0.7;
	public static final double PITCH_THRESHOLD = 7.5;
	public static final double ROLL_THRESHOLD = 7.5;
	public static final double INITIAL_IGNORE_DURATION = 1.0;
	public static final double DEFAULT_MINIMUM_LEVEL_DURATION = 0.4;

	private boolean onDefense = false;
	private boolean crossedDefense = false;
	private long timeAtDefenseCross = 0;
	private double power = DEFAULT_POWER;
	private double minimumLevelDurationMicroseconds = DEFAULT_MINIMUM_LEVEL_DURATION * 1000000;

	public DriveForwardUntilLevel() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	public DriveForwardUntilLevel(double power) {
		this();
		this.power = power;
	}

	public DriveForwardUntilLevel(double power, double minimumLevelDuration) {
		this();
		this.power = power;
		this.minimumLevelDurationMicroseconds = minimumLevelDuration * 1000000;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.zeroAngle();
		Robot.driveTrain.setPIDController(new StraightDrivingDriveTrainPIDController(power));
		Robot.driveTrain.setSetpoint(0.0);
		onDefense = false;
		crossedDefense = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (timeSinceInitialized() < INITIAL_IGNORE_DURATION) {
			return;
		}
		double pitch = Robot.driveTrain.getNavX().getPitch();
		double roll = Robot.driveTrain.getNavX().getRoll();
		boolean isLevel = (Math.abs(pitch) <= PITCH_THRESHOLD) && (Math.abs(roll) <= ROLL_THRESHOLD);
		if (!crossedDefense) {
			if (!isLevel && !onDefense) {
				// We have just begun to cross the defense.
				System.out.println("DriveForwardUntilLevel: We have just begun to cross the defense.");
				onDefense = true;
			} else if (isLevel && onDefense) {
				// We have just finished crossing the defense.
				System.out.println("DriveForwardUntilLevel: We have just finished crossing the defense.");
				onDefense = false;
				crossedDefense = true;
				timeAtDefenseCross = Utility.getFPGATime();
			}
		} else if (!isLevel) {
			// We didn't actually cross the defense.
			System.out.println("DriveForwardUntilLevel: We didn't actually cross the defense.");
			onDefense = true;
			crossedDefense = false;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		boolean finished = (crossedDefense && Utility.getFPGATime() - timeAtDefenseCross >= minimumLevelDurationMicroseconds);
		if (finished) {
			System.out.println("DriveForwardUntilLevel: We have finished normally.");
		}
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
		double pitch = Robot.driveTrain.getNavX().getPitch();
		double roll = Robot.driveTrain.getNavX().getRoll();
		double timeSinceDefenseCross = Utility.getFPGATime() - timeAtDefenseCross;
		boolean isLevel = (Math.abs(pitch) <= PITCH_THRESHOLD) && (Math.abs(roll) <= ROLL_THRESHOLD);
		System.out.println("DriveForwardUntilLevel: Ending.");
		System.out.println("DriveForwardUntilLevel: System status at end: pitch = " + pitch);
		System.out.println("DriveForwardUntilLevel: System status at end: roll = " + roll);
		System.out.println("DriveForwardUntilLevel: System status at end: isLevel = " + isLevel);
		System.out.println("DriveForwardUntilLevel: System status at end: onDefense = " + onDefense);
		System.out.println("DriveForwardUntilLevel: System status at end: crossedDefense = " + crossedDefense);
		System.out.println("DriveForwardUntilLevel: System status at end: timeSinceDefenseCross = " + timeSinceDefenseCross);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
		System.out.println("DriveForwardUntilLevel: We have been interrupted.");
	}
}
