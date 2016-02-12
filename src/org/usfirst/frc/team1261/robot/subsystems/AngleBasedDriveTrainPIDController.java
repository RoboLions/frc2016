package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

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
}
