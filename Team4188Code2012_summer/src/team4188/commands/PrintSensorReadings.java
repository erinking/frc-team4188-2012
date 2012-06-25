/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.commands;

import team4188.CorpsLog;
import team4188.RobotMap;

/**
 *
 * @author Erin
 */
public class PrintSensorReadings extends CommandBase {
    
    public PrintSensorReadings() {}

    // Called just before this Command runs the first time
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        CorpsLog.log("Gyro",drivetrain.getGyroAngle(),true,false);
        
        //CorpsLog.log("Accelerometer X-Axis reading",drivetrain.getAxisAcceleration(0),true,false);
        //CorpsLog.log("Accelerometer Y-Axis reading",drivetrain.getAxisAcceleration(1),true,false);
        //CorpsLog.log("Accelerometer Z-Axis reading",drivetrain.getAxisAcceleration(2),true,false);
        
        CorpsLog.log("Left Encoder",drivetrain.getEncoderDistance(0),true,false);
        CorpsLog.log("Right Encoder",drivetrain.getEncoderDistance(1),true,false);
        
        //CorpsLog.log("Top Shooter Wheel Speed",shooter.getTopWheelSpeed(),true,false);
        //CorpsLog.log("Bottom Shooter Wheel Speed",shooter.getBottomWheelSpeed(),true,false);
        CorpsLog.log("Top Shooter Motor Speed",shooter.getTopMotorSpeed(),true,false);
        CorpsLog.log("Bottom Shooter Motor Speed",shooter.getBottomMotorSpeed(),true,false);
        
        CorpsLog.log("Current Pan Trim",RobotMap.getPanTrim(),true,false);
        CorpsLog.log("Current Speed Trim",RobotMap.getSpeedTrim(),true,false);
        
        //CorpsLog.log("Tilt Encoder PWM reading",turret.getPWM(),true,false);
        //CorpsLog.log("Tilt Encoder reading in Degrees",turret.getDegrees(),true,false);
        //CorpsLog.log("Tilt Angle",turret.getAbsoluteAngle(),true,false);
        
        //CorpsLog.log("Temperature",drivetrain.getTemperature(),true,false);
        
        //CorpsLog.log("Ramp flipper pneumatic sensors",rampFlipper.getDirection(),false);
        CorpsLog.log("Pressure switch",rampFlipper.getPressureSwitch(),true,false);
        CorpsLog.log("Compressor enabled",rampFlipper.getEnabled(),true,false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
