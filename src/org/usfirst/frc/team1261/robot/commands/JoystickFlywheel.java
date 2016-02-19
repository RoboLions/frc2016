package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.OI;
import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickFlywheel extends Command {

	public static final Joystick JOYSTICK = Robot.oi.getManipulatorJoystick();
	public static final int OUT_JOYSTICK_AXIS = OI.AXIS_RIGHT_TRIGGER;
	public static final int IN_JOYSTICK_AXIS = OI.AXIS_LEFT_TRIGGER;

	public static final double OUT_POWER_SCALING_FACTOR = 1.0;
	public static final double IN_POWER_SCALING_FACTOR = 0.35;

	public JoystickFlywheel() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.flywheel);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.flywheel.stop();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.flywheel.setFlywheelPower((JOYSTICK.getRawAxis(OUT_JOYSTICK_AXIS) * OUT_POWER_SCALING_FACTOR)
				- (JOYSTICK.getRawAxis(IN_JOYSTICK_AXIS) * IN_POWER_SCALING_FACTOR));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.flywheel.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
