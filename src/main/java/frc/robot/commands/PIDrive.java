package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDrive extends Command {
    public double targetTicks;
    public double diameter = 6; // in inches
    
    double P = 0.00007;
    double I = 0;
    double D = 0;
    double integral, previous_error, error, derivative = 0;
    double dt = 0.02;
    // max estimated maybe 10 feet per second?
    // 1 inch per second is roughly 20 ticks per 100 ms
    double completionThreshold = 100; // in ticks (each tick is 0.005 inches roughly, with circumference = ~19 inches)
    double ff = 0.1;
    //ff = feedforward: A known value supplied to the output as a guesstimate so the PID only has to make minor corrections.

    double maxVoltage = 0.35 + ff;

    int itersUnderThreshold = 0;
    int itersComplete = 20;
    
    //m_drivetrain is a drivetrain subsystem btw
    public PIDrive(double targetInches) {
        targetTicks = 4096 * targetInches/(diameter*Math.PI);
        //SmartDashboard.putNumber("Running: ", 2);
        requires(Robot.m_drivetrain);
    }

    protected void initialize() {
        //SmartDashboard.putNumber("Running: ", 1);
        Robot.m_drivetrain.move(0, 0);
        Robot.leftTalon.setSelectedSensorPosition(0);
        Robot.rightTalon.setSelectedSensorPosition(0);
        SmartDashboard.putString("RAN: ", "No");
    }
    
    protected void execute() {
        //SmartDashboard.putNumber("Running: ", 0);
        double leftTicks = Robot.leftTalon.getSelectedSensorPosition(0); // adjusted for the encoder flipped on left
        double rightTicks = -Robot.rightTalon.getSelectedSensorPosition(0);
        double avgTicks = (leftTicks + rightTicks) / 2;
        error = targetTicks - avgTicks; // Error = Target - Actual
        integral += (error * dt); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (error - previous_error) / dt;
        previous_error = error;

        double voltage = (P * error + I * this.integral + D * derivative);
        voltage += (voltage > 0 ? ff : -ff);
        if(Math.abs(voltage) >= maxVoltage){
            voltage = Math.signum(voltage) * maxVoltage;
        }
        Robot.m_drivetrain.moveWithCurve(voltage, 0, true);

        PIDebug(voltage, leftTicks, rightTicks, avgTicks, targetTicks, integral, error, derivative);
        
        if(Math.abs(error) <= completionThreshold){
            itersUnderThreshold++;
        }
        else{
            itersUnderThreshold = 0;
        }

    }

    protected boolean isFinished() {
        //SmartDashboard.putString("RAN: ", "Run");
        return itersUnderThreshold >= itersComplete;
    }
    
    protected void end() {
        Robot.leftTalon.setSelectedSensorPosition(0);
        Robot.rightTalon.setSelectedSensorPosition(0);
    	Robot.m_drivetrain.move(0, 0);
    }
    protected void PIDebug(double voltage1, double leftTicks1, double rightTicks1, double avgTicks1, double targetTicks1, double integral1, double error1, double derivative1 ) {
        SmartDashboard.putNumber("Encoder Voltage percentage: ", voltage1);
        SmartDashboard.putNumber("Left Encoder Ticks: ", leftTicks1);
        SmartDashboard.putNumber("Right Encoder Ticks: ", rightTicks1);
        SmartDashboard.putNumber("Average Ticks: ", avgTicks1);
        SmartDashboard.putNumber("Target Ticks: ", targetTicks1);
        SmartDashboard.putNumber("Encoder Error Integral: ", integral1);
        SmartDashboard.putNumber("Encoder Error: ", error1);
        SmartDashboard.putNumber("Encoder Error Derivative: ", derivative1);
    }
    public void debug() {
        SmartDashboard.putNumber("left velocity target:", Robot.leftTalon.getClosedLoopTarget());
        SmartDashboard.putNumber("left velocity error:", Robot.leftTalon.getClosedLoopError());
        SmartDashboard.putNumber("left velocity current:", Robot.leftTalon.getSelectedSensorVelocity());

        SmartDashboard.putNumber("right velocity target:", Robot.rightTalon.getClosedLoopTarget());
        SmartDashboard.putNumber("right velocity error:", Robot.rightTalon.getClosedLoopError());
        SmartDashboard.putNumber("right velocity current:", Robot.rightTalon.getSelectedSensorVelocity());

        SmartDashboard.putNumber("Velocity1:", Robot.rightTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Velocity2:", Robot.leftTalon.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Pos:", Robot.rightTalon.getSelectedSensorPosition());

        
    }
    protected void interrupted() {

    }
}