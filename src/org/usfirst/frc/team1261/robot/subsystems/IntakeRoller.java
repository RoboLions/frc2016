package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link Subsystem} representing the intake roller.
 */
public class IntakeRoller extends Subsystem {

	CANTalon intakeRollerMotor = RobotMap.intakeRollerMotor;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets intake roller motor power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setIntakeRollerMotorPower(double power) {
		intakeRollerMotor.set(power);
	}

	/**
	 * Ceases function in the motors correlated with the intake roller, causing
	 * a general end to motion of the intake roller.
	 */
	public void stop() {
		setIntakeRollerMotorPower(0.0);
	}

	/**
	 * Gets the {@link CANTalon} that represents the intake roller motor.
	 * 
	 * @return The {@link CANTalon} associated with the intake roller motor.
	 */
	public CANTalon getIntakeRollerMotor() {
		return intakeRollerMotor;
	}
}
