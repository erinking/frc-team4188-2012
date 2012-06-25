/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package team4188;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team4188.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends IterativeRobot {
    private Command autonomousCommand, printReadings;// test;
    //*private DriveForward test;
    //*private boolean testStarted = false;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        CorpsLog.log("Status","robotInit",false,true);
        // Initialize all subsystems
        CommandBase.init();
        // instantiate the command used for the autonomous period
        autonomousCommand = new Autonomous();
        SmartDashboard.putData(Scheduler.getInstance());
        System.out.println(Scheduler.getInstance());
        printReadings = new PrintSensorReadings();
        // test = new TestEncoders();
    }

    public void autonomousInit() {
        CorpsLog.log("Status","autonomousInit",false,true);
        autonomousCommand.start();
        //updateStatus();
        //CommandBase.drivetrain.setMaxVoltages(6.0);
        //Timer.delay(5.0);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        CorpsLog.log("Status","autonomousPeriodic",false,false);
        Scheduler.getInstance().run();
        //updateStatus();
    }

    public void teleopInit() {
        CorpsLog.log("Status","teleopInit",false,true);
        // This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to 
	// continue until interrupted by another command, remove
	// this line or comment it out.
        //CommandBase.turret.disablePID();
        CommandBase.drivetrain.disablePID();
        //*test = new DriveForward(0.1);
        
	autonomousCommand.cancel();
        //CommandBase.drivetrain.setMaxVoltages(12.0);
        
        //if(!printReadings.isRunning()) printReadings.start();
        //updateStatus();
        
        //*testStarted = false;
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        CorpsLog.log("Status","teleopPeriodic",false,false);
        Scheduler.getInstance().run();
        if(!printReadings.isRunning()) printReadings.start();
        //updateStatus();
        
        /**if(!testStarted) {
            test.start();
            testStarted = true;
        }**/
        //*System.out.println("test done? "+test.doneYet());
        CommandBase.monitorJoysticks();
    }
    
    public void disabledInit() {
        CorpsLog.log("Status","disabledInit",false,true);
        //printReadings.setRunWhenDisabled(true);
        //if(!printReadings.isRunning()) printReadings.start();
        //*testStarted = false;
    }
    
    public void disabledPeriodic() {
        CorpsLog.log("Status","disabledPeriodic",false,false);
        //if(!printReadings.isRunning()) printReadings.start();
        //updateStatus();
    }
    /*
    public void updateStatus() {
        //System.out.println("Status: Updating Status");
        //SmartDashboard.putString("Status","updating status");
        //SmartDashboard.putString("Pilot Mode", RobotMap.getPilotMode());
        //SmartDashboard.putString("CopilotMode", RobotMap.getCopilotMode());
        //SmartDashboard.putInt("Previous Copilot X Scale", RobotMap.getPrevCopilotXScale());
    }*/
}
