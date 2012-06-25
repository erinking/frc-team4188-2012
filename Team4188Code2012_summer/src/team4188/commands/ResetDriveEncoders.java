/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

/**
 *
 * @author Erin
 */
public class ResetDriveEncoders extends CommandBase {
    private boolean reset;
    
    public ResetDriveEncoders() {}

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.resetEncoders();
        reset = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return reset;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
