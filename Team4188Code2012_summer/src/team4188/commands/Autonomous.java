/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author Erin
 */
public class Autonomous extends CommandGroup {
    public Autonomous() {
        /*
        //addSequential(new ResetDriveEncoders());
        //addSequential(new ResetGyro());
        addSequential(new AutoAim());
        addParallel(new Backspin());
        addSequential(new Wait(3.0));
        addSequential(new Shoot());
        //addSequential(new Wait(3.0));
        //addSequential(new ResetGyro());
        addSequential(new AutoAim());
        //addParallel(new Backspin());        //necessary?
        //addSequential(new Intake());
        //addSequential(new Wait(1.0));
        addSequential(new Shoot());
        addSequential(new ShooterOff());
        
        //addSequential(new Wait(1.0));
        ////addSequential(new RetrieverOff());
        //addSequential(new DriveForward(2.0)); //correct distance to bridge?
        //addSequential(new FlipRamp());
        */
        
        addSequential(new Wait(5.0));
        addSequential(new Eject());
        //addSequential(new Wait(8.0));
        //addSequential(new DriveForward(-0.5));
        //addSequential(new TurnAround());
        
        //addSequential(new DriveForward(2.0)); //correct distance to bridge?
        //addSequential(new FlipRamp());
        
    }
}
