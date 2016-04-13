package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickFlywheel;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link Subsystem} representing the flywheel.
 */
public class Flywheel extends Subsystem {

	public static final FeedbackDevice LEFT_MOTOR_FEEDBACK_DEVICE = FeedbackDevice.QuadEncoder;
	public static final boolean LEFT_MOTOR_SENSOR_REVERSED = false;
	public static final int LEFT_MOTOR_ENCODER_CODES_PER_REVOLUTION = 0;
	public static final int LEFT_MOTOR_POTENTIOMETER_TURNS = 0;
	public static final float LEFT_MOTOR_NOMINAL_OUTPUT_VOLTAGE = 0.0f;
	public static final float LEFT_MOTOR_PEAK_OUTPUT_VOLTAGE = 12.0f;
	public static final int LEFT_MOTOR_PIDF_PROFILE = 0;
	public static final double LEFT_MOTOR_GAIN_F = 0.0;
	public static final double LEFT_MOTOR_GAIN_P = 0.0;
	public static final double LEFT_MOTOR_GAIN_I = 0.0;
	public static final double LEFT_MOTOR_GAIN_D = 0.0;

	public static final FeedbackDevice RIGHT_MOTOR_FEEDBACK_DEVICE = FeedbackDevice.QuadEncoder;
	public static final boolean RIGHT_MOTOR_SENSOR_REVERSED = false;
	public static final int RIGHT_MOTOR_ENCODER_CODES_PER_REVOLUTION = 0;
	public static final int RIGHT_MOTOR_POTENTIOMETER_TURNS = 0;
	public static final float RIGHT_MOTOR_NOMINAL_OUTPUT_VOLTAGE = 0.0f;
	public static final float RIGHT_MOTOR_PEAK_OUTPUT_VOLTAGE = 12.0f;
	public static final int RIGHT_MOTOR_PIDF_PROFILE = 0;
	public static final double RIGHT_MOTOR_GAIN_F = 0.0;
	public static final double RIGHT_MOTOR_GAIN_P = 0.0;
	public static final double RIGHT_MOTOR_GAIN_I = 0.0;
	public static final double RIGHT_MOTOR_GAIN_D = 0.0;

	public static final double REQUIRED_FLYWHEEL_SPEED = 200.0;
	public static final double MINIMUM_FLYWHEEL_SPEED = 575.0;

	CANTalon flywheelLeftMotor = RobotMap.flywheelLeftMotor;
	CANTalon flywheelRightMotor = RobotMap.flywheelRightMotor;
	DigitalInput photoGate = RobotMap.photoGate;

