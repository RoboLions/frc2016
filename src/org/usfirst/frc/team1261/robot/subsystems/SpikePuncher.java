package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SpikePuncher extends Subsystem {

	Relay spike = RobotMap.spike;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets spike to the specified {@link Relay.Value Value}.
	 * 
	 * @param value
	 *            The {@link Relay.Value Value} to which this spike should be
	 *            set. Valid values are {@link Relay.Value#kForward kForward},
	 *            {@link Relay.Value#kReverse kReverse}, and
	 *            {@link Relay.Value#kOff kOff}.
	 */
	public void setSpikeValue(Relay.Value value) {
		spike.set(value);
	}

	/**
	 * Gets the {@link Relay} that represents the spike.
	 * 
	 * @return The {@link Relay} associated with the spike.
	 */
	public Relay getSpike() {
		return spike;
	}
}
