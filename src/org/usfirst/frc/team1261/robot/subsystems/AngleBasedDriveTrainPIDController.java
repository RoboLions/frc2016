package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * An angle-based {@link DriveTrain} {@link PIDController}.
 */
class AngleBasedDriveTrainPIDController extends PIDController {

	public static final double kP = 0.001;
	public static final double kI = 0.0;
	public static final double kD = 0.0;
	public static final double DEFAULT_TOLERANCE = 10.0;

	public AngleBasedDriveTrainPIDController(DriveTrain driveTrain) {
		super(kP, kI, kD, new DisplacementPIDSource() {
			@Override
			public double pidGet() {
				return driveTrain.getAngle();
			}
		}, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				driveTrain.turn(output);
			}
		});
		setAbsoluteTolerance(DEFAULT_TOLERANCE);
	}
	
	/**
	 * Return {@code true} if the error is within the tolerance determined by
	 * {@link AngleBasedDriveTrainPIDController#DEFAULT_TOLERANCE}.<br>
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
