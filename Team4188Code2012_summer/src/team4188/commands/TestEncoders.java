/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

/**
 *
 * @author Erin
 */
public class TestEncoders extends CommandBase {
    boolean doneYet;
    double distance = 0.1;
    
    public TestEncoders() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        //drivetrain.resetEncoders();
        drivetrain.setMaxVoltages(6.0);
        doneYet = drivetrain.driveDistance(distance);        //1.0 m...?
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        doneYet = drivetrain.thereYet(distance);               
    }
    
    public boolean doneYet() {
        return doneYet;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return doneYet;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
