package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1261.robot.subsystems.StraightDrivingDriveTrainPIDController;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardUntilSideRangeFinderDistance extends Command {

	public static final double DEFAULT_POWER = 0.7;
	public static final double DEFAULT_MINIMUM_FINISHED_DURATION = 0.4;
	private static final double IS_ON_DEFENSE_RANGE = 500;

	private boolean crossingDefense = false;
	private boolean crossedDefense = false;
	private long timeAtDefenseCross = 0;
	private double power = DEFAULT_POWER;
	private double minimumFinishedDurationMicroseconds = DEFAULT_MINIMUM_FINISHED_DURATION * 1000000;

	public DriveForwardUntilSideRangeFinderDistance() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	public DriveForwardUntilSideRangeFinderDistance(double power) {
		this();
		this.power = power;
	}

	public DriveForwardUntilSideRangeFinderDistance(double power, double minimumFinishedDuration) {
		this();
		this.power = power;
		this.minimumFinishedDurationMicroseconds = minimumFinishedDuration * 1000000;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.setPIDController(new StraightDrivingDriveTrainPIDController(power));
		Robot.driveTrain.setSetpoint(0.0);
		crossingDefense = false;
		crossedDefense = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		boolean isNotOnDefense;
		
		try {
			isNotOnDefense = Robot.driveTrain.getSideRangeFinderDistance() > IS_ON_DEFENSE_RANGE;
		} catch (DriveTrain.RangeFinderNoSignalException e) {
			isNotOnDefense = true;
		}
		
		if (!crossedDefense) {
			if (!isNotOnDefense && !crossingDefense) {
				// We have just begun to cross the defense.
				System.out.println("DriveForwardUntilLevel: We have just begun to cross the defense.");
				crossingDefense = true;
			} else if (isNotOnDefense && crossingDefense) {
				// We have just finished crossing the defense.
				System.out.println("DriveForwardUntilLevel: We have just finished crossing the defense.");
				crossingDefense = false;
				crossedDefense = true;
				timeAtDefenseCross = Utility.getFPGATime();
			}
		} else if (!isNotOnDefense) {
			// We didn't actually cross the defense.
			System.out.println("DriveForwardUntilLevel: We didn't actually cross the defense.");
			crossingDefense = true;
			crossedDefense = false;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		boolean finished = (crossedDefense && Utility.getFPGATime() - timeAtDefenseCross >= minimumFinishedDurationMicroseconds);
		if (finished) {
			System.out.println("DriveForwardUntilLevel: We have finished normally.");
		}
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
		double timeSinceDefenseCross = Utility.getFPGATime() - timeAtDefenseCross;
		double sideRangeFinderDistance;
		try {
			sideRangeFinderDistance = Robot.driveTrain.getSideRangeFinderDistance();
		} catch (DriveTrain.RangeFinderNoSignalException e) {
			sideRangeFinderDistance = 99999;
		}
		System.out.println("DriveForwardUntilSideRangeFinderDistance: Ending.");
		System.out.println("DriveForwardUntilSideRangeFinderDistance: System status at end: sideRangeFinderDistance = " + sideRangeFinderDistance);
		System.out.println("DriveForwardUntilSideRangeFinderDistance: System status at end: crossingDefense = " + crossingDefense);
		System.out.println("DriveForwardUntilSideRangeFinderDistance: System status at end: crossedDefense = " + crossedDefense);
		System.out.println("DriveForwardUntilSideRangeFinderDistance: System status at end: timeSinceDefenseCross = " + timeSinceDefenseCross);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
		System.out.println("DriveForwardUntilLevel: We have been interrupted.");
	}
}
