package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickIntakeArm;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The {@link PIDSubsystem} representing the intake arm.
 */
public class IntakeArm extends PIDSubsystem {

	public static final double kP = 0.001;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	public static final double TOLERANCE = 0.0;

	// TODO: figure out these values
	public static final double[] SETPOINTS = { 0.0, 200.0, 400.0, 600.0 };
	// This array needs to be in ascending order for other code to work.

	Encoder intakeArmEncoder = RobotMap.intakeArmEncoder;
	CANTalon intakeArmMotor = RobotMap.intakeArmMotor;
	DigitalInput intakeArmLimitSwitch = RobotMap.intakeArmLimitSwitch;

	LimitSwitchStatus intakeArmLimitSwitchStatus = LimitSwitchStatus.OFF;
	double lastPower = 0.0;

	/**
	 * {@code enum} defining possible statuses of the limit switch. Possible
	 * values are {@link LimitSwitchStatus#UPPER UPPER},
	 * {@link LimitSwitchStatus#LOWER LOWER}, or {@link LimitSwitchStatus#OFF
	 * OFF}.
	 */
	public static enum LimitSwitchStatus {
		/**
		 * Indicates that the limit switch is pressed because the mechanism
		 * pressing it is at its upper limit.
		 */
		UPPER,
		/**
		 * Indicates that the limit switch is pressed because the mechanism
		 * pressing it is at its lower limit.
		 */
		LOWER,
		/**
		 * Indicates that the limit switch is not pressed.
		 */
		OFF
	}

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
	 * Updates the limit switch status returned by
	 * {@link IntakeArm#getLimitSwitchStatus getLimitSwitchStatus}.
	 */
	public void updateLimitSwitchStatus() {
		if (intakeArmLimitSwitch.get()) {
			if (intakeArmLimitSwitchStatus == LimitSwitchStatus.OFF) {
				if (lastPower > 0.0) {
					intakeArmLimitSwitchStatus = LimitSwitchStatus.UPPER;
				} else if (lastPower < 0.0) {
					intakeArmLimitSwitchStatus = LimitSwitchStatus.LOWER;
				}
			}
		} else {
			intakeArmLimitSwitchStatus = LimitSwitchStatus.OFF;
		}
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
		updateLimitSwitchStatus();
//		if ((power > 0.0 && intakeArmLimitSwitchStatus == LimitSwitchStatus.UPPER)
//				|| (power < 0.0 && intakeArmLimitSwitchStatus == LimitSwitchStatus.LOWER)) {
//			power = 0.0;
//		}
		if (power != 0.0) {
			lastPower = power;
		}
		intakeArmMotor.set(power);
	}

	/**
	 * Gets the {@link CANTalon} that represents the intake arm motor.
	 * 
	 * @return The {@link CANTalon} associated with the intake arm motor.
	 */
	public CANTalon getIntakeArmMotor() {
		return intakeArmMotor;
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

	/**
	 * Gets the {@link LimitSwitchStatus} that represents the status of the
	 * intake arm limit switch.
	 * 
	 * @return {@link LimitSwitchStatus#UPPER UPPER},
	 *         {@link LimitSwitchStatus#LOWER LOWER}, or
	 *         {@link LimitSwitchStatus#OFF OFF}, depending on the status of the
	 *         limit switch.
	 */
	public LimitSwitchStatus getLimitSwitchStatus() {
		return intakeArmLimitSwitchStatus;
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
		setIntakeArmMotorPower(output);
	}

}
