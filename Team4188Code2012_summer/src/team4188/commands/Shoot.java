/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Erin
 */
public class Shoot extends CommandBase {
    private boolean doneYet = false;//, intakeWasOn;
    private static final double TRANSFER_DELAY_SECONDS = 0.7;
    
    public Shoot() {
        requires(ballRetrieval);
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        shooter.transferOn();
        //intakeWasOn = ballRetrieval.intakeOn();
        //if(!intakeWasOn)
            ballRetrieval.intake();
        Timer.delay(TRANSFER_DELAY_SECONDS);
        shooter.transferOff();
        //if(!intakeWasOn)
            ballRetrieval.off();
        doneYet = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return doneYet;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}