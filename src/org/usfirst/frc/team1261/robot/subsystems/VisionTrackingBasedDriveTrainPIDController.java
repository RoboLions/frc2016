package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * A vision-tracking-based {@link DriveTrain} {@link PIDController}.
 */
class VisionTrackingBasedDriveTrainPIDController extends PIDController {

	// TODO: figure out these values
	public static final double kP = 0.0015;
	public static final double kI = 0.0001;
	public static final double kD = 0.0;
	public static final double DEFAULT_TOLERANCE = RaspberryPiCommunicationAdapter.X_AXIS_TOLERANCE;

	public static final double OUTPUT_THRESHOLD = 0.2;

	/**
	 * Error value used for PID when no target can be found.
	 */
	public static final double DEFAULT_ERROR = 0.0;

	public VisionTrackingBasedDriveTrainPIDController(DriveTrain driveTrain) {
		super(kP, kI, kD, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				try {
					return RaspberryPiCommunicationAdapter.getTargetXOffset();
				} catch (RaspberryPiCommunicationAdapter.NoContoursFoundException e) {
					return DEFAULT_ERROR;
				}
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				if (Math.abs(output) <= OUTPUT_THRESHOLD) {
					output = Math.signum(output) * OUTPUT_THRESHOLD;
				}
				driveTrain.turn(output);
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
		return (RaspberryPiCommunicationAdapter.isContourFound() && Math.abs(getError()) < DEFAULT_TOLERANCE);
	}
}
