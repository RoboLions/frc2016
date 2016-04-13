package org.usfirst.frc.team1261.robot.commands;

import org.usfirst.frc.team1261.robot.Robot;
import org.usfirst.frc.team1261.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;

import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoElevateAlign extends Command {

	public static final double X_AXIS_TARGET = 0.0;
	public static final double Y_AXIS_TARGET = 0.0;
	public static final double MINIMUM_ON_TARGET_DURATION = 0.25;

	private static final double MINIMUM_ON_TARGET_DURATION_MICROSECONDS = MINIMUM_ON_TARGET_DURATION * 1000000;
	private boolean onTarget = false;
	private long timeWhenReachedTarget = 0;

    public AutoElevateAlign() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	requires(Robot.shooterArm);
    }

 // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Starting auto-shot...");
    	Robot.driveTrain.setPIDController(DriveTrain.DriveTrainPIDController.VISION_TRACK);
		Robot.driveTrain.setSetpoint(X_AXIS_TARGET);
    	Robot.shooterArm.setPIDController(ShooterArm.ShooterArmPIDController.VISION_TRACK);
    	Robot.shooterArm.setSetpoint(Y_AXIS_TARGET);
    	onTarget = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.driveTrain.onTarget() && Robot.shooterArm.onTarget()) {
    		if (!onTarget) {
    			timeWhenReachedTarget = Utility.getFPGATime();
    		}
    		onTarget = true;
    	} else {
    		onTarget = false;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return (onTarget && Utility.getFPGATime() - timeWhenReachedTarget >= MINIMUM_ON_TARGET_DURATION_MICROSECONDS);
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
