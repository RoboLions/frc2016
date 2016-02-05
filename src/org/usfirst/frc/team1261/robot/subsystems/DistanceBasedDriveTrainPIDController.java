package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * A distance-based {@link DriveTrain} {@link PIDController}.
 */
class DistanceBasedDriveTrainPIDController extends PIDController {

	public static final double kP = 0.01;
	public static final double kI = 0.0;
	public static final double kD = 0.0;

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
	}
}
