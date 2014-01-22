package cougartech.aerialassist.prototypes;

import edu.wpi.first.wpilibj.CANJaguar;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import java.util.Date;

public class RobotMain extends IterativeRobot
{

    Joystick joy;
    CANJaguar motor;
    Date nowTime;
    boolean wantShoot = false;

    boolean reset = false;
    long sTime;
    long pTime;
    int movementPer = 61;
    long motorTimeOut = 5000;
    long timePer = 120;
    double scaledCurrent;
    double resistance;
        
    public void robotInit()
    {
        joy = new Joystick(1);
        
        try
        {
            motor = new CANJaguar(10);
        }
        catch(CANTimeoutException ex)
        {
            ex.printStackTrace();
        }
        
        nowTime = new Date();
        //encoder = new Encoder(1, 2);
    }

    public void autonomousPeriodic()
    {
    }

    public void teleopPeriodic()
    {
        nowTime = new Date();
    
        try
        {
            motor.enableControl();
        }
        catch(CANTimeoutException ex)
        {
            ex.printStackTrace();
        }
      
        if(wantShoot)
        {
                if(nowTime.getTime() < (sTime + timePer))
                {
                    try
                    {
                        motor.setX(1.0);
                    }
                    catch(CANTimeoutException ex)
                    {
                        ex.printStackTrace();
                    }
                    reset = true;
                }
                else
                {
                    try
                    {
                        motor.setX(0.0);
                    }
                    catch(CANTimeoutException ex)
                    {
                        ex.printStackTrace();
                    }
                }
         }
        else if(!wantShoot && joy.getRawButton(1))
        {
            sTime = nowTime.getTime();
            wantShoot = true;
            
        }
        
        if(reset)
        {
            
        }
    }
}
