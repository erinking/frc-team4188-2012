/*
 * Shooter class.
 * Defines the components and methods of the basketball-shooting mechanism on
 * Team 4188's robot for the 2012 FIRST Robotics game Rebound Rumble.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.AS5030DualAnalog;
import team4188.CorpsLog;
import team4188.RobotMap;

/**
 * @author Erin
 */
public class Shooter extends Subsystem {
    private SpinningWheel topWheel, bottomWheel;
    private static final boolean NEWPID = false;
    private CANJaguar shooterTop, shooterBottom;
    // A second set of brushes after the ball retrieval will transfer balls from the ball retrieval into the shooter.
    private Relay transfer;
    private static final int //ENC_CODES_PER_REV = 1,     // hopefully this will work with the sin/cos output.
            TOP = 0, BOTTOM = 1;
    private static final double MAX_SHOOTER_VOLTAGE = 12.0, //RAMP_RATE = 10.0,
            GEAR_RATIO = 26.0/22.0, P = 0.3, I = 0.001, D = 0.0,    // LEAVE THESE CONSTANTS ALONE!
            PID_LOOP_TIME = .05;
    private AS5030DualAnalog topShooterSpeed, bottomShooterSpeed;
    private PIDController topSpeedPID, bottomSpeedPID;
    
    public Shooter() {
        // super(P,I,D); // added by DAR
    }

    public void initDefaultCommand() {
        // DAR took out setDefaultCommand because once we set the motor speed in a command, we want
        // the command to complete without turning the motors back off with the default command.
        // setDefaultCommand(new ShooterOff());
    }
    
  
    
