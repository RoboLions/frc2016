package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;

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
	 * @return The {@link Encoder} associated with the shooter arm motor encoder.
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

	protected double returnPIDInput() {
		// Return your input value for the PID loop
		// e.g. a sensor, like a potentiometer:
		// yourPot.getAverageVoltage() / kYourMaxVoltage;
		return getAngle();
	}

	protected void usePIDOutput(double output) {
		// Use output to drive your system, like a motor
		// e.g. yourMotor.set(output);
		shooterArmMotor.set(output);
	}
}
