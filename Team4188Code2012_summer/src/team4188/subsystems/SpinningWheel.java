/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import team4188.AS5030DualAnalog;

/**
 *
 * @author Erin
 */
public class SpinningWheel extends PIDSubsystem {

    private static final double Kp = 0.7;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    
    private static final double MAX_SHOOTER_VOLTAGE = 12.0, RAMP_RATE = 10.0,
            GEAR_RATIO = 26.0/22.0; //P = 0.3, I = 0.001, D = 0.0;    // LEAVE THESE CONSTANTS ALONE!
    
    private CANJaguar motor = null;
    private AS5030DualAnalog speedometer = null;
    
    // Initialize your subsystem here
    public SpinningWheel(int canId, int analogSensorChannel) throws Exception {
        super("SpinningWheel", Kp, Ki, Kd);
        motor = new CANJaguar(canId,CANJaguar.ControlMode.kPercentVbus);
        motor.configNeutralMode(CANJaguar.NeutralMode.kCoast);
        motor.configMaxOutputVoltage(MAX_SHOOTER_VOLTAGE);
        speedometer = new AS5030DualAnalog(analogSensorChannel);
        
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        // return 0.0;
        return speedometer.getRate()*GEAR_RATIO;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        try {
          motor.setX(output);
        } catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
