package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.commands.TurnUntilContourFound.Direction;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SimpleAutonomousProgram extends CommandGroup {

	public static final double SHOOTER_ARM_INITIAL_POSITION = ShooterArm.SETPOINT_HORIZONTAL_POSITION;
	public static final double SHOOTER_ARM_FINAL_POSITION = ShooterArm.SETPOINT_SHOOTING_POSITION;
	public static final double DRIVE_FORWARD_UNTIL_SIDE_RANGE_FINDER_TIMEOUT = 4;
	public static final double DRIVE_FORWARD_DURATION = 0.3;
	public static final double RANGE_FINDER_DISTANCE = 1.4;
	public static final double LOWER_INTAKE_ARM_TIMEOUT = 0.75;

	public SimpleAutonomousProgram(boolean shoot, Direction direction) {
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
		addSequential(new GoToShooterArmPosition(SHOOTER_ARM_INITIAL_POSITION));
		//addSequential(new DriveForwardUntilLevel(), DRIVE_FORWARD_UNTIL_LEVEL_TIMEOUT);
		addSequential(new DriveForwardUntilSideRangeFinderDistance(), 
				DRIVE_FORWARD_UNTIL_SIDE_RANGE_FINDER_TIMEOUT);
		addSequential(new DriveForward(DRIVE_FORWARD_DURATION));
		addSequential(new IntakeArmToLowerLimitSwitch(), LOWER_INTAKE_ARM_TIMEOUT);
		addSequential(new GoToShooterArmPosition(SHOOTER_ARM_FINAL_POSITION));
		if (shoot) {
			addSequential(new TurnUntilContourFound(direction));
			addSequential(new AutoElevateAlignShoot());
		}
	}
}
