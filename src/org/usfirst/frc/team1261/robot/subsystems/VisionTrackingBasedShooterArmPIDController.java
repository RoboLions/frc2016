package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A vision-tracking-based {@link ShooterArm} {@link PIDController}.
 */
class VisionTrackingBasedShooterArmPIDController extends PIDController {

	// TODO: figure out these values
	public static final double kP = 0.005;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	public static final double DEFAULT_TOLERANCE = RaspberryPiCommunicationAdapter.Y_AXIS_TOLERANCE;

	/**
	 * Error value used for PID when no target can be found.
	 */
	public static final double DEFAULT_ERROR = 0.0;

	public VisionTrackingBasedShooterArmPIDController(ShooterArm shooterArm) {
		super(kP, kI, kD, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				try {
					double targetYOffset = RaspberryPiCommunicationAdapter.getTargetYOffset();
					SmartDashboard.putNumber("targetYOffset", targetYOffset);
					SmartDashboard.putBoolean("Contours found", true);
					return targetYOffset;
				} catch (RaspberryPiCommunicationAdapter.NoContoursFoundException e) {
					SmartDashboard.putNumber("targetYOffset", DEFAULT_ERROR);
					SmartDashboard.putBoolean("Contours found", false);
					return DEFAULT_ERROR;
				}
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				shooterArm.setShooterArmMotorPower(-output);
				SmartDashboard.putNumber("Output to shooterArm.setShooterArmMotorPower", output);
			}
		});
		setAbsoluteTolerance(DEFAULT_TOLERANCE);
	}

	/**
	 * Return {@code true} if the error is within the tolerance determined by
	 * {@link VisionTrackingBasedShooterArmPIDController#DEFAULT_TOLERANCE}.<br>
	 * <em>This method overrides {@link PIDSubsystem}'s
	 * {@link PIDSubsystem#onTarget onTarget} method as a workaround for
	 * <a href="https://usfirst.collab.net/sf/tracker/do/viewArtifact/projects.wpilib/tracker.4_defects/artf4812">
	 * a bug in WPILib's implementation</a>.</em>
	 * 
	 * @return {@code true} if the error is less than the tolerance.
	 */
	public boolean onTarget() {
		return (Math.abs(getError()) < DEFAULT_TOLERANCE);
	}
}
