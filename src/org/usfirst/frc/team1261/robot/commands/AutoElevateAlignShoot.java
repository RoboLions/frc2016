package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.Flywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoElevateAlignShoot extends CommandGroup {

	public static final double MINIMUM_FLYWHEEL_SPEED = Flywheel.MINIMUM_FLYWHEEL_SPEED;

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
		requires(Robot.visionTrackingLED);
		FlywheelOut flywheelOut = new FlywheelOut();
		PowerLED powerLED = new PowerLED();
		addParallel(flywheelOut);
		addParallel(powerLED);
		addSequential(new CommandWithMinimumFlywheelSpeed(new AutoElevateAlign(), MINIMUM_FLYWHEEL_SPEED));
		addSequential(new SpikeOutAndIn());
		addSequential(new FinishCommand(flywheelOut));
		addSequential(new FinishCommand(powerLED));
	}
	
    
    public void initialize() {
    	System.out.println("Starting auto-shot...");
    }

    public void end() {
    	System.out.println("Auto-shot finished.");
    }
    
    public void interrupted() {
    	System.out.println("Auto-shot interrupted.");
    }
}
