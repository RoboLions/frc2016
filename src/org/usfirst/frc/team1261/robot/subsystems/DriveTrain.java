package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link Subsystem} representing the drivetrain.
 */
public class DriveTrain extends Subsystem {

	CANTalon leftMotor = RobotMap.leftMotor;
	CANTalon rightMotor = RobotMap.rightMotor;
	Encoder leftEncoder = RobotMap.leftEncoder;
	Encoder rightEncoder = RobotMap.rightEncoder;
	RobotDrive robotDrive = RobotMap.driveTrain;
	AHRS navX = RobotMap.navX;

	// Change this to change the default PIDController for the DriveTrain.
	PIDController controller = new DisabledDriveTrainPIDController(this);

	/**
	 * Predefined {@link PIDController}s that the {@link DriveTrain} can use.
	 */
	public static enum DriveTrainPIDController {
		/**
		 * A distance-based {@link PIDController} for the {@link DriveTrain}.
		 */
		DISTANCE,
		/**
		 * An angle-based {@link PIDController} for the {@link DriveTrain}.
		 */
		ANGLE,
		/**
		 * A {@link PIDController} for the {@link DriveTrain} that does nothing.
		 */
		DISABLED;

		private PIDController getPIDControllerForDriveTrain(DriveTrain driveTrain) {
			switch (this) {
			case DISTANCE:
				return new DistanceBasedDriveTrainPIDController(driveTrain);
			case ANGLE:
				return new AngleBasedDriveTrainPIDController(driveTrain);
			default:
				return new DisabledDriveTrainPIDController(driveTrain);
			}
		}
	}

	public void initDefaultCommand() {
		setDefaultCommand(new JoystickDrive());
	}

	/**
	 * Returns distance traveled as an average of the distance reported by the
	 * left and right encoders.
	 * 
	 * @return Distance traveled in encoder units.
	 */
	public double distanceTraveled() {
		return (leftDistanceTraveled() + rightDistanceTraveled()) / 2;
	}

	/**
	 * Returns distance traveled according to the right encoder.
	 * 
	 * @return Right encoder's distance traveled in encoder units.
	 */
	public double leftDistanceTraveled() {
		return leftEncoder.getDistance();
	}

	/**
	 * Returns distance traveled according to the right encoder.
	 * 
	 * @return Right encoder's distance traveled in encoder units.
	 */
	public double rightDistanceTraveled() {
		return rightEncoder.getDistance();
	}

	/**
	 * Resets the encoders measuring distance traveled.
	 */
	public void resetDistanceTraveled() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	/**
	 * Drives the robot straight (forwards or backwards).
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0.
	 */
	public void drive(double power) {
		robotDrive.setLeftRightMotorOutputs(power, power);
	}

	/**
	 * Turns the robot in place.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0. Negative values represent
	 *            turning left, and positive values represent turning right.
	 */
	public void turn(double power) {
		robotDrive.setLeftRightMotorOutputs(power, -power);
	}

	/**
	 * Ceases function in the motors correlated with the drivetrain, causing a
	 * general end to motion of the wheels.
	 */
	public void stop() {
		robotDrive.stopMotor();
	}

	/**
	 * Gets the yaw angle from the navX micro.
	 * 
	 * @return A {@code float} between -180.0 and 180.0 representing the yaw
	 *         angle in degrees.
	 */
	public float getYaw() {
		return navX.getYaw();
	}

	/**
	 * Zeroes the yaw angle on the navX micro.
	 */
	public void zeroYaw() {
		navX.zeroYaw();
	}

	/**
	 * Sets the {@link PIDController} for this {@link DriveTrain}.
	 * 
	 * @param pidController
	 *            A {@link PIDController}.
	 */
	public void setPIDController(PIDController pidController) {
		controller.disable();
		controller = pidController;
	}

	/**
	 * Sets the {@link PIDController} for this {@link DriveTrain}.
	 * 
	 * @param driveTrainPIDController
	 *            A value from the {@code DriveTrainPIDController} {@code enum}
	 *            representing the desired {@link PIDController}.
	 */
	public void setPIDController(DriveTrainPIDController driveTrainPIDController) {
		setPIDController(driveTrainPIDController.getPIDControllerForDriveTrain(this));
	}

	/**
	 * Disables the {@link PIDController} for this {@link DriveTrain}.
	 */
	public void disablePIDController() {
		setPIDController(DriveTrainPIDController.DISABLED);
	}

	/**
	 * Gets the {@link PIDController} that this {@link DriveTrain} is currently
	 * using.
	 * 
	 * @return The {@link PIDController} that this {@link DriveTrain} is
	 *         currently using.
	 */
	public PIDController getPIDController() {
		return controller;
	}

	/**
	 * Gets the {@link RobotDrive}.
	 * 
	 * @return The {@link RobotDrive} associated with the drivetrain.
	 */
	public RobotDrive getRobotDrive() {
		return robotDrive;
	}

	/**
	 * Gets the navX micro object.
	 * 
	 * @return The {@link AHRS} associated with the navX micro.
	 */
	public AHRS getNavX() {
		return navX;
	}
}
