package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickFlywheel;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link Subsystem} representing the flywheel.
 */
public class Flywheel extends Subsystem {

	CANTalon flywheelLeftMotor = RobotMap.flywheelLeftMotor;
	CANTalon flywheelRightMotor = RobotMap.flywheelRightMotor;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickFlywheel());
	}

	/**
	 * Sets flywheel power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setFlywheelPower(double power) {
		flywheelLeftMotor.set(-power);
		flywheelRightMotor.set(power);
	}

	/**
	 * Gets the {@link CANTalon} that represents the left flywheel motor.
	 * 
	 * @return The {@link CANTalon} associated with the left flywheel motor.
	 */
	public CANTalon getLeftFlywheelMotor() {
		return flywheelLeftMotor;
	}

	/**
	 * Gets the {@link CANTalon} that represents the right flywheel motor.
	 * 
	 * @return The {@link CANTalon} associated with the right flywheel motor.
	 */
	public CANTalon getRightFlywheelMotor() {
		return flywheelRightMotor;
	}

}
