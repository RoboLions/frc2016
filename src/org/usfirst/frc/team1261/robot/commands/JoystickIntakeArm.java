package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.OI;
import org.usfirst.frc.team1261.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickIntakeArm extends Command {
	
	public static final Joystick JOYSTICK = Robot.oi.getManipulatorJoystick();
	public static final int JOYSTICK_AXIS = OI.AXIS_RIGHT_STICK_Y;

	public static final double POWER_SCALING_FACTOR = -1.0;

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
    	Robot.intakeArm.setIntakeArmMotorPower(JOYSTICK.getRawAxis(JOYSTICK_AXIS) * POWER_SCALING_FACTOR);
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
