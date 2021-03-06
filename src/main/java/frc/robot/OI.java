/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public Joystick controllerOne = new Joystick(0);
	public Button boost = new JoystickButton(controllerOne, 6); 
  public Button autoAlignButton = new JoystickButton(controllerOne, 4); // Y 
  public Button setReverse = new JoystickButton(controllerOne, 3); // X
  public Button toggleCoast = new JoystickButton(controllerOne, 1); // A
  public Button toggleBrake = new JoystickButton(controllerOne, 2); // B
  public Button cancel = new JoystickButton(controllerOne, 10); // click the right joystick
  public Button turn180 = new JoystickButton(controllerOne, 9); // click the left joystick
  public Joystick controllerTwo = new Joystick(1); //also an xbox
  public Button hatchButton = new JoystickButton(controllerTwo, 4); // Y
  public Button flashcolor = new JoystickButton(controllerTwo, 1); // A
  public Button hatchleverup = new JoystickButton(controllerTwo, 3); // X
  public Button hatchleverdown = new JoystickButton(controllerTwo, 2); // B
  // cargo turning is axis/trigger control in the TurnCargo command

  public OI() {
    autoAlignButton.whenPressed(new VisionProcess());
    setReverse.whenPressed(new Reverse());
    toggleCoast.whenPressed(new ToggleCoastMode());
    toggleBrake.whenPressed(new ToggleBrakeMode());
    cancel.whenPressed(new Stop());
    turn180.whenPressed(new PIDturn(180));

    //hatchleverup.whenPressed(new HatchTurnUp());
    //hatchleverdown.whenPressed(new HatchTurnDown());
    hatchButton.whenPressed(new HatchActivate());
    flashcolor.whenPressed(new Flash());
  }
}
