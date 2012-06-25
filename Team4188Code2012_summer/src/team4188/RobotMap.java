package team4188;

import team4188.subsystems.Shooter;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static final int
            // CAN JAGUARS
            LEFT_DRIVE_MOTOR = 11,
            RIGHT_DRIVE_MOTOR = 12,
            TILT_MOTOR = 13,            // 13 on production, 13 on test bot.
            BOTTOM_SHOOTER_MOTOR= 14,   // 14 on production, 14 on test bot.
            TOP_SHOOTER_MOTOR = 15,     // 15 on production, 15 on test bot.
            // SERVOS/PWMS - PWM on Digital Sidecar
            //camPan = 1,
            //camTilt = 2,
            // SPIKE RELAYS - Relay output on Digital Sidecar
            COMPRESSOR_RELAY = 1,
            TRANSFER_RELAY = 2,
            BALL_RETRIEVAL_RELAY = 3,
            // PRESSURE SWITCH - Digital input
            PRESSURE_SWITCH = 1,    //1 on production, 1 on test bot
            // ENCODERS - Digital inputs
            TILT_ENCODER = 2,       //2 on production, 2 on test bot
            LEFT_ENCODER_1 = 3,     //3 on production, 3 on test bot
            LEFT_ENCODER_2 = 4,     //4 on production, 4 on test bot
            RIGHT_ENCODER_1 = 5,    //5 on production, 5 on test bot
            RIGHT_ENCODER_2 = 6,    //6 on production, 6 on test bot
            // PNEUMATIC SENSORS - Digital inputs
            //PNEUMATIC_EXTENDED_SENSOR = 7,
            //PNEUMATIC_RETRACTED_SENSOR = 8,
            // ANALOG SENSORS - Analog Breakout Module on cRIO
            GYRO = 1,
            TEMPERATURE = 2,
            BOTTOM_SHOOTER_SIN = 3,
            TOP_SHOOTER_SIN = 4,
            //TOP_SHOOTER_COS = 4,
            //BOTTOM_SHOOTER_COS = 6,
            // I2C "slot" number (digital module 1)
            ACCELEROMETER = 1,
            // SOLENOIDS - Solenoid Breakout Module on cRIO
            FLIPPER_SOLENOID_FORWARD = 1,
            FLIPPER_SOLENOID_REVERSE = 2;
            //GYRO_MODE = 0,
            //ENCODER_MODE = 1;
            //ACCELEROMETER_MODE = 2;

    /* DAR I don't think these static methods and variables are appropriate in this class.
       RobotMap is understood to be a place for setting system-wide constants, not implementing
       functionality, isn't it?  Would these be better in RobotMain?
       */
    private static final double MAX_ANGLE = 54.0;
    private static double currentAngle = MAX_ANGLE,      //MAX ANGLE?
            //pan trim is in degrees off center, speed is in rps
            panTrim = -1.0, speedTrim = 1.5;
    
    private static String pilotMode = "Manual Driving",
            copilotMode = "Manual Aiming";
    private static int prevCopilotXScale = 2;// driveTrainMode = 0;
    //private static Shooter.SpeedMode shooterVoltage = Shooter.SpeedMode.kLow;
    private static double shooterSpeed = 0.0;
    private static boolean driveReverse = false;
    
    public static double getCurrentAngle() {return MAX_ANGLE;}              // this is temp. - change to currentAngle if you want manual control...
    public static void setCurrentAngle(double angle) {currentAngle = angle;}
    public static double getPanTrim() {return panTrim;}
    public static void setPanTrim(double trim) {panTrim = trim;}
    public static double getSpeedTrim() {return speedTrim;}
    public static void setSpeedTrim(double trim) {speedTrim = trim;}
    //public static int getDriveTrainMode() {return driveTrainMode;}
    //public static void setDriveTrainMode(int newMode) {driveTrainMode = newMode;}
    
    public static String getPilotMode() {return pilotMode;}
    public static String getCopilotMode() {return copilotMode;}
    public static int getPrevCopilotXScale() {return prevCopilotXScale;}
    public static void setPilotMode(String newMode) {pilotMode = newMode;}
    public static void setCopilotMode(String newMode) {copilotMode = newMode;}
    public static void setPrevCopilotXScale(int newScale) {prevCopilotXScale = newScale;}
    //public static void setShooterSpeed(Shooter.SpeedMode voltage) {shooterVoltage = voltage;}
    //public static Shooter.SpeedMode getShooterSpeed() {return shooterVoltage;}
    public static void setShooterSpeed(double speed) {shooterSpeed = speed;}
    public static double getShooterSpeed() {return shooterSpeed;}
    // true is for reverse, false is for forward
    public static void setDriveReverse(boolean set) {driveReverse = set;}
    public static boolean getDriveReverse() {return driveReverse;}
}