package org.usfirst.frc.team1261.robot.subsystems;

import org.usfirst.frc.team1261.robot.RobotMap;
import org.usfirst.frc.team1261.robot.commands.JoystickShooterArm;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The {@link Subsystem} representing the shooter arm.
 */
public class ShooterArm extends Subsystem {

	Encoder shooterArmEncoder = RobotMap.shooterArmEncoder;
	CANTalon shooterArmMotor = RobotMap.shooterArmMotor;

	public static final double SETPOINT_INTAKE_POSITION = -6.25;
	public static final double SETPOINT_HORIZONTAL_POSITION = 0.0;
	public static final double SETPOINT_SHOOTING_POSITION = 12.0;
	
	// TODO: figure out these values
	public static final double[] SETPOINTS = { SETPOINT_INTAKE_POSITION, SETPOINT_HORIZONTAL_POSITION, SETPOINT_SHOOTING_POSITION };
	// This array needs to be in ascending order for other code to work.
	
	// Change this to change the default PIDController for the DriveTrain.
	PIDController controller = new DisabledShooterArmPIDController(this);

	/**
	 * Predefined {@link PIDController}s that the {@link ShooterArm} can use.
	 */
	public static enum ShooterArmPIDController {
		/**
		 * A vision-tracking-based {@link PIDController} for the {@link ShooterArm}.
		 */
		VISION_TRACK,
		/**
		 * An angle-based {@link PIDController} for the {@link ShooterArm}.
		 */
		ANGLE,
		/**
		 * A {@link PIDController} for the {@link ShooterArm} that does nothing.
		 */
		DISABLED;

		private PIDController getPIDControllerForShooterArm(ShooterArm shooterArm) {
			switch (this) {
			case VISION_TRACK:
				return new VisionTrackingBasedShooterArmPIDController(shooterArm);
			case ANGLE:
				return new AngleBasedShooterArmPIDController(shooterArm);
			default:
				return new DisabledShooterArmPIDController(shooterArm);
			}
		}
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
		controller.disable();
		setShooterArmMotorPower(0.0);
	}
	

	/**
	 * Sets the {@link PIDController} for this {@link ShooterArm}.
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
	 * Sets the {@link PIDController} for this {@link ShooterArm}.
	 * 
	 * @param shooterArmPIDController
	 *            A value from the {@code ShooterArmPIDController} {@code enum}
	 *            representing the desired {@link PIDController}.
	 */
	public void setPIDController(ShooterArmPIDController shooterArmPIDController) {
		setPIDController(shooterArmPIDController.getPIDControllerForShooterArm(this));
	}

	/**
	 * Disables the {@link PIDController} for this {@link ShooterArm}.
	 */
	public void disablePIDController() {
		setPIDController(ShooterArmPIDController.DISABLED);
	}

	/**
	 * Gets the {@link PIDController} that this {@link ShooterArm} is currently
	 * using.
	 * 
	 * @return The {@link PIDController} that this {@link ShooterArm} is
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
	 * {@link ShooterArmPIDController}, please implement this method yourself.</em>
	 * 
	 * @return {@code true} if the error is less than the tolerance.
	 */
	public boolean onTarget() {
		return getPIDController().onTarget();
	}
}
