package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoElevateAlignShoot extends CommandGroup {

	public static final double MINIMUM_FLYWHEEL_VELOCITY = Robot.MINIMUM_FLYWHEEL_VELOCITY;

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
		addParallel(new FlywheelOut());
		addParallel(new PowerLED());
		addSequential(new CommandWithMinimumFlywheelVelocity(new AutoElevateAlign(), MINIMUM_FLYWHEEL_VELOCITY));
		addSequential(new SpikeOutAndIn());
		addSequential(new CancelCommandGroup(this));
	}
	
    
    public void initialize() {
    	SmartDashboard.putBoolean("AutoElevateAlign running", true);
    }
    

    public void end() {
    	SmartDashboard.putBoolean("AutoElevateAlign running", false);
    }
    
    public void interrupted() {
    	end();
    }
}
