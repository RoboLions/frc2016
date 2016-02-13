package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * A distance-based {@link DriveTrain} {@link PIDController}.
 */
class DistanceBasedDriveTrainPIDController extends PIDController {

	public static final double kP = 0.001;
	public static final double kI = 0.00002;
	public static final double kD = 0.0;
	public static final double DEFAULT_TOLERANCE = 10.0;

	public DistanceBasedDriveTrainPIDController(DriveTrain driveTrain) {
		super(kP, kI, kD, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				return driveTrain.distanceTraveled();
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				driveTrain.drive(output);
			}
		});
		setAbsoluteTolerance(DEFAULT_TOLERANCE);
	}
	
	/**
	 * Return {@code true} if the error is within the tolerance determined by
	 * {@link DistanceBasedDriveTrainPIDController#DEFAULT_TOLERANCE}.<br>
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
