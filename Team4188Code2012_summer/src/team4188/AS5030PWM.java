/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SensorBase;

/**
 * @author Erin
 */
public class AS5030PWM extends SensorBase implements PIDSource {
    private double ZERO_DEGREES = 52.3;      // This is the measured value of the 0 degrees position.
    private DigitalInput position;
    private Counter countXhi, countXlow;

    public AS5030PWM(int channel) {
        position = new DigitalInput(channel);
        countXhi = new Counter();
        countXlow = new Counter();
        // specify the source
        countXhi.setUpSource(position);
        countXlow.setUpSource(position);
        // setup the counters for semi-period mode
        countXhi.setSemiPeriodMode(true);
        countXlow.setSemiPeriodMode(false);
        countXhi.start();
        countXlow.start();
    }
    
    public double getPWM() {
        return 256*countXhi.getPeriod()/(countXhi.getPeriod()+countXlow.getPeriod());
    }
    
    // Converts raw PWM signal into DEGREES of tilt
    public double getDegrees() {
        return getPWM()*(360.0/256.0);
    }
    
    // This method is customized for Team 4188's 2012 robot.
    public double getAbsoluteAngle() {
        // 22 degrees +-1 degree is our absolute minimum angle...
        // NOTE: the absolute maximum is 54 degrees, in case that matters later.
        return (getDegrees()-ZERO_DEGREES);
    }

    public double pidGet() {
        return getAbsoluteAngle();
    }
}