package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoElevateAlign extends Command {

	public static final double X_AXIS_TARGET = 0.0;
	public static final double Y_AXIS_TARGET = 0.0;

    public AutoElevateAlign() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	requires(Robot.shooterArm);
    }

 // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.setPIDController(DriveTrain.DriveTrainPIDController.VISION_TRACK);
		Robot.driveTrain.setSetpoint(X_AXIS_TARGET);
    	Robot.shooterArm.setPIDController(ShooterArm.ShooterArmPIDController.VISION_TRACK);
    	Robot.shooterArm.setSetpoint(Y_AXIS_TARGET);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.onTarget() && Robot.shooterArm.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.shooterArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
