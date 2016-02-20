package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.OI;
import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickDrive extends Command {

	public static final Joystick JOYSTICK = Robot.oi.getDriverJoystick();
	public static final int THROTTLE_AXIS = OI.AXIS_LEFT_STICK_Y;
	public static final int ROTATE_AXIS = OI.AXIS_RIGHT_STICK_X;
	public static final int TURBO_BUTTON = OI.BUTTON_RIGHT_BUMPER;
	public static final boolean SQUARED_INPUTS = true;

	public static final double SCALING_FACTOR = 0.75;

	private double correctedScalingFactor;

	public JoystickDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		if (SQUARED_INPUTS) {
			correctedScalingFactor = Math.sqrt(SCALING_FACTOR);
		} else {
			correctedScalingFactor = SCALING_FACTOR;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.disablePIDController();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double throttleValue = JOYSTICK.getRawAxis(THROTTLE_AXIS);
		double rotateValue = JOYSTICK.getRawAxis(ROTATE_AXIS);
		if (!JOYSTICK.getRawButton(TURBO_BUTTON)) {
			throttleValue *= correctedScalingFactor;
			rotateValue *= correctedScalingFactor;
		}
		Robot.driveTrain.getRobotDrive().arcadeDrive(throttleValue, rotateValue, SQUARED_INPUTS);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
