
package org.usfirst.frc.team1261.robot;

import org.usfirst.frc.team1261.robot.commands.AutoElevateAlignShoot;
import org.usfirst.frc.team1261.robot.commands.BothMotorIntake;
import org.usfirst.frc.team1261.robot.commands.IntakeArmSetpointDown;
import org.usfirst.frc.team1261.robot.commands.IntakeArmSetpointUp;
import org.usfirst.frc.team1261.robot.commands.PrepareToShoot;
import org.usfirst.frc.team1261.robot.commands.SpikeOutAndIn;
import org.usfirst.frc.team1261.robot.commands.TrackElevateAlign;
import org.usfirst.frc.team1261.robot.triggers.DriverRightTriggerTrigger;
import org.usfirst.frc.team1261.robot.triggers.ManipulatorDPadDownTrigger;
import org.usfirst.frc.team1261.robot.triggers.ManipulatorDPadUpTrigger;
import org.usfirst.frc.team1261.robot.triggers.ManipulatorLeftTriggerTrigger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public static final int DRIVER_JOYSTICK = 0;
	public static final int MANIPULATOR_JOYSTICK = 1;
	
	public static final int BUTTON_A = 1;
	public static final int BUTTON_B = 2;
	public static final int BUTTON_X = 3;
	public static final int BUTTON_Y = 4;
	public static final int BUTTON_LEFT_BUMPER = 5;
	public static final int BUTTON_RIGHT_BUMPER = 6;
	public static final int BUTTON_BACK = 7;
	public static final int BUTTON_START = 8;
	public static final int BUTTON_LEFT_STICK = 9;
	public static final int BUTTON_RIGHT_STICK = 10;

	public static final int AXIS_LEFT_STICK_X = 0;
	public static final int AXIS_LEFT_STICK_Y = 1;
	public static final int AXIS_LEFT_TRIGGER = 2;
	public static final int AXIS_RIGHT_TRIGGER = 3;
	public static final int AXIS_RIGHT_STICK_X = 4;
	public static final int AXIS_RIGHT_STICK_Y = 5;
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	static Joystick driverJoystick = new Joystick(DRIVER_JOYSTICK);
	static Joystick manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK);
	
	Button prepareToShootButton = new JoystickButton(manipulatorJoystick, BUTTON_RIGHT_BUMPER);
	Button trackElevateAlignButton = new JoystickButton(driverJoystick, BUTTON_A);
	Button autoElevateAlignShootButton = new JoystickButton(driverJoystick, BUTTON_B);
	Trigger spikeOutAndInButton = new DriverRightTriggerTrigger(driverJoystick);
	Trigger autoIntakeButton = new ManipulatorLeftTriggerTrigger(manipulatorJoystick);
	Trigger intakeArmSetpointDownButton = new ManipulatorDPadDownTrigger(manipulatorJoystick);
	Trigger intakeArmSetpointUpButton = new ManipulatorDPadUpTrigger(manipulatorJoystick);
	
	public OI() {
		prepareToShootButton.whenPressed(new PrepareToShoot());
		trackElevateAlignButton.toggleWhenPressed(new TrackElevateAlign());
		autoElevateAlignShootButton.toggleWhenPressed(new AutoElevateAlignShoot());
		spikeOutAndInButton.whenActive(new SpikeOutAndIn());
		autoIntakeButton.whileActive(new BothMotorIntake());
		intakeArmSetpointDownButton.whenActive(new IntakeArmSetpointDown());
		intakeArmSetpointUpButton.whenActive(new IntakeArmSetpointUp());
	}
	
	public static Joystick getDriverJoystick() {
		return driverJoystick;
	}
	
	public static Joystick getManipulatorJoystick() {
		return manipulatorJoystick;
	}
}

