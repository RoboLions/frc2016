package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoElevateAlignShoot extends CommandGroup {

	public static final double MINIMUM_SPIKE_DELAY = 5.0;

	public AutoElevateAlignShoot() {
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

		requires(Robot.shooterArm);
		requires(Robot.driveTrain);
		requires(Robot.flywheel);
		requires(Robot.spikePuncher);
		addParallel(new FlywheelOut());
		addSequential(new CommandWithMinimumDuration(new AutoElevateAlign(), MINIMUM_SPIKE_DELAY));
		addSequential(new SpikeOutAndIn());
		cancel();
	}
}
