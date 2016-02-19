package org.usfirst.frc.team1261.robot.triggers;

import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class ManipulatorDPadRightTrigger extends Trigger {

	public static final Joystick JOYSTICK = Robot.oi.getManipulatorJoystick();
	public static final int POV_INDEX = 0;
	public static final int POV_ANGLE = 90;

	public boolean get() {
		return JOYSTICK.getPOV(POV_INDEX) == POV_ANGLE;
	}
}
