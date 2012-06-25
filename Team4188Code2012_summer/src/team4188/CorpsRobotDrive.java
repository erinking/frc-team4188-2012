/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * @author Erin
 */
public class CorpsRobotDrive extends RobotDrive implements PIDOutput {
    static final int kFrontLeft_val = 0;
    static final int kFrontRight_val = 1;
    static final int kRearLeft_val = 2;
    static final int kRearRight_val = 3;
    
    public CorpsRobotDrive(final int leftMotorChannel, final int rightMotorChannel) {
        super(leftMotorChannel, rightMotorChannel);
    }

    public CorpsRobotDrive(final int frontLeftMotor, final int rearLeftMotor,
            final int frontRightMotor, final int rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }

    public CorpsRobotDrive(SpeedController leftMotor, SpeedController rightMotor) {
        super(leftMotor, rightMotor);
    }

    public CorpsRobotDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor,
            SpeedController frontRightMotor, SpeedController rearRightMotor) {
        super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
    }

    public void pidWrite(double output) {
        System.out.println("pidWrite in CorpsRobotDrive");
        //if(RobotMap.getDriveTrainMode()==RobotMap.GYRO_MODE) {
        // NOT - on practice bot...
            super.m_rearLeftMotor.pidWrite(-output*super.m_invertedMotors[kRearLeft_val]);
            super.m_rearRightMotor.pidWrite(-output*super.m_invertedMotors[kRearRight_val]);
        //}
        /*else if(RobotMap.getDriveTrainMode()==RobotMap.ENCODER_MODE) {
            super.m_rearLeftMotor.pidWrite(output*super.m_invertedMotors[kRearLeft_val]);
            super.m_rearRightMotor.pidWrite(-output*super.m_invertedMotors[kRearRight_val]);
        }
        else if(RobotMap.getDriveTrainMode()==RobotMap.ACCELEROMETER_MODE) {
            super.m_rearLeftMotor.pidWrite(output*super.m_invertedMotors[kRearLeft_val]);
            super.m_rearRightMotor.pidWrite(-output*super.m_invertedMotors[kRearRight_val]);
        }*/
        ////////////////////????????????????????????????????????????????????????
    }
}