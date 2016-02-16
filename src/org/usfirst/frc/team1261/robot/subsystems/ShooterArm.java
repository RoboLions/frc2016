package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickShooterArm;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * The {@link PIDSubsystem} representing the shooter arm.
 */
public class ShooterArm extends PIDSubsystem {

	public static final double kP = 0.001;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	public static final double TOLERANCE = 0.0;

	Encoder shooterArmEncoder = RobotMap.shooterArmEncoder;
	CANTalon shooterArmMotor = RobotMap.shooterArmMotor;

	// Initialize your subsystem here
	public ShooterArm() {
		// Use these to get going:
		// setSetpoint() - Sets where the PID controller should move the system
		// to
		// enable() - Enables the PID controller.
		super("ShooterArm", kP, kI, kD);
		setAbsoluteTolerance(TOLERANCE);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new JoystickShooterArm());
	}

	/**
	 * Returns angle in encoder units. This is measured by the distance traveled
	 * by the shooter arm encoder.
	 * 
	 * @return Angle of shooter arm in encoder units.
	 */
	public double getAngle() {
		return shooterArmEncoder.getDistance();
	}

	/**
	 * Gets the {@link Encoder} that represents the shooter arm motor encoder.
	 * 
	 * @return The {@link Encoder} associated with the shooter arm motor
	 *         encoder.
	 */
	public Encoder getShooterArmEncoder() {
		return shooterArmEncoder;
	}

	/**
	 * Gets the {@link CANTalon} that represents the shooter arm motor.
	 * 
	 * @return The {@link CANTalon} associated with the shooter arm motor.
	 */
	public CANTalon getShooterArmMotor() {
		return shooterArmMotor;
	}

	/**
	 * Sets shooter arm motor power to the specified power level.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void setShooterArmMotorPower(double power) {
		shooterArmMotor.set(power);
	}

	/**
	 * Ceases function in the motors correlated with the shooter arm, causing a
	 * general end to motion of the shooter arm.
	 */
	public void stop() {
		disable();
		setShooterArmMotorPower(0.0);
	}

	/**
	 * Return {@code true} if the error is within the tolerance determined by
	 * {@link ShooterArm#TOLERANCE}.<br>
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
		setShooterArmMotorPower(output);
	}
}
