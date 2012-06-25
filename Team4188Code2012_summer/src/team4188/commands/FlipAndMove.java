/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.CorpsLog;

/**
 * @author Erin
 */
public class FlipAndMove extends CommandBase {
    private boolean moved, movedAgain, flipped, letGo, movedThird;
    private static final double FLIP_DELAY_SECONDS = 0.3;
    private String status = "start";
    
    public FlipAndMove() {
        requires(rampFlipper);
        //requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(!moved) {
            moved = drivetrain.driveDistance(-0.07);                            // Drive backwards 7 cm
            status = "backing up";
        }
        else if(moved) {
            if(!flipped) {
                rampFlipper.flip();                                             // Karate Chop
                flipped = true;
                status = "flipping";
            }
            else if(flipped && !movedAgain) {
                movedAgain = drivetrain.driveDistance(0.20);                    // Drive forward 20 cm
                status = "moving up";
            }
            else if(movedAgain) {
                if(!letGo) {
                    rampFlipper.letGo();                                        // Retract arm
                    letGo = true;
                    status = "letting go";
                }
                else if (letGo && !movedThird) {
                    movedThird = drivetrain.driveDistance(0.10);                // Drive forward 10 cm
                    status = "moving up more";
                }
                else status = "done";
            }
        }
        CorpsLog.log("FlipRamp status",status,true,true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return movedThird;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
