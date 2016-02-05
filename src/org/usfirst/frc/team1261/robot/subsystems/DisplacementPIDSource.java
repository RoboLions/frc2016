package org.usfirst.frc.team1261.robot.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * An {@code abstract} class that implements {@link PIDSource} whose
 * {@link PIDSourceType} is {@link PIDSourceType#kDisplacement}.
 */
abstract class DisplacementPIDSource implements PIDSource {
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}
}
