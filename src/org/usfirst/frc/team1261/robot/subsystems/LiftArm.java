package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LiftArm extends Subsystem {

	CANTalon liftArmWinchMotor = RobotMap.liftArmWinchMotor;
	Servo liftArmBottomServo = RobotMap.liftArmBottomServo;
	Servo liftArmTopServo = RobotMap.liftArmTopServo;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets lift arm winch motor power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setWinchMotorPower(double power) {
		liftArmWinchMotor.set(power);
	}

	/**
	 * Sets position of the bottom servo of the lift arm to the specified
	 * position.
	 * 
	 * @param position
	 *            The position from 0.0 to 1.0, where 0.0 represents the
	 *            position fully to the left and 1.0 represents the position
	 *            fully to the right.
	 */
	public void setBottomServoPosition(double position) {
		liftArmBottomServo.set(position);
	}

	/**
	 * Sets position of the top servo of the lift arm to the specified position.
	 * 
	 * @param position
	 *            The position from 0.0 to 1.0, where 0.0 represents the
	 *            position fully to the left and 1.0 represents the position
	 *            fully to the right.
	 */
	public void setTopServoPosition(double position) {
		liftArmTopServo.set(position);
	}

	/**
	 * Ceases function in the motors correlated with the lift arm winch, causing
	 * a general end to motion of the lift arm winch.
	 */
	public void stop() {
		setWinchMotorPower(0.0);
	}

	/**
	 * Gets the {@link CANTalon} that represents the lift arm winch motor.
	 * 
	 * @return The {@link CANTalon} associated with the lift arm winch motor.
	 */
	public CANTalon getWinchMotor() {
		return liftArmWinchMotor;
	}

	/**
	 * Gets the {@link Servo} that represents the bottom servo of the lift arm.
	 * 
	 * @return The {@link Servo} associated with the bottom servo of the lift
	 *         arm.
	 */
	public Servo getBottomServo() {
		return liftArmBottomServo;
	}

	/**
	 * Gets the {@link Servo} that represents the top servo of the lift arm.
	 * 
	 * @return The {@link Servo} associated with the top servo of the lift arm.
	 */
	public Servo getTopServo() {
		return liftArmTopServo;
	}
}
