package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A vision-tracking-based {@link DriveTrain} {@link PIDController}.
 */
class VisionTrackingBasedDriveTrainPIDController extends PIDController {

	// TODO: figure out these values
	public static final double kP = 0.0015;
	public static final double kI = 0.0001;
	public static final double kD = 0.0;
	public static final double DEFAULT_TOLERANCE = RaspberryPiCommunicationAdapter.X_AXIS_TOLERANCE;

	/**
	 * Error value used for PID when no target can be found.
	 */
	public static final double DEFAULT_ERROR = 0.0;

	public VisionTrackingBasedDriveTrainPIDController(DriveTrain driveTrain) {
		super(kP, kI, kD, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				try {
					double targetXOffset = RaspberryPiCommunicationAdapter.getTargetXOffset();
					SmartDashboard.putNumber("targetXOffset", targetXOffset);
					SmartDashboard.putBoolean("Contours found", true);
					return targetXOffset;
				} catch (RaspberryPiCommunicationAdapter.NoContoursFoundException e) {
					SmartDashboard.putNumber("targetXOffset", DEFAULT_ERROR);
					SmartDashboard.putBoolean("Contours found", false);
					return DEFAULT_ERROR;
				}
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				driveTrain.turn(output);
				SmartDashboard.putNumber("Output to driveTrain.turn", output);
			}
		});
		setAbsoluteTolerance(DEFAULT_TOLERANCE);
	}

	/**
	 * Return {@code true} if the error is within the tolerance determined by
	 * {@link VisionTrackingBasedDriveTrainPIDController#DEFAULT_TOLERANCE}.<br>
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
