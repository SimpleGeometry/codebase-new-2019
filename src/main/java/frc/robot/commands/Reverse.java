package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.AnalogGyro;


public class Reverse extends Command {


    public Reverse() {
       
    }        
    // Called just before this Command runs the first time
            
    @Override
    protected void initialize() {
        //SmartDashboard.putNumber("Interrupt: ", 0);
    }
        
    // Called repeatedly when this Command is scheduled to run
    
    @Override
    protected void execute() {
        Robot.m_drivetrain.reverse = !Robot.m_drivetrain.reverse;

        
            
        // //SmartDashboard.putNumber("Encoder 1: ", e1.getDistance());
        // //SmartDashboard.putNumber("Encoder 2: ", e2.getDistance());
        // SmartDashboard.putNumber("leftEncoder Pos", Robot.leftTalon.getSelectedSensorPosition(0));
        // SmartDashboard.putNumber("RightEncoder Pos", Robot.rightTalon.getSelectedSensorPosition(1));
        // SmartDashboard.putNumber("Gyro Output Angle: ", gyro.getAngle());

        // //alternative drive mode, can't go backwards
        // //Robot.m_drivetrain.arcadeDrive(Robot.m_oi.controllerOne.getRawAxis(3), Robot.m_oi.controllerOne.getRawAxis(0)* 0.5);
        
    }
       
    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
     return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
      
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {

    }
}