package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.subsystems.IntakeArm;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutonomousProgram extends CommandGroup {

	public static final double INTAKE_ARM_POSITION = IntakeArm.SETPOINT_INTAKE_POSITION;
	public static final double SHOOTER_ARM_POSITION = ShooterArm.SETPOINT_SHOOTING_POSITION;

	/**
	 * The delay in seconds after powering the flywheel before the spike should
	 * fire.
	 */
	public static final double SPIKE_DELAY = 5.0;
	/**
	 * The total duration in seconds during which the flywheel should be
	 * powered, including the spike delay.
	 */
	public static final double FLYWHEEL_OUT_DURATION = SPIKE_DELAY + 2.0;

	public AutonomousProgram() {
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
		addSequential(new GoToIntakeArmPosition(INTAKE_ARM_POSITION));
		addSequential(new GoToShooterArmPosition(SHOOTER_ARM_POSITION));
		addParallel(new FlywheelOut(), FLYWHEEL_OUT_DURATION);
		addSequential(new WaitCommand(SPIKE_DELAY));
		addSequential(new SpikeOutAndIn());
	}
}
