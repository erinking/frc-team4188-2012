/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.CorpsLog;
import team4188.RobotMap;

/**
 *
 * @author Erin
 */
public class SwitchDirection extends CommandBase {
    private String direction = "Forward";
    
    public SwitchDirection() {}

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if((RobotMap.getDriveReverse()) == true) {
            RobotMap.setDriveReverse(false);
            direction = "Forward";
        }
        else if((RobotMap.getDriveReverse()) == false) {
            RobotMap.setDriveReverse(true);
            direction = "Reverse";
        }
        CorpsLog.log("Direction",direction,true,false);
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