	public Flywheel() {

		flywheelLeftMotor.setFeedbackDevice(LEFT_MOTOR_FEEDBACK_DEVICE);
		flywheelLeftMotor.reverseSensor(LEFT_MOTOR_SENSOR_REVERSED);
		if (LEFT_MOTOR_FEEDBACK_DEVICE == FeedbackDevice.QuadEncoder) {
			flywheelLeftMotor.configEncoderCodesPerRev(LEFT_MOTOR_ENCODER_CODES_PER_REVOLUTION);
		}
		if (LEFT_MOTOR_FEEDBACK_DEVICE == FeedbackDevice.AnalogEncoder
				|| LEFT_MOTOR_FEEDBACK_DEVICE == FeedbackDevice.AnalogPot) {
			flywheelLeftMotor.configPotentiometerTurns(LEFT_MOTOR_POTENTIOMETER_TURNS);
		}
		flywheelLeftMotor.configNominalOutputVoltage(LEFT_MOTOR_NOMINAL_OUTPUT_VOLTAGE,
				-LEFT_MOTOR_NOMINAL_OUTPUT_VOLTAGE);
		flywheelLeftMotor.configPeakOutputVoltage(LEFT_MOTOR_PEAK_OUTPUT_VOLTAGE, -LEFT_MOTOR_PEAK_OUTPUT_VOLTAGE);
		flywheelLeftMotor.setProfile(LEFT_MOTOR_PIDF_PROFILE);
		flywheelLeftMotor.setF(LEFT_MOTOR_GAIN_F);
		flywheelLeftMotor.setP(LEFT_MOTOR_GAIN_P);
		flywheelLeftMotor.setI(LEFT_MOTOR_GAIN_I);
		flywheelLeftMotor.setD(LEFT_MOTOR_GAIN_D);

		flywheelRightMotor.setFeedbackDevice(RIGHT_MOTOR_FEEDBACK_DEVICE);
		flywheelRightMotor.reverseSensor(RIGHT_MOTOR_SENSOR_REVERSED);
		if (RIGHT_MOTOR_FEEDBACK_DEVICE == FeedbackDevice.QuadEncoder) {
			flywheelRightMotor.configEncoderCodesPerRev(RIGHT_MOTOR_ENCODER_CODES_PER_REVOLUTION);
		}
		if (RIGHT_MOTOR_FEEDBACK_DEVICE == FeedbackDevice.AnalogEncoder
				|| RIGHT_MOTOR_FEEDBACK_DEVICE == FeedbackDevice.AnalogPot) {
			flywheelRightMotor.configPotentiometerTurns(RIGHT_MOTOR_POTENTIOMETER_TURNS);
		}
		flywheelRightMotor.configNominalOutputVoltage(RIGHT_MOTOR_NOMINAL_OUTPUT_VOLTAGE,
				-RIGHT_MOTOR_NOMINAL_OUTPUT_VOLTAGE);
		flywheelRightMotor.configPeakOutputVoltage(RIGHT_MOTOR_PEAK_OUTPUT_VOLTAGE, -RIGHT_MOTOR_PEAK_OUTPUT_VOLTAGE);
		flywheelRightMotor.setProfile(RIGHT_MOTOR_PIDF_PROFILE);
		flywheelRightMotor.setF(RIGHT_MOTOR_GAIN_F);
		flywheelRightMotor.setP(RIGHT_MOTOR_GAIN_P);
		flywheelRightMotor.setI(RIGHT_MOTOR_GAIN_I);
		flywheelRightMotor.setD(RIGHT_MOTOR_GAIN_D);

	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickFlywheel());
	}

	/**
	 * Sets flywheel speed to the specified speed.
	 * 
	 * @param power
	 *            The speed in revolutions per minute (rpm).
	 */
	public void setFlywheelSpeed(double speed) {

		flywheelLeftMotor.changeControlMode(TalonControlMode.Speed);
		flywheelRightMotor.changeControlMode(TalonControlMode.Speed);
		flywheelLeftMotor.set(-speed);
		flywheelRightMotor.set(speed);

	}

	/**
	 * Sets flywheel power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setFlywheelPower(double power) {

		flywheelLeftMotor.changeControlMode(TalonControlMode.PercentVbus);
		flywheelRightMotor.changeControlMode(TalonControlMode.PercentVbus);
		flywheelLeftMotor.set(power);
		flywheelRightMotor.set(-power);

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

	/**
	 * Gets the {@code boolean} that represents the status of the flywheel
	 * photogate.
	 * 
	 * @return {@code true} if the photogate is blocked, {@code false}
	 *         otherwise.
	 */
	public boolean isPhotoGateBlocked() {
		return photoGate.get();
	}

	/**
	 * Gets the {@code boolean} that represents whether the flywheel is at or
	 * above the given speed. This represents if it is safe to shoot.
	 * 
	 * @param speed
	 *            The speed at or above which the flywheel must be.
	 * @return {@code true} if both flywheel motors are at or above the given
	 *         speed, {@code false} otherwise.
	 */
	public boolean meetsSpeed(double speed) {
		if (Math.abs(flywheelLeftMotor.getEncVelocity()) < speed)
			return false;
		if (Math.abs(flywheelRightMotor.getEncVelocity()) < speed)
			return false;
		return true;
	}

	/**
	 * Gets the {@code boolean} that represents whether the flywheel is at or
	 * above the {@link Flywheel#REQUIRED_FLYWHEEL_SPEED}. This represents if it
	 * is safe to shoot.
	 * 
	 * @return {@code true} if it is safe to shoot, {@code false} otherwise.
	 */
	public boolean meetsRequiredSpeed() {
		return meetsSpeed(REQUIRED_FLYWHEEL_SPEED);
	}

	/**
	 * Gets the {@code boolean} that represents whether the flywheel is at or
	 * above the {@link Flywheel#MINIMUM_FLYWHEEL_SPEED}. This represents if the
	 * robot should shoot.
	 * 
	 * @return {@code true} if the robot should shoot, {@code false} otherwise.
	 */
	public boolean meetsMinimumSpeed() {
		return meetsSpeed(MINIMUM_FLYWHEEL_SPEED);
	}

	/**
	 * Ceases function in the motors correlated with the flywheel, causing a
	 * general end to motion of the flywheel.
	 */
	public void stop() {
		setFlywheelPower(0.0);
	}
}
