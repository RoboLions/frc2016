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

	CANTalon frontLeftMotor = RobotMap.frontLeftMotor;
	CANTalon rearLeftMotor = RobotMap.rearLeftMotor;
	CANTalon frontRightMotor = RobotMap.frontRightMotor;
	CANTalon rearRightMotor = RobotMap.rearRightMotor;
	Encoder leftEncoder = RobotMap.leftDriveEncoder;
	Encoder rightEncoder = RobotMap.rightDriveEncoder;
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
		 * A vision-tracking-based {@link PIDController} for the {@link DriveTrain}.
		 */
		VISION_TRACK,
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
			case VISION_TRACK:
				return new VisionTrackingBasedDriveTrainPIDController(driveTrain);
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
	 * Returns distance traveled according to the left encoder.
	 * 
	 * @return Left encoder's distance traveled in encoder units.
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
		robotDrive.setLeftRightMotorOutputs(-power, -power);
	}

	/**
	 * Turns the robot in place.
	 * 
	 * @param power
	 *            The power, between -1.0 and 1.0. Negative values represent
	 *            turning left, and positive values represent turning right.
	 */
	public void turn(double power) {
		robotDrive.setLeftRightMotorOutputs(-power, power);
	}

	/**
	 * Ceases function in the motors correlated with the drivetrain, causing a
	 * general end to motion of the wheels.
	 */
	public void stop() {
		controller.disable();
		robotDrive.stopMotor();
	}

	/**
	 * Gets the accumulated yaw angle from the navX micro. This does not reset
	 * when the robot turns a full 360 degrees, so it is useful for algorithms
	 * expecting a continuous, unbounded input range. To reset this value to
	 * zero degrees, use the {@link DriveTrain#zeroYaw zeroYaw} method.
	 * 
	 * @return A {@code double} representing the accumulated yaw angle in
	 *         degrees.
	 * @see {@link DriveTrain#getYaw getYaw}
	 */
	public double getAngle() {
		return navX.getAngle();
	}

	/**
	 * Gets the yaw angle from the navX micro. To reset this value to zero
	 * degrees, use the {@link DriveTrain#zeroYaw zeroYaw} method.
	 * 
	 * @return A {@code float} between -180.0 and 180.0 representing the yaw
	 *         angle in degrees.
	 * @see {@link DriveTrain#getAngle getAngle}
	 */
	public float getYaw() {
		return navX.getYaw();
	}

	/**
	 * Alias for {@link DriveTrain#zeroYaw zeroYaw}, which zeroes the yaw angle
	 * on the navX micro. This affects both {@link DriveTrain#getYaw getYaw} and
	 * {@link DriveTrain#getAngle getAngle} .
	 */
	public void zeroAngle() {
		navX.zeroYaw();
	}

	/**
	 * Zeroes the yaw angle on the navX micro. This affects both
	 * {@link DriveTrain#getYaw getYaw} and {@link DriveTrain#getAngle getAngle}
	 * .
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
		stop();
		controller = pidController;
		controller.enable();
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
	 * Sets the setpoint for the PIDController. Equivalent to
	 * getPIDController().setSetpoint(setpoint).
	 * 
	 * @param setpoint
	 *            The desired setpoint.
	 */
	public void setSetpoint(double setpoint) {
		getPIDController().setSetpoint(setpoint);
	}

	/**
	 * Return {@code true} if the error is within the specified tolerance.
	 * Equivalent to getPIDController().onTarget().<br>
	 * <em>The current implementation of {@link PIDController#onTarget()} is
	 * buggy. If you are using a {@link PIDController} that is not a
	 * {@link DriveTrainPIDController}, please implement this method yourself.</em>
	 * 
	 * @return {@code true} if the error is less than the tolerance.
	 */
	public boolean onTarget() {
		return getPIDController().onTarget();
	}

	/**
	 * Drive to a specified distance.
	 * 
	 * @param distance
	 *            The distance, according to the encoders, to which the robot
	 *            should drive.
	 * @see {@link driveToRelative} to drive to a distance relative to the
	 *      current distance.
	 */
	public void driveTo(double distance) {
		setPIDController(DriveTrainPIDController.DISTANCE);
		setSetpoint(distance);
	}

	/**
	 * Drive to a specified distance relative to the current distance.
	 * 
	 * @param distance
	 *            The distance relative to the current distance, according to
	 *            the encoders, to which the robot should drive.
	 * @see {@link driveTo} to drive to an absolute distance.
	 */
	public void driveToRelative(double distance) {
		setPIDController(DriveTrainPIDController.DISTANCE);
		setSetpoint(distanceTraveled() + distance);
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

	/**
	 * Gets the {@link CANTalon} that represents the front left motor.
	 * 
	 * @return The {@link CANTalon} associated with the front left motor.
	 */
	public CANTalon getFrontLeftMotor() {
		return frontLeftMotor;
	}

	/**
	 * Gets the {@link CANTalon} that represents the rear left motor.
	 * 
	 * @return The {@link CANTalon} associated with the rear left motor.
	 */
	public CANTalon getRearLeftMotor() {
		return rearLeftMotor;
	}

	/**
	 * Gets the {@link CANTalon} that represents the front right motor.
	 * 
	 * @return The {@link CANTalon} associated with the front right motor.
	 */
	public CANTalon getFrontRightMotor() {
		return frontRightMotor;
	}

	/**
	 * Gets the {@link CANTalon} that represents the rear right motor.
	 * 
	 * @return The {@link CANTalon} associated with the rear right motor.
	 */
	public CANTalon getRearRightMotor() {
		return rearRightMotor;
	}
}
