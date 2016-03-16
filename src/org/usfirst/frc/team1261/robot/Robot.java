// NOTE TO ALL FUTURE PROGRAMMERS LOOKING AT THIS CODE:
// I'm sorry.
// -Rishov

package org.usfirst.frc.team1261.robot;

import org.usfirst.frc.team1261.robot.commands.ReachAutonomousProgram;
import org.usfirst.frc.team1261.robot.commands.SimpleAutonomousProgram;
import org.usfirst.frc.team1261.robot.commands.ZeroAngle;
import org.usfirst.frc.team1261.robot.commands.ZeroIntakeArmEncoder;
import org.usfirst.frc.team1261.robot.commands.ZeroShooterArmEncoder;
import org.usfirst.frc.team1261.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1261.robot.subsystems.Flywheel;
import org.usfirst.frc.team1261.robot.subsystems.IntakeArm;
import org.usfirst.frc.team1261.robot.subsystems.IntakeRoller;
import org.usfirst.frc.team1261.robot.subsystems.ShooterArm;
import org.usfirst.frc.team1261.robot.subsystems.SpikePuncher;
import org.usfirst.frc.team1261.robot.subsystems.VisionTrackingLED;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final int IMAGE_QUALITY = 50;
	public static final String CAMERA_ID = null;

	public static DriveTrain driveTrain;
	public static IntakeArm intakeArm;
	public static IntakeRoller intakeRoller;
	public static Flywheel flywheel;
	public static ShooterArm shooterArm;
	public static SpikePuncher spikePuncher;
	public static VisionTrackingLED visionTrackingLED;

	public static OI oi;

	Command autonomousCommand;
	SendableChooser chooser;

	public static boolean isAlignedVert = true;

	SendableChooser startingPositionChooser = new SendableChooser();
	SendableChooser endingPositionChooser = new SendableChooser();
	SendableChooser defenseTypeChooser = new SendableChooser();
	SendableChooser shotLocationChooser = new SendableChooser();

	CameraServer server;

	public Robot() {
		RobotMap.init();

		driveTrain = new DriveTrain();
		intakeArm = new IntakeArm();
		intakeRoller = new IntakeRoller();
		flywheel = new Flywheel();
		shooterArm = new ShooterArm();
		spikePuncher = new SpikePuncher();
		visionTrackingLED = new VisionTrackingLED();

		oi = new OI();
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		// chooser = new SendableChooser();
		// chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		// SmartDashboard.putData("Auto mode", chooser);

		startingPositionChooser.addDefault("Left", "Left");
		startingPositionChooser.addObject("Left-Center", "Left-Center");
		startingPositionChooser.addObject("Center", "Center");
		startingPositionChooser.addObject("Right-Center", "Right-Center");
		startingPositionChooser.addObject("Right", "Right");

		endingPositionChooser.addDefault("Left", "Left");
		endingPositionChooser.addObject("Left-Center", "Left-Center");
		endingPositionChooser.addObject("Center", "Center");
		endingPositionChooser.addObject("Right-Center", "Right-Center");
		endingPositionChooser.addObject("Right", "Right");

		defenseTypeChooser.addDefault("Low Bar", "Low Bar");
		defenseTypeChooser.addObject("Portcullis", "Portcullis");
		defenseTypeChooser.addObject("Cheval-de-Frise", "Cheval-de-Frise");
		defenseTypeChooser.addObject("Moat", "Moat");
		defenseTypeChooser.addObject("Ramparts", "Ramparts");
		defenseTypeChooser.addObject("Rock Wall", "Rock Wall");
		defenseTypeChooser.addObject("Rough Terrain", "Rough Terrain");

		shotLocationChooser.addDefault("Middle-Long", "Middle-Long");
		shotLocationChooser.addObject("Middle-Short", "Middle-Short");
		shotLocationChooser.addObject("Side-Long", "Side-Long");

		SmartDashboard.putData("Starting Position Chooser", startingPositionChooser);
		SmartDashboard.putData("Shot Location Chooser", shotLocationChooser);
		SmartDashboard.putData("Ending Position Chooser", endingPositionChooser);
		SmartDashboard.putData("Defense Type Chooser", defenseTypeChooser);

		SmartDashboard.putData(new ZeroAngle());
		SmartDashboard.putData(new ZeroShooterArmEncoder());
		SmartDashboard.putData(new ZeroIntakeArmEncoder());

		SmartDashboard.putBoolean("Autonomous enabled", true);
		SmartDashboard.putBoolean("ONLY REACH DEFENSES", false);
		
		SmartDashboard.putBoolean("Override Shooter Limit Switch", false);

		if (CAMERA_ID != null) {
			server = CameraServer.getInstance();
			server.setQuality(IMAGE_QUALITY);
			server.startAutomaticCapture(CAMERA_ID);
		}
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		// autonomousCommand = (Command) chooser.getSelected();
		if (SmartDashboard.getBoolean("Autonomous enabled", true)) {
			if(SmartDashboard.getBoolean("ONLY REACH DEFENSES", false)){
				autonomousCommand = new ReachAutonomousProgram();
			}
			else{
				autonomousCommand = new SimpleAutonomousProgram();
			}
		} else {
			autonomousCommand = null;
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
		allPeriodic();
	}

	/**
	 * This function is called periodically during all modes
	 */
	public void allPeriodic() {
		SmartDashboard.putNumber("Shooter arm encoder", Robot.shooterArm.getAngle());
		SmartDashboard.putNumber("Intake arm encoder", Robot.intakeArm.getAngle());
		SmartDashboard.putNumber("Rangefinder voltage", RobotMap.rangeFinder.getVoltage());
		SmartDashboard.putNumber("navX yaw", RobotMap.navX.getYaw());
		SmartDashboard.putBoolean("Shooter arm lower limit", Robot.shooterArm.isLowerLimitSwitchHit());
		SmartDashboard.putBoolean("Intake arm lower limit", Robot.intakeArm.isLowerLimitSwitchHit());
		SmartDashboard.putBoolean("Intake arm upper limit", Robot.intakeArm.isUpperLimitSwitchHit());
		SmartDashboard.putBoolean("navX connected", RobotMap.navX.isConnected());
		SmartDashboard.putBoolean("navX calibrating", RobotMap.navX.isCalibrating());
	}
}
