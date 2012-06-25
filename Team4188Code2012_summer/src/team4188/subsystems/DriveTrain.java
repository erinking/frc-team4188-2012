/*
 * DriveTrain class.
 * Defines the components and methods of the driving mechanism on
 * Team 4188's robot for the 2012 FIRST Robotics game Rebound Rumble.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import team4188.*;
import team4188.commands.ManualDrive;

/**
 * @author Erin
 */
public class DriveTrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Declare variable for the robot drive system
    private CorpsRobotDrive driveTrain;          // robot will use CAN for drive motors
    private CANJaguar left, right;
    private Timer timer;
    // Drive Wheel Encoders
    private Encoder leftEnc;
    private Encoder rightEnc;
    //private DriveEncoders driveEncoders;
    // distance per pulse of a drive-wheel encoder, in inches. [CHANGE THESE VALUES!!!!!!!!!!]
    private static final double NUM_CLICKS = 360, //distance per pulse = 1.23 mm //.0254
            GEAR_RATIO = 12.0/26.0, //PAN_VOLTAGE = 3.5/12.0, BALANCE_VOLTAGE = 2.0/12.0,
            MAX_VOLTAGE = 12.0, WHEEL_CIRCUMFERENCE = 0.1524,   // 6 inches on real bot!
            //WHEEL_CIRCUMFERENCE = 0.2032,   // 8 inches on practice bot!
            // 11 inches between wheels on the same side, 21 inches between wheels on opposite sides - pythagorean theorem
            //DIAGONAL_R_BETWEEN_WHEELS = Math.sqrt((.5334*.5334)+(.2794*.2794))/2,
            Pg = 0.1, Ig = 0.005, Dg = 0.0,     // LEAVE THESE CONSTANTS ALONE!
            Pe = 8.0, Ie = 0.01, De = 0.0,      // LEAVE THESE CONSTANTS ALONE!
            //Pa = 0.1, Ia = 0.005, Da = 0.0,
            PID_LOOP_TIME = .05, SETTLED_TIME = 2.0,
            gyroTOLERANCE = 0.3,            // 0.2778% error ~= 0.5 degrees...?
            encoderTOLERANCE = 0.5;         // 0.2% error ~= 1 cm...
            //accelTOLERANCE = 0.0;
    private static final int GYRO_MODE = 0, ENCODER_MODE = 1;
    private int mode = GYRO_MODE;
    private boolean resetG = false, resetE = false, timerRunning = false;
    
    private Gyro gyro;                          // gyro.
    //private ADXL345_I2C_PID accel;            // accelerometer.
    private AnalogChannel temperature;          // temperature.
    
    private PIDController gyroPID, leftEncPID, rightEncPID;//, encPID, accelPID;
    
    public void initDefaultCommand() {
        setDefaultCommand(new ManualDrive());
    }
    
    public void init() {
        CorpsLog.log("DriveTrain Status","initializing",false,true);
        try {
            // Define CAN Jaguar objects, set the drive Jags to be default voltage
            // controllers, & set the forklift Jags to be position controllers.
            
            left = new CANJaguar(RobotMap.LEFT_DRIVE_MOTOR, CANJaguar.ControlMode.kPercentVbus);
            right = new CANJaguar(RobotMap.RIGHT_DRIVE_MOTOR, CANJaguar.ControlMode.kPercentVbus);
            left.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            right.configNeutralMode(CANJaguar.NeutralMode.kBrake);
            left.configMaxOutputVoltage(MAX_VOLTAGE);
            right.configMaxOutputVoltage(MAX_VOLTAGE);
        } catch (CANTimeoutException ex) {ex.printStackTrace();}

        // Create a robot using standard right/left robot drive on the CAN bus
        driveTrain = new CorpsRobotDrive(left, right);
        // Both sides are true for production bot, false for test bot.
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
        
        driveTrain.setSafetyEnabled(false);
        
        // Left quadrature encoder, digital ports 1 & 2, goingBackwards.
        leftEnc = new Encoder(RobotMap.LEFT_ENCODER_1,RobotMap.LEFT_ENCODER_2,false,EncodingType.k4X);
        // Right quadrature encoder, digital ports 3 & 4, goingBackwards... eh. (was false)
        rightEnc = new Encoder(RobotMap.RIGHT_ENCODER_1,RobotMap.RIGHT_ENCODER_2,false,EncodingType.k4X);
        // Set distance per pulse for each encoder
        leftEnc.setDistancePerPulse(GEAR_RATIO*WHEEL_CIRCUMFERENCE/NUM_CLICKS);
        rightEnc.setDistancePerPulse(GEAR_RATIO*WHEEL_CIRCUMFERENCE/NUM_CLICKS);
        // Set PID source parameter to Distance...
        leftEnc.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        rightEnc.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
        // Start the encoders.
        leftEnc.start();
        rightEnc.start();
        
        //driveEncoders = new DriveEncoders(leftEnc,rightEnc);
        
        // Accelerometer I2C in Digital Module number 1.
        //accel = new ADXL345_I2C_PID(RobotMap.ACCELEROMETER,ADXL345_I2C.DataFormat_Range.k4G);

        // Gyro on analog channel 2.
        gyro = new Gyro(RobotMap.GYRO);
        gyro.reset();
        
        // Temperature on analog channel 1.
        temperature = new AnalogChannel(RobotMap.TEMPERATURE);
        
        gyroPID = new PIDController(Pg,Ig,Dg,gyro,driveTrain,PID_LOOP_TIME);
        //encPID = new PIDController(Pe,Ie,De,driveEncoders,driveTrain,PID_LOOP_TIME);
        leftEncPID = new PIDController(Pe,Ie,De,leftEnc,left,PID_LOOP_TIME);
        rightEncPID = new PIDController(Pe,Ie,De,rightEnc,right,PID_LOOP_TIME);
        //accelPID = new PIDController(Pa,Ia,Da,accel,driveTrain,PID_LOOP_TIME);
        
        gyroPID.setInputRange(-90.0, 90.0);   // soft limits: 0 to 90 degrees...
        //encPID.setInputRange(-2.5,2.5);       // adjust?
        leftEncPID.setInputRange(-2.5,2.5);       // adjust?
        rightEncPID.setInputRange(-2.5,2.5);       // adjust?
        //accelPID.setInputRange(-10.0, 10.0);  // in G's? change...
        
        gyroPID.setOutputRange(-1.0, 1.0);
        //encPID.setOutputRange(-1.0,1.0);
        leftEncPID.setOutputRange(-1.0,1.0);
        rightEncPID.setOutputRange(-1.0,1.0);
        //accelPID.setOutputRange(-1.0, 1.0);
        
        timer = new Timer();
        
        CorpsLog.log("DriveTrain Status","initialized",false,true);
    }
    
    public void drive(double Y, double X, boolean squaredInputs, boolean driveReverse) {
        disablePID();
        // -Y for test bot, Y for real bot / X on real bot, -X on test bot.
        if(driveReverse) driveTrain.arcadeDrive(Y, X, squaredInputs);
        // Y for test bot, -Y for real bot / X on real bot, -X on test bot.
        else driveTrain.arcadeDrive(-Y, X, squaredInputs);
    }
    
    /**
     * @param targetAngle Angle to drive in degrees/radians?
     */
    public boolean autoAimPan(double targetAngle) {
        //RobotMap.setDriveTrainMode(RobotMap.GYRO_MODE);
        mode = GYRO_MODE;
        //encPID.disable();
        if(leftEncPID.isEnable()) leftEncPID.disable();
        if(rightEncPID.isEnable()) rightEncPID.disable();
        //accelPID.disable();
        if(!resetG) {
            gyro.reset();
            resetG = true;
        }
        if(!gyroPID.isEnable()) gyroPID.enable();
        CorpsLog.log("method status", "autoAimPan",true,false);
        gyroPID.setSetpoint(targetAngle);
        if(thereYet(targetAngle)) {
            disablePID();
            resetG = false;
            return true;
        }
        return false;
    }
    
    /**
     * @param input Distance to drive in meters. Negative to drive backwards.
     */
    public boolean driveDistance(double distance) {
        //RobotMap.setDriveTrainMode(RobotMap.ENCODER_MODE);
        mode = ENCODER_MODE;
        gyroPID.disable();
        //accelPID.disable();
        if(!resetE) {
            resetEncoders();
            resetE = true;
        }
        if(!leftEncPID.isEnable()) leftEncPID.enable();
        if(!rightEncPID.isEnable()) rightEncPID.enable();
        CorpsLog.log("method status", "driveDistance",true,false);
        leftEncPID.setSetpoint(-distance);
        rightEncPID.setSetpoint(distance);     // the right drive wheel and encoder are inverted.
        if(thereYet(distance)) {
            disablePID();
            resetE = false;
            return true;
        }
        return false;
    }
    
    public boolean balance() {                              // TEST THIS
        /*RobotMap.setDriveTrainMode(RobotMap.ACCELEROMETER_MODE);
        gyroPID.disable();
        encPID.disable();
        if(!encPID.isEnable()) accelPID.enable();
        CorpsLog.log("method status", "balance method",true,false);
        accelPID.setSetpoint(0.0);
        if(thereYet(0.0)) disablePID();
        return thereYet(0.0);*/
        return true;        // UNTESTED...
    }
    
    public void resetGyro() {
        gyro.reset();
    }
    
    public double getGyroAngle() {
        return gyro.getAngle();
    }
    
    /**
     * @param axis 0 for X axis, 1 for Y axis, 2 for Z axis. Incorrect axis input will return an acceleration value of 0.0.
     *//*
    public double getAxisAcceleration(int axis) {
        if (axis == 0) return accel.getAcceleration(ADXL345_I2C.Axes.kX);
        else if (axis == 1) return accel.getAcceleration(ADXL345_I2C.Axes.kY);
        else if (axis == 2) return accel.getAcceleration(ADXL345_I2C.Axes.kZ);
        else return 0.0;
    }*/
    
    /**
     * @param wheelSide 0 for left, 1 for right. Incorrect side input will return a position value of 0.0.
     * @return Count from the encoder (since the last reset?).
     */
    public double getEncoderCount(int wheelSide) {
        if (wheelSide == 0) return left.get();
        else if (wheelSide == 1) return right.get();
        else return 0.0;
    }
    
    /**
     * @param wheelSide 0 for left, 1 for right. Incorrect side input will return a position value of 0.0.
     * @return Distance the encoder has recorded since the last reset, adjusted for the gear ratio.
     */
    public double getEncoderDistance(int wheelSide) {
        if (wheelSide == 0) return leftEnc.getDistance();
        else if (wheelSide == 1) return rightEnc.getDistance();
        else return 0.0;
    }
    
    public double getAvgEncoderDistance() {
        return (leftEnc.getDistance()+rightEnc.getDistance())/2.0;
    }
    
    public void resetEncoders() {
        leftEnc.reset();
        rightEnc.reset();
    }
    
    public double getTemperature() {
        return temperature.getVoltage();        // ADJUST THIS TO RETURN IN DEGREES...
    }
    
    public void disablePID() {
        if(gyroPID.isEnable()) gyroPID.disable();
        if(leftEncPID.isEnable()) leftEncPID.disable();
        if(rightEncPID.isEnable()) rightEncPID.disable();
        //accelPID.disable();
    }
    
    public boolean thereYet(double target) {
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
    
    private boolean onTarget(double target) {
        boolean toReturn = false, leftOnTarget, rightOnTarget;
        //switch(RobotMap.getDriveTrainMode()) {
        switch(mode) {
            case GYRO_MODE:
                System.out.println("gyro onTarget...");
                if(gyro.getAngle()>=(target-((gyroTOLERANCE/100.0)*180.0)) &&
                        gyro.getAngle()<=(target+((gyroTOLERANCE/100.0)*180.0)))
                    toReturn = true;
                else toReturn = false;
                break;
            case ENCODER_MODE:
                System.out.println("encoder onTarget... left error: "+
                        (leftEnc.pidGet()-target)+"\tright error: "+
                        (rightEnc.pidGet()-(-target))+"\ttolerance: "+
                        ((encoderTOLERANCE/100.0)*5.0));
                //if(driveEncoders.pidGet() >= target - (encoderTOLERANCE/100.0)*5.0 &&
                //        driveEncoders.pidGet() <= target + (encoderTOLERANCE/100.0)*5.0)
                if((leftEnc.pidGet()>=(target-((encoderTOLERANCE/100.0)*5.0))
                        && leftEnc.pidGet()<=(target+((encoderTOLERANCE/100.0)*5.0))))
                        leftOnTarget = true;
                else leftOnTarget = false;
                if((rightEnc.pidGet()>=((-target)-((encoderTOLERANCE/100.0)*5.0))
                        && rightEnc.pidGet()<=((-target)+((encoderTOLERANCE/100.0)*5.0))))
                    rightOnTarget = true;
                else rightOnTarget = false;
                System.out.println("left onTarget? "+leftOnTarget);
                System.out.println("right onTarget? "+rightOnTarget);
                if (leftOnTarget && rightOnTarget) toReturn = true;
                else toReturn = false;
                break;
            //case RobotMap.ACCELEROMETER_MODE:
            //    return true;               // REVISE LATER
            //    break;
        }
        System.out.println("on target? "+toReturn);
        return toReturn;
    }
    
    public void setMaxVoltages(double voltage) {
        try {
            left.configMaxOutputVoltage(voltage);
            right.configMaxOutputVoltage(voltage);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}