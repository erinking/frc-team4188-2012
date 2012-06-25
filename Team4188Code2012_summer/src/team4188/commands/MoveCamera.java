/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.RobotMap;

/**
 *
 * @author Erin
 */
public class MoveCamera extends CommandBase {
    private int direction;
    
    public MoveCamera(int newDirection) {
        requires(vision);
        direction = newDirection;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    /**
     * @param direction 0 for backwards, 1 for forwards.
     */
    protected void execute() {
        // FORWARDS camera control.
        if (direction == 1)
        {
            RobotMap.setCopilotMode("FORWARD CAMERA CONTROL");
            RobotMap.setPrevCopilotXScale(oi.copilotStick.getXScale()); // retain the previous scale.
            oi.copilotStick.setXScale(1);                               // set the scale to 1.
            //vision.steerForward(oi.copilotStick.getX(), oi.copilotStick.getY());
        }
        // REVERSE camera control.
        else if(direction == 0)
        {
            RobotMap.setCopilotMode("REVERSE CAMERA CONTROL");
            RobotMap.setPrevCopilotXScale(oi.copilotStick.getXScale()); // retain the previous scale.
            oi.copilotStick.setXScale(1);                               // set the scale to 1.
            //vision.steerReverse(oi.copilotStick.getX(), oi.copilotStick.getY());
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
