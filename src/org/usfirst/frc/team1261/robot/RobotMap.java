package org.usfirst.frc.team1261.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

	public static CANTalon frontLeftMotor;
	public static CANTalon rearLeftMotor;
	public static CANTalon frontRightMotor;
	public static CANTalon rearRightMotor;
	public static CANTalon intakeRollerMotor;
	public static CANTalon leftIntakeArmMotor;
	public static CANTalon rightIntakeArmMotor;
	public static CANTalon flywheelLeftMotor;
	public static CANTalon flywheelRightMotor;
	public static CANTalon shooterArmMotor;
	public static Relay spike;
	public static Encoder shooterArmEncoder;
	public static Encoder intakeArmEncoder;
	public static RobotDrive driveTrain;
	public static AHRS navX;
	public static DigitalInput intakeArmLowerLimitSwitch;
	public static DigitalInput shooterArmLowerLimitSwitch;
	public static DigitalInput photoGate;

	public static void init() {
		frontLeftMotor = new CANTalon(3);
		rearLeftMotor = new CANTalon(4);
		frontRightMotor = new CANTalon(1);
		rearRightMotor = new CANTalon(2);
		intakeRollerMotor = new CANTalon(8);
		leftIntakeArmMotor = new CANTalon(9);
		rightIntakeArmMotor = new CANTalon(11);
		flywheelLeftMotor = new CANTalon(6);
		flywheelRightMotor = new CANTalon(5);
		shooterArmMotor = new CANTalon(7);
		spike = new Relay(0);
		shooterArmEncoder = new Encoder(2, 3);
		intakeArmEncoder = new Encoder(8, 9);
		driveTrain = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		navX = new AHRS(SerialPort.Port.kUSB);
		intakeArmLowerLimitSwitch = new DigitalInput(0);
		shooterArmLowerLimitSwitch = new DigitalInput(1);
		photoGate = new DigitalInput(4);

		driveTrain.setInvertedMotor(MotorType.kFrontLeft, true);
		driveTrain.setInvertedMotor(MotorType.kRearLeft, true);
		driveTrain.setInvertedMotor(MotorType.kFrontRight, true);
		driveTrain.setInvertedMotor(MotorType.kRearRight, true);
	}
}
