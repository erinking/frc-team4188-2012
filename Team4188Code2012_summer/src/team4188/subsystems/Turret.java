/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.AS5030PWM;
import team4188.CorpsLog;
import team4188.RobotMap;
import team4188.commands.HoldAngle;

/**
 * @author Erin
 */
public class Turret extends Subsystem {
    private CANJaguar tilt;
    private static final double MAX_TILT_VOLTAGE = 10.0,
            P = 0.035, I = 0.0001, D = 0.0, PID_LOOP_TIME = .05,     // LEAVE THESE CONSTANTS ALONE!
            SETTLED_TIME = 2.0, //TOLERANCE = 1.0;        // 1.0% = 3.6 degrees
            TOLERANCE = 1.0;        // 0.556% = 2 degrees
    private AS5030PWM tiltPosition;
    private PIDController tiltPID;
    private Timer timer;
    private boolean timerRunning = false;

    public void initDefaultCommand() {
        //setDefaultCommand(new HoldAngle());
    }

    public void init() {
        CorpsLog.log("Turret Status","initializing",false,true);
        try {
            tilt = new CANJaguar(RobotMap.TILT_MOTOR, CANJaguar.ControlMode.kPercentVbus);
            tilt.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            tilt.configMaxOutputVoltage(MAX_TILT_VOLTAGE);
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
        tiltPosition = new AS5030PWM(RobotMap.TILT_ENCODER);
        tiltPID = new PIDController(P,I,D,tiltPosition,tilt,PID_LOOP_TIME);
        tiltPID = new PIDController(P,I,D,tiltPosition,tilt,PID_LOOP_TIME);
        tiltPID.setInputRange(0.0, 90.0);   // soft limits: 0 to 90 degrees...
        tiltPID.setOutputRange(-1.0, 1.0);
        tiltPID.setTolerance(TOLERANCE);
        timer = new Timer();
        CorpsLog.log("Turret Status","initialized",false,true);
    }
    
    public void manualAimTilt (double Y) {
        if(tiltPID.isEnable()) disablePID();
        try {
            tilt.setX(Y);   // * 3?
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
        checkLimits();
    }
    
    public boolean autoAimTilt(double targetPosition) {
        checkLimits();
        if(!tiltPID.isEnable() && !onTarget(targetPosition)) enablePID();     //monitors drift...
        CorpsLog.log("method status", "autoAimTilt method",true,false);
        tiltPID.setSetpoint(targetPosition);
        if(thereYet(targetPosition)) disablePID();
        CorpsLog.log("tilt PID enabled?",tiltPID.isEnable(),false,true);        //temporary
        return thereYet(targetPosition);
    }
    
    private void checkLimits() {
        try {
            if(!tilt.getForwardLimitOK()){
                CorpsLog.log("Tilt Limits", "lower limit triggered",false,false);
                disablePID();
            }
            else if(!tilt.getReverseLimitOK()){
                CorpsLog.log("Tilt Limits", "upper limit triggered",false,false);
                disablePID();
            }
            else CorpsLog.log("Tilt Limits", "none triggered",false,false);
        } catch (CANTimeoutException ex) {ex.printStackTrace();}
    }
    
    private void enablePID() {
        tiltPID.enable();
    }
    
    public void disablePID() {
        tiltPID.disable();
    }
    
    private boolean thereYet(double target) {
        if(onTarget(target) && !timerRunning) {
            timer.start();
            timerRunning = true;
        }
        else if (!onTarget(target) && timerRunning) {
            timer.stop();
            timer.reset();
            timerRunning = false;
        }
        return timer.get() >= SETTLED_TIME;
    }
    
    public double getPWM() {
        return tiltPosition.getPWM();
    }
    
    public double getDegrees() {
        return tiltPosition.getDegrees();
    }
    
    public double getAbsoluteAngle() {
        return tiltPosition.getAbsoluteAngle();
    }
    
    private boolean onTarget(double target) {
        if(tiltPosition.getAbsoluteAngle() >= target - (TOLERANCE/100.0)*360.0 &&
                tiltPosition.getAbsoluteAngle() <= target + (TOLERANCE/100.0)*360.0)
            return true;
        else return false;
    }
}