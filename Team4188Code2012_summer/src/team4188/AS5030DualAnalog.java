/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;

/**
 * @author charris, Erin
 */
public class AS5030DualAnalog extends SensorBase implements PIDSource {

    private static final double BIAS_VOLTAGE = 2.25,
            LOWER_LIMIT = -0.1, UPPER_LIMIT = 0.1, STOPPED_PERIOD = 0.5;
    private AnalogChannel sinChannel = null;
    private AnalogChannel cosChannel = null;
    private AnalogTrigger rateTrigger = null;
    private Counter rateCounter = null;

    public AS5030DualAnalog(int slot, int sinChannelParam, int cosChannelParam) {
        sinChannel = new AnalogChannel(slot, sinChannelParam);
        if(cosChannelParam != 0)
            cosChannel = new AnalogChannel(slot, cosChannelParam);
        rateTrigger = new AnalogTrigger(sinChannel);
        rateTrigger.setLimitsVoltage(LOWER_LIMIT+BIAS_VOLTAGE,UPPER_LIMIT+BIAS_VOLTAGE);
        rateCounter = new Counter(rateTrigger);
        rateCounter.setMaxPeriod(STOPPED_PERIOD);
        rateCounter.start();
    }
    
    /**
     * @param sinChannelParam sin channel for encoder used to get angle.
     * @param cosChannelParam cos channel for encoder used to get angle.
     */
    public AS5030DualAnalog(int sinChannelParam, int cosChannelParam) {
        this(AnalogChannel.getDefaultAnalogModule(), sinChannelParam, cosChannelParam);
    }

    /**
     * @param channel This can be used if you are measuring rate only, not angle.
     */
    public AS5030DualAnalog(int channel) {
        this(AnalogChannel.getDefaultAnalogModule(), channel, 0);
    }
    
    protected double getCos() {
        return cosChannel.getVoltage() - BIAS_VOLTAGE;
    }

    protected double getSin() {
        return sinChannel.getVoltage() - BIAS_VOLTAGE;
    }

    public double getAngle() {
        // calculate the angle
        double theta = MathUtils.atan2(getSin(), getCos());
        // convert to degrees
        return Math.toDegrees(theta) + 180.0;
    }

    /**
     * Get the speed or angle of the encoder for use with PIDControllers
     * @return the current speed/angle according to the encoder
     */
    public double pidGet() {
        if (cosChannel != null) return getAngle();
        return getRate();
    }
    
    /**
     * @return The rate of the magnetic encoder in rotations per second.
     */
    public double getRate() {
        double rate = 1/rateCounter.getPeriod();
        /*System.out.println("channel "+sinChannel.getChannel()+"\tvoltage with bias: "+
                sinChannel.getVoltage()+"\tvoltage without bias: "+getSin()+"\ttrigger: "+
                rateTrigger.getTriggerState()+"\tperiod: "+
                rateCounter.getPeriod()+"\trate: "+rate);*/
        return rate;
    }
}