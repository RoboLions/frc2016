package org.usfirst.frc.team1261.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class ManipulatorDPadLeftTrigger extends Trigger {

	public static final int POV_INDEX = 0;
	public static final int POV_ANGLE = 270;
	
	private final Joystick joystick;

	public ManipulatorDPadLeftTrigger(Joystick joystick) {
		this.joystick = joystick;
	}

	public boolean get() {
		return joystick.getPOV(POV_INDEX) == POV_ANGLE;
	}
}
