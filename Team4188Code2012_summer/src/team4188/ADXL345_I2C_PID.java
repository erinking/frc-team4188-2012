/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package team4188;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 * @author Erin
 */
public class ADXL345_I2C_PID extends ADXL345_I2C implements PIDSource {
    /**
     * Constructor.
     *
     * @param slot The slot of the digital module that the sensor is plugged into.
     * @param range The range (+ or -) that the accelerometer will measure.
     */
    public ADXL345_I2C_PID(int slot, DataFormat_Range range) {
        super(slot, range);
    }
    
    public double pidGet() {
        return 0.0;                                                         //////REVISE
    }
}
