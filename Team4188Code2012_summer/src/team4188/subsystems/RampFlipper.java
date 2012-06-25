/*
 * RampFlipper class.
 * Defines the components and methods of the ramp-flipping mechanism on
 * Team 4188's robot for the 2012 FIRST Robotics game Rebound Rumble.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.CorpsLog;
import team4188.RobotMap;
import team4188.commands.FlipperDoNothing;

/**
 * @author Erin
 */
public class RampFlipper extends Subsystem {
    private Compressor compressor;
    private DoubleSolenoid flipper;
    //private DigitalInput extended, retracted;
    private static final double PNEUMATIC_DELAY_SECONDS = 0.1;

    public void initDefaultCommand() {
        setDefaultCommand(new FlipperDoNothing());
    }
    
    public void init() {
        CorpsLog.log("RampFlipper Status","initializing",false,true);
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH,RobotMap.COMPRESSOR_RELAY);
        compressor.start();
        //solenoid to flip the ramp on solenoid breakout module.
        flipper = new DoubleSolenoid(RobotMap.FLIPPER_SOLENOID_FORWARD,
                RobotMap.FLIPPER_SOLENOID_REVERSE);
        //extended = new DigitalInput(RobotMap.PNEUMATIC_EXTENDED_SENSOR);
        //retracted = new DigitalInput(RobotMap.PNEUMATIC_RETRACTED_SENSOR);
        CorpsLog.log("RampFlipper Status","initialized",false,true);
    }
    
    public void flip() {
        flipper.set(DoubleSolenoid.Value.kForward);     // Flip the ramp.
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        flipper.set(DoubleSolenoid.Value.kOff);         // Release power.
    }
    
    public void letGo() {
        flipper.set(DoubleSolenoid.Value.kReverse);     // Retract the piston.
        Timer.delay(PNEUMATIC_DELAY_SECONDS);
        flipper.set(DoubleSolenoid.Value.kOff);         // Release power.
    }
    
    public void doNothing() {
        flipper.set(DoubleSolenoid.Value.kOff);
    }
    /*
    public String getDirection() {
        if (extended.get()) return "extended";
        else if (retracted.get()) return "retracted";
        else return "neither extended nor retracted"; 
    }*/
    
    public boolean getPressureSwitch() {
        return compressor.getPressureSwitchValue();
    }
    
    public boolean getEnabled() {
        return compressor.enabled();
    }
}
