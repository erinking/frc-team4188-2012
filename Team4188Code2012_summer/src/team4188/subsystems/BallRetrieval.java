/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.CorpsLog;
import team4188.RobotMap;
import team4188.commands.RetrieverOff;

/**
 * @author Erin
 */
public class BallRetrieval extends Subsystem {
    private Relay power;
    //private boolean intakeOn;

    public void initDefaultCommand() {
        setDefaultCommand(new RetrieverOff());
    }
    
    public void init() {
        CorpsLog.log("BallRetrieval Status","initializing",false,true);
        power = new Relay(RobotMap.BALL_RETRIEVAL_RELAY);
        CorpsLog.log("BallRetrieval Status","initialized",false,true);
    }
    
    public void off() {
        power.set(Relay.Value.kOff);
        //intakeOn = false;
    }
    
    public void intake() {
        power.set(Relay.Value.kReverse);
        //intakeOn = true;
    }
    
    public void eject() {
        power.set(Relay.Value.kForward);
        //intakeOn = false;
    }
    
    /*public boolean intakeOn() {
        return intakeOn;
    }*/
}
