package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link Subsystem} representing the intake.
 */
public class Intake extends Subsystem {

	CANTalon intakeMotor = RobotMap.intakeMotor;
	CANTalon intakeArmMotor = RobotMap.intakeArmMotor;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets intake motor power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setIntakeMotorPower(double power) {
		intakeMotor.set(power);
	}

	/**
	 * Sets intake arm motor power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setIntakeArmMotorPower(double power) {
		intakeArmMotor.set(power);
	}

	/**
	 * Gets the {@link CANTalon} that represents the intake motor.
	 * 
	 * @return The {@link CANTalon} associated with the intake motor.
	 */
	public CANTalon getIntakeMotor() {
		return intakeMotor;
	}

	/**
	 * Gets the {@link CANTalon} that represents the intake arm motor.
	 * 
	 * @return The {@link CANTalon} associated with the intake arm motor.
	 */
	public CANTalon getIntakeArmMotor() {
		return intakeArmMotor;
	}

}
