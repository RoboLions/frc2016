package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SpikeOutAndIn extends CommandGroup {
	
	public static final double SPIKE_OUT_TIMEOUT = 0.25;
	public static final double SPIKE_IN_TIMEOUT = 0.25;

	private boolean canceled = false;

    public  SpikeOutAndIn() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	requires(Robot.spikePuncher);
    	addSequential(new SpikeOut(), SPIKE_OUT_TIMEOUT);
    	addSequential(new SpikeIn(), SPIKE_IN_TIMEOUT);
    }

    public void initialize() {
    	canceled = !Robot.flywheel.meetsRequiredSpeed();
    	if (canceled)
    		System.out.println("Canceling firing the spike because the flywheel is too slow.");
    }

    public boolean isFinished() {
    	return canceled || super.isFinished();
    }
}
