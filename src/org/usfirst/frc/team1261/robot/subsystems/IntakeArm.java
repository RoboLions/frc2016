package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickIntakeArm;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The {@link PIDSubsystem} representing the intake arm.
 */
public class IntakeArm extends PIDSubsystem {

	public static final double kP = 0.001;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	public static final double TOLERANCE = 0.0;

	public static final double PID_POWER_SCALING_FACTOR = 0.8;

	public static final double SETPOINT_ALMOST_GROUND_POSITION = -43.0;
	public static final double SETPOINT_INTAKE_POSITION = -37.0;
	public static final double SETPOINT_FULLY_UP_POSITION = 0.0;

	// TODO: figure out these values
	public static final double[] SETPOINTS = { SETPOINT_ALMOST_GROUND_POSITION, SETPOINT_INTAKE_POSITION,
			SETPOINT_FULLY_UP_POSITION };
	// This array needs to be in ascending order for other code to work.

	Encoder intakeArmEncoder = RobotMap.intakeArmEncoder;
	CANTalon leftIntakeArmMotor = RobotMap.leftIntakeArmMotor;
	CANTalon rightIntakeArmMotor = RobotMap.rightIntakeArmMotor;
	DigitalInput intakeArmUpperLimitSwitch = RobotMap.intakeArmUpperLimitSwitch;
	DigitalInput intakeArmLowerLimitSwitch = RobotMap.intakeArmLowerLimitSwitch;

	// Initialize your subsystem here
	public IntakeArm() {
		// Use these to get going:
		// setSetpoint() - Sets where the PID controller should move the system
		// to
		// enable() - Enables the PID controller.
		super("IntakeArm", kP, kI, kD);
		setAbsoluteTolerance(TOLERANCE);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickIntakeArm());
	}

	/**
	 * Sets intake arm motor power to the specified power level. Note that this
	 * method will automatically set the motor power to {@code 0.0} if the power
	 * level indicates that the intake arm is trying to go in the direction of
	 * the limit switch when the limit switch is pressed.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setIntakeArmMotorPower(double power) {
		if (!SmartDashboard.getBoolean("Override intake arm limits", false) && ((power < 0.0 && intakeArmLowerLimitSwitch.get()) || (power > 0.0 && intakeArmUpperLimitSwitch.get()))) {
			// If motor is going against limit switch
			power = 0.0;
		}
		leftIntakeArmMotor.set(power);
		rightIntakeArmMotor.set(power);
	}

	/**
	 * Gets the {@link CANTalon} that represents the left intake arm motor.
	 * 
	 * @return The {@link CANTalon} associated with the left intake arm motor.
	 */
	public CANTalon getLeftIntakeArmMotor() {
		return leftIntakeArmMotor;
	}

	/**
	 * Gets the {@link CANTalon} that represents the right intake arm motor.
	 * 
	 * @return The {@link CANTalon} associated with the right intake arm motor.
	 */
	public CANTalon getRightIntakeArmMotor() {
		return rightIntakeArmMotor;
	}

	/**
	 * Returns angle in encoder units. This is measured by the distance traveled
	 * by the intake arm encoder.
	 * 
	 * @return Angle of intake arm in encoder units.
	 */
	public double getAngle() {
		return intakeArmEncoder.getDistance();
	}

	/**
	 * Gets the {@link Encoder} that represents the intake arm motor encoder.
	 * 
	 * @return The {@link Encoder} associated with the intake arm motor encoder.
	 */
	public Encoder getIntakeArmEncoder() {
		return intakeArmEncoder;
	}

	/**
	 * Resets the intake arm motor encoder to the value 0.0.
	 */
	public void zeroIntakeArmEncoder() {
		intakeArmEncoder.reset();
	}

	/**
	 * Ceases function in the motors correlated with the intake arm, causing a
	 * general end to motion of the intake arm.
	 */
	public void stop() {
		disable();
		setIntakeArmMotorPower(0.0);
	}

	/**
	 * Return {@code true} if the error is within the tolerance determined by
	 * {@link IntakeArm#TOLERANCE}.<br>
	 * <em>This method overrides {@link PIDSubsystem}'s
	 * {@link PIDSubsystem#onTarget onTarget} method as a workaround for
	 * <a href="https://usfirst.collab.net/sf/tracker/do/viewArtifact/projects.wpilib/tracker.4_defects/artf4812">
	 * a bug in WPILib's implementation</a>.</em>
	 * 
	 * @return {@code true} if the error is less than the tolerance.
	 */
	public boolean onTarget() {
		return (Math.abs(getPIDController().getError()) < TOLERANCE);
	}

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		return getAngle();
	}

	protected void usePIDOutput(double output) {
		// Use output to drive your system, like a motor
		// e.g. yourMotor.set(output);
		setIntakeArmMotorPower(Math.signum(output) * PID_POWER_SCALING_FACTOR);
	}

	/**
	 * Gets the {@link DigitalInput} that represents the intake arm lower limit
	 * switch.
	 * 
	 * @return The {@link DigitalInput} associated with the intake arm lower
	 *         limit switch.
	 */
	public DigitalInput getIntakeArmLowerLimitSwitch() {
		return intakeArmLowerLimitSwitch;
	}

	/**
	 * Gets the {@code boolean} that represents the status of the intake arm
	 * lower limit switch.
	 * 
	 * @return {@code true} if the intake arm lower limit switch is hit,
	 *         {@code false} otherwise.
	 */
	public boolean isLowerLimitSwitchHit() {
		if (SmartDashboard.getBoolean("Override intake arm limits", false)) {
			return false;
		}
		return intakeArmLowerLimitSwitch.get();
	}

	/**
	 * Gets the {@link DigitalInput} that represents the intake arm upper limit
	 * switch.
	 * 
	 * @return The {@link DigitalInput} associated with the intake arm upper
	 *         limit switch.
	 */
	public DigitalInput getIntakeArmUpperLimitSwitch() {
		return intakeArmUpperLimitSwitch;
	}

	/**
	 * Gets the {@code boolean} that represents the status of the intake arm
	 * upper limit switch.
	 * 
	 * @return {@code true} if the intake arm upper limit switch is hit,
	 *         {@code false} otherwise.
	 */
	public boolean isUpperLimitSwitchHit() {
		if (SmartDashboard.getBoolean("Override intake arm limits", false)) {
			return false;
		}
		return intakeArmUpperLimitSwitch.get();
	}
}
