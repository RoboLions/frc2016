package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VisionTrackingLED extends Subsystem {

	public static final Value ON_VALUE = Value.kForward;
	public static final Value OFF_VALUE = Value.kOff;

	Relay ledPower = RobotMap.ledPower;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Gets the {@link Relay} that represents the power for the LED used for
	 * vision tracking.
	 * 
	 * @return The {@link Relay} associated with the power for the LED used for
	 *         vision tracking.
	 */
	public Relay getLEDPower() {
		return ledPower;
	}

	public void enable() {
		ledPower.set(ON_VALUE);
	}

	public void disable() {
		ledPower.set(OFF_VALUE);
	}
}
