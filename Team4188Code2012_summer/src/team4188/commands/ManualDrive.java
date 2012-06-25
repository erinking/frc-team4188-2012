/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.RobotMap;

/**
 * @author Erin
 */
public class ManualDrive extends CommandBase {
    private double throttle;
    
    public ManualDrive() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.setMaxVoltages(12.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        RobotMap.setPilotMode("Manual Driving");
        // **********PILOT CONTROLS**********
        // Pilot Joystick controls drivetrain, encoders, line followers, & gyro.

        // Throttle from the joystick.
        throttle = ((oi.pilotStick.getThrottle()*0.6)+0.4);
        
        if (!oi.pilotStick.isDead())
            drivetrain.drive((oi.pilotStick.getY()*throttle),
            (oi.pilotStick.getX()*throttle), true, RobotMap.getDriveReverse());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
