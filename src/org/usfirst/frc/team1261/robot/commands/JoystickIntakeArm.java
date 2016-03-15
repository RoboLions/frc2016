package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.OI;
import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickIntakeArm extends Command {

	public static final Joystick JOYSTICK = OI.getManipulatorJoystick();
	public static final int JOYSTICK_AXIS = OI.AXIS_LEFT_STICK_Y;
	public static final double JOYSTICK_SCALING_FACTOR = -1.0;

	public static final double DEADZONE = 0.25; // 0.25;
	public static final double MAXIMUM_JOYSTICK_INPUT = 1.0;

	public static final double MINIMUM_POWER_FACTOR = 0.8;
	public static final double MAXIMUM_POWER_FACTOR = 1.0;

	// Used for calculating the scaling factor for the joystick to motor power
	// function.
	public static final double SLOPE = (MAXIMUM_POWER_FACTOR - MINIMUM_POWER_FACTOR)
			/ (MAXIMUM_JOYSTICK_INPUT - DEADZONE);

	public JoystickIntakeArm() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intakeArm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.intakeArm.stop();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double joystickInput = JOYSTICK.getRawAxis(JOYSTICK_AXIS) * JOYSTICK_SCALING_FACTOR;
		if (joystickInput > DEADZONE) {
			Robot.intakeArm.setIntakeArmMotorPower((joystickInput - DEADZONE) * SLOPE + MINIMUM_POWER_FACTOR);
		} else if (joystickInput < -DEADZONE) {
			Robot.intakeArm.setIntakeArmMotorPower((joystickInput + DEADZONE) * SLOPE - MINIMUM_POWER_FACTOR);
		} else {
			Robot.intakeArm.stop();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intakeArm.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
