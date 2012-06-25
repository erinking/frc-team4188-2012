package team4188;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team4188.commands.*;

public class OI {
    // Declare variables for the two joysticks being used
    public CorpsJoystick pilotStick, copilotStick;
    public Button pilotTrigger, pilotTop, pilot3, pilot4, pilot5,
            copilotTrigger, copilotTop, copilot3, copilot4, copilot5, copilot6,
            copilot7, copilot8, copilot9, copilot10, copilot11;
    public DriverStationEnhancedIO enhancedIO;

    public OI() {
        CorpsLog.log("OI Status","top",false,true);
        // Define joysticks being used at USB port #1 and USB port #2 on the Drivers Station

        // Pilot Stick is on port 1, with 3 axes and 11 buttons.
        // Dead zones from -1.5 to 1.5 on X & Y, square X & Y inputs. No twist.
        pilotStick = new
                CorpsJoystick(1,3,11,-1.5,1.5,1,1,-1.5,1.5,1,1,0,0,0,0);

        // Copilot Stick is on port 2, with 3 axes and 11 buttons.
        // Dead zones from -3 to 3 on X, -5 to 5 on Y, square X and quadruple Y
        // inputs. No twist. Negate Y Scale because the tilt will be used
        // airplane-style.
        copilotStick = new
                CorpsJoystick(2,3,11,-5,5,2,1,-3,3,4,-1,0,0,0,0);

        // PILOT JOYSTICK BUTTONS
        pilotTrigger = new JoystickButton(pilotStick, 1);
        pilotTop = new JoystickButton(pilotStick, 2);
        pilot3 = new JoystickButton(pilotStick, 3);
        pilot4 = new JoystickButton(pilotStick, 4);
        pilot5 = new JoystickButton(pilotStick, 5);
        //COPILOT JOYSTICK BUTTONS
        copilotTrigger = new JoystickButton(copilotStick, 1);
        copilotTop = new JoystickButton(copilotStick, 2);
        copilot3 = new JoystickButton(copilotStick, 3);
        copilot4 = new JoystickButton(copilotStick, 4);
        copilot5 = new JoystickButton(copilotStick, 5);
        copilot6 = new JoystickButton(copilotStick, 6);
        copilot7 = new JoystickButton(copilotStick, 7);
        copilot8 = new JoystickButton(copilotStick, 8);
        copilot9 = new JoystickButton(copilotStick, 9);
        copilot10 = new JoystickButton(copilotStick, 10);
        copilot11 = new JoystickButton(copilotStick, 11);

        enhancedIO = DriverStation.getInstance().getEnhancedIO();

        // Virtual Buttons
        //SmartDashboard.putData(new Autonomous());
        SmartDashboard.putData(new DisableAllPID());

        // Pilot Buttons
        pilotTrigger.whenPressed(new FlipRamp());     //change to whileHeld, FlipAndMove
        pilotTop.whileHeld(new Balance());
        //pilot3.whenPressed(new ResetDriveEncoders());
        //pilot4.whenPressed(new ResetGyro());
        pilot5.whenPressed(new SwitchDirection());

        // Copilot Buttons
        copilotTrigger.whenPressed(new Shoot());
        copilotTop.whenPressed(new AutoAim());
        copilot3.whenPressed(new Intake());
        copilot4.whenPressed(new RetrieverOff());
        copilot5.whenPressed(new Eject());
        copilot6.whenPressed(new Backspin());
        copilot7.whenPressed(new ShooterOff());
        //copilot9.whenPressed(new Topspin());
        //copilot10.whenPressed(new RunShooter());
        copilot8.whenPressed(new PanTrimLeft());
        copilot9.whenPressed(new PanTrimRight());
        copilot10.whenPressed(new SpeedTrimDown());
        copilot11.whenPressed(new SpeedTrimUp());
        
        CorpsLog.log("OI Status","botttom",false,true);
    }
}

