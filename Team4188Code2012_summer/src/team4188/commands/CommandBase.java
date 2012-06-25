package team4188.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team4188.CorpsLog;
import team4188.OI;
import team4188.RobotMap;
import team4188.subsystems.*;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static DriveTrain drivetrain;
    public static Shooter shooter;
    //public static Turret turret;
    public static BallRetrieval ballRetrieval;
    public static RampFlipper rampFlipper;
    public static Vision vision;

    public static void init() {
        CorpsLog.log("CommandBase Status","initializing",false,true);
        
        drivetrain = new DriveTrain();
        shooter = new Shooter();
        //turret = new Turret();
        ballRetrieval = new BallRetrieval();
        rampFlipper = new RampFlipper();
        vision = new Vision();
        
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        
        drivetrain.init();
        shooter.init();
        //turret.init();
        ballRetrieval.init();
        rampFlipper.init();
        vision.init();

        //showSubsystem();
        CorpsLog.log("CommandBase Status","initialized",false,true);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
    
    private static void showSubsystem() {
        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(rampFlipper);
        SmartDashboard.putData(vision);
        SmartDashboard.putData(ballRetrieval);
        //SmartDashboard.putData(turret);
    }
    
    public static void monitorJoysticks() {
        //if(!oi.pilotStick.isDead())
        //    new ManualDrive().start();
        //System.out.println("pilot dead "+oi.pilotStick.isDead()+
        //        "\tcopilot dead "+oi.copilotStick.isDead());          //GET BACK TO THIS......!
        /*
        if(oi.copilotStick.isDead())
            new HoldAngle().start();
        else new ManualAim().start();*/
        
        if(oi.copilotStick.getThrottle() > 0.9)
            RobotMap.setShooterSpeed(45.0);         // top speed ~= 45 RPS... ?
    }
}