package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TeleOpCommands extends CommandGroup {

    public static DriveTrainControl drive;
    public TeleOpCommands() {
        addParallel(new DriveTrainControl());
        //addParallel(new Cameratest());
       //addParallel(new LinearSlideControl());
       //addParallel(new IntakeControl());
       //addParallel(new ClawsControl());
       //addParallel(new PlatformControl());
       //addParallel(new GripperControl());
    }
    public void end() {
        drive.end();
    }
}