    public void init() {
        CorpsLog.log("Shooter Status","initializing",false,true);
        if (NEWPID) {
            /*try {
                topWheel = new SpinningWheel(RobotMap.TOP_SHOOTER_MOTOR,RobotMap.TOP_SHOOTER_SIN);
                bottomWheel = new SpinningWheel(RobotMap.BOTTOM_SHOOTER_MOTOR,RobotMap.BOTTOM_SHOOTER_SIN);
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
        } else {
            // Erin's way
            try {
                shooterTop = new CANJaguar(RobotMap.TOP_SHOOTER_MOTOR, CANJaguar.ControlMode.kPercentVbus);
                //shooterTop.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
                //shooterTop.configEncoderCodesPerRev(ENC_CODES_PER_REV);
                shooterTop.configNeutralMode(CANJaguar.NeutralMode.kCoast);
                shooterTop.configMaxOutputVoltage(MAX_SHOOTER_VOLTAGE);
                //shooterTop.setVoltageRampRate(RAMP_RATE);
                //shooterTop.enableControl();

                shooterBottom = new CANJaguar(RobotMap.BOTTOM_SHOOTER_MOTOR, CANJaguar.ControlMode.kPercentVbus);
                //shooterBottom.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
                //shooterBottom.configEncoderCodesPerRev(ENC_CODES_PER_REV);
                shooterBottom.configNeutralMode(CANJaguar.NeutralMode.kCoast);
                shooterBottom.configMaxOutputVoltage(MAX_SHOOTER_VOLTAGE);
                //shooterTop.setVoltageRampRate(RAMP_RATE);
                //shooterBottom.enableControl();
            } catch (CANTimeoutException ex) {ex.printStackTrace();}
            transfer = new Relay(RobotMap.TRANSFER_RELAY);

            topShooterSpeed = new AS5030DualAnalog(RobotMap.TOP_SHOOTER_SIN);
            bottomShooterSpeed = new AS5030DualAnalog(RobotMap.BOTTOM_SHOOTER_SIN);

            topSpeedPID = new PIDController(P,I,D,topShooterSpeed,shooterTop,PID_LOOP_TIME);
            topSpeedPID.setInputRange(0.0, 60.0);
            topSpeedPID.setOutputRange(0.0, 1.0);   // Reversed in hardware.
            topSpeedPID.setTolerance(0.0);          // in percent of the maximum RPS

            bottomSpeedPID = new PIDController(P,I,D,bottomShooterSpeed,shooterBottom,PID_LOOP_TIME);
            bottomSpeedPID.setInputRange(0.0, 60.0);
            bottomSpeedPID.setOutputRange(0.0, 1.0);
            bottomSpeedPID.setTolerance(0.0);       // in percent of the maximum RPS
        }
        CorpsLog.log("Shooter Status","initialized",false,true);
    }
    
    public void transferOn() {
        transfer.set(Relay.Value.kReverse);
    }
    public void transferOff() {
        transfer.set(Relay.Value.kOff);
    }
    public void transferReverse() {
        transfer.set(Relay.Value.kForward);
    }
    
    public void runShooter(double throttle) {
        if (NEWPID) {
            topWheel.setSetpoint(100.0);  // set in RPS?
            topWheel.enable();
            bottomWheel.setSetpoint(100.0);
            bottomWheel.enable();
        } else {
            if(!topSpeedPID.isEnable()||!bottomSpeedPID.isEnable()) enablePID();
            setSpeed(TOP,getTargetSpeed(1,throttle));
            setSpeed(BOTTOM,getTargetSpeed(1,throttle));
        }
    }
    
    public void shooterOff() {
        if (NEWPID) {
            //topWheel.setSetpoint(0.0);
            //bottomWheel.setSetpoint(0.0);
            // Not sure about how to stop... above setSetpoint may not have enough time for the PID
            // loop to ramp down to 0
            topWheel.disable();
            bottomWheel.disable();
        } else {
            if(topSpeedPID.isEnable()||bottomSpeedPID.isEnable()) disablePID();
            setSpeed(TOP,0.0);
            setSpeed(BOTTOM,0.0);
            //try {
            //    shooterTop.disableControl();
            //    shooterBottom.disableControl();
            //} catch (CANTimeoutException ex) {ex.printStackTrace();}
        }
    }
    
    public void topspin(double throttle) {
        if (NEWPID) {
        } else {
            if(!topSpeedPID.isEnable()||!bottomSpeedPID.isEnable()) enablePID();
            setSpeed(TOP,getTargetSpeed(1,throttle));
            setSpeed(BOTTOM,getTargetSpeed(0,throttle));
            //setSpeed(BOTTOM,0.0);
        }
    }
    
    public void backspin(double throttle) {
        if (NEWPID) {
        } else {
            if(!topSpeedPID.isEnable()||!bottomSpeedPID.isEnable()) enablePID();
            //setSpeed(TOP,0.0);
            setSpeed(TOP,getTargetSpeed(0,throttle));
            setSpeed(BOTTOM,getTargetSpeed(1,throttle));
        }
    }

    /*
    public static class SpeedMode {
        public final int value;
        static final int kHigh_val = 0;
        static final int kLow_val = 1;
        public static final SpeedMode kHigh = new SpeedMode(kHigh_val);
        public static final SpeedMode kLow = new SpeedMode(kLow_val);

        private SpeedMode(int value) {
            this.value = value;
        }
    }*/
    
    /**
     * @param motor 0 for motor with less power, 1 for motor with more power
     * @return target speed in RPS
     */
    private double getTargetSpeed(int motor, double throttle) {
        double toReturn = 0.0, MAX_SPEED = 45.0;
        if (motor == 1) {
            /*if(RobotMap.getShooterSpeed()==SpeedMode.kHigh) toReturn = 30.0;  // 30.0 = ~60% speed
                //toReturn = 0.6;
            else if(RobotMap.getShooterSpeed()==SpeedMode.kLow) toReturn = 10.0;// 15.0 = ~30% speed
                //toReturn = 0.3;*/
            
            //toReturn = MAX_SPEED * throttle;
            toReturn = RobotMap.getShooterSpeed();
        }
        else if (motor == 0) {
            /*if(RobotMap.getShooterSpeed()==SpeedMode.kHigh) toReturn = 45.0;  // 45.0 = ~100% speed
                //toReturn = 1.0;
            else if(RobotMap.getShooterSpeed()==SpeedMode.kLow) toReturn = 20.0;// 30.0 = ~60% speed
                //toReturn = 0.6;*/
            
            //toReturn = MAX_SPEED * throttle * 0.5;  //make slower motor half the speed of the faster one.
            toReturn = RobotMap.getShooterSpeed() * 0.5;  //make slower motor half the speed of the faster one.
        }
        toReturn += RobotMap.getSpeedTrim();
        return toReturn; 
    }
    
    public double getTopWheelSpeed() {
        return topShooterSpeed.getRate()*GEAR_RATIO;
        /*try { return shooterTop.getSpeed()*GEAR_RATIO;
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            return 0.0; }*/
    }
    
    public double getBottomWheelSpeed() {
        return bottomShooterSpeed.getRate()*GEAR_RATIO;
        /*try { return shooterBottom.getSpeed()*GEAR_RATIO;
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            return 0.0; }*/
    }
    
    public double getTopMotorSpeed() {
        return topShooterSpeed.getRate();
        /*try { return shooterTop.getSpeed();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            return 0.0; }*/
    }
    
    public double getBottomMotorSpeed() {
        return bottomShooterSpeed.getRate();
        /*try { return shooterBottom.getSpeed();
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
            return 0.0; }*/
    }
    
    /**
     * @param motor 0 for top, 1 for bottom.
     * @param targetSpeed The speed that you want the motor to go, in RPS.
     */
    public void setSpeed(int motor, double targetSpeed) {
        if(motor == BOTTOM){
            CorpsLog.log("Bottom target speed",targetSpeed,true,false);
            if(targetSpeed == 0.0)
                 try {shooterBottom.setX(targetSpeed);}
                 catch (CANTimeoutException ex) {ex.printStackTrace();}
            else bottomSpeedPID.setSetpoint(targetSpeed);
        }
        else if (motor == TOP) {
            CorpsLog.log("Top target speed",targetSpeed,true,false);
            if(targetSpeed == 0.0)
                try {shooterTop.setX(-targetSpeed);}
                catch (CANTimeoutException ex) {ex.printStackTrace();}
            else topSpeedPID.setSetpoint(targetSpeed);
        }
    }
    
    private void disablePID () {
        topSpeedPID.disable();
        bottomSpeedPID.disable();
    }
    
    private void enablePID () {
        topSpeedPID.enable();
        bottomSpeedPID.enable();
    }
}