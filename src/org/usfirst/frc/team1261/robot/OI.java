
package org.usfirst.frc.team1261.robot;

import org.usfirst.frc.team1261.robot.commands.IntakeArmDown;
import org.usfirst.frc.team1261.robot.commands.IntakeArmUp;
import org.usfirst.frc.team1261.robot.commands.IntakeIn;
import org.usfirst.frc.team1261.robot.commands.IntakeOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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
	
	Joystick driverJoystick = new Joystick(DRIVER_JOYSTICK);
	Joystick manipulatorJoystick = new Joystick(MANIPULATOR_JOYSTICK);
	
	Button intakeInButton = new JoystickButton(driverJoystick, BUTTON_A);
	Button intakeOutButton = new JoystickButton(driverJoystick, BUTTON_B);
	Button intakeArmUpButton = new JoystickButton(driverJoystick, BUTTON_X);
	Button intakeArmDownButton = new JoystickButton(driverJoystick, BUTTON_Y);
	
	public OI() {
		intakeInButton.whileHeld(new IntakeIn());
		intakeOutButton.whileHeld(new IntakeOut());
		intakeArmUpButton.whileHeld(new IntakeArmUp());
		intakeArmDownButton.whileHeld(new IntakeArmDown());
	}
	
	public Joystick getDriverJoystick() {
		return driverJoystick;
	}
	
	public Joystick getManipulatorJoystick() {
		return manipulatorJoystick;
	}
}

