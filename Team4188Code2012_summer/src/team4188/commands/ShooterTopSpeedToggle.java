/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.CorpsLog;
import team4188.RobotMap;
import team4188.subsystems.Shooter;

/**
 *
 * @author Erin
 */
public class ShooterTopSpeedToggle extends CommandBase {
    
    public ShooterTopSpeedToggle() {}

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        /*
        if(RobotMap.getShooterSpeed() == Shooter.SpeedMode.kLow) {
            RobotMap.setShooterSpeed(Shooter.SpeedMode.kHigh);
            CorpsLog.log("Shooter Mode","High",true,false);
        }
        else if(RobotMap.getShooterSpeed() == Shooter.SpeedMode.kHigh) {
            RobotMap.setShooterSpeed(Shooter.SpeedMode.kLow);
            CorpsLog.log("Shooter Mode","Low",true,false);
        }*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
