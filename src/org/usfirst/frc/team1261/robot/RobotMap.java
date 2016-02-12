package org.usfirst.frc.team1261.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Servo;

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
	
	public static CANTalon frontLeftMotor = new CANTalon(6);
	public static CANTalon rearLeftMotor = new CANTalon(5);
	public static CANTalon frontRightMotor = new CANTalon(3);
	public static CANTalon rearRightMotor = new CANTalon(4);
	public static CANTalon intakeMotor = new CANTalon(0);
	public static CANTalon intakeArmMotor = new CANTalon(0);
	public static CANTalon flywheelLeftMotor = new CANTalon(0);
	public static CANTalon flywheelRightMotor = new CANTalon(0);
	public static CANTalon shooterArmMotor = new CANTalon(0);
	public static Servo servo = new Servo(6);
	public static Encoder rightEncoder = new Encoder(6, 7);
	public static Encoder leftEncoder = new Encoder(8, 9, true); // left encoder has reversed direction sense
	public static Encoder shooterArmEncoder = new Encoder(0, 0);
	public static RobotDrive driveTrain = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	public static AHRS navX = new AHRS(SerialPort.Port.kUSB);
	public static AnalogInput rangeFinder = new AnalogInput(0);
	
	static {
		driveTrain.setInvertedMotor(MotorType.kFrontLeft, true);
		driveTrain.setInvertedMotor(MotorType.kRearLeft, true);
		driveTrain.setInvertedMotor(MotorType.kFrontRight, true);
		driveTrain.setInvertedMotor(MotorType.kRearRight, true);
		rangeFinder.setAverageBits(0);
		rangeFinder.setOversampleBits(0);
	}
}
