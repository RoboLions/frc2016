package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

	CANTalon liftWinchMotor = RobotMap.liftWinchMotor;
	Servo liftServo = RobotMap.liftServo;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets lift winch motor power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setWinchMotorPower(double power) {
		liftWinchMotor.set(power);
	}

	/**
	 * Sets position of the servo of the lift to the specified position.
	 * 
	 * @param position
	 *            The position from 0.0 to 1.0, where 0.0 represents the
	 *            position fully to the left and 1.0 represents the position
	 *            fully to the right.
	 */
	public void setServoPosition(double position) {
		liftServo.set(position);
	}

	/**
	 * Ceases function in the motors correlated with the lift winch, causing a
	 * general end to motion of the lift winch.
	 */
	public void stop() {
		setWinchMotorPower(0.0);
	}

	/**
	 * Gets the {@link CANTalon} that represents the lift winch motor.
	 * 
	 * @return The {@link CANTalon} associated with the lift winch motor.
	 */
	public CANTalon getWinchMotor() {
		return liftWinchMotor;
	}

	/**
	 * Gets the {@link Servo} that represents the servo of the lift arm.
	 * 
	 * @return The {@link Servo} associated with the servo of the lift arm.
	 */
	public Servo getServo() {
		return liftServo;
	}
}
