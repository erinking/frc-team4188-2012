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
public class ManualAim extends CommandBase {
    
    public ManualAim() {
        //requires(turret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    // **********CO-PILOT CONTROLS**********
    // Co-Pilot Joystick controls shooter.
        // Move Y-axis to tilt.
        RobotMap.setCopilotMode("Manual Aiming");
        oi.copilotStick.setXScale(RobotMap.getPrevCopilotXScale());             // set the scale to the previous scale.
        //if (!oi.copilotStick.isDead())
            //turret.manualAimTilt(oi.copilotStick.getY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        //RobotMap.setCurrentAngle(turret.getAbsoluteAngle());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        //RobotMap.setCurrentAngle(turret.getAbsoluteAngle());
    }
}
