package cougartech.aerialassist.prototypes;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import java.util.Date;

public class RobotMain extends IterativeRobot
{

    Joystick joy;
    CANJaguar motor;
    CANJaguar motor2;
    DigitalInput stopSwitch;
    DigitalInput ballDetect;
    Date nowTime;
    boolean wantShoot = false;
    boolean reset = false;
    long sTime;
    long timePer = 120;
        
    public void robotInit()
    {
        joy = new Joystick(1);
        stopSwitch = new DigitalInput(1);
        ballDetect = new DigitalInput(2);
        
        try
        {
            motor = new CANJaguar(10);
            motor = new CANJaguar(11);
        }
        catch(CANTimeoutException ex)
        {
            ex.printStackTrace();
        }
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
            motor2.enableControl();
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
                        motor2.setX(1.0);
                    }
                    catch(CANTimeoutException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try
                    {
                        motor.setX(0.0);
                        motor2.setX(0.0);
                    }
                    catch(CANTimeoutException ex)
                    {
                        ex.printStackTrace();
                    }
                    reset = true;
                }
         }
        else if(!wantShoot && joy.getRawButton(1) && !ballDetect.get())
        {
            sTime = nowTime.getTime();
            wantShoot = true;
        }
       
        if(reset)
        {
            if(stopSwitch.get())
            {
                try
                {
                    motor.setX(0.0);
                    motor2.setX(0.0);
                }
                catch(CANTimeoutException ex)
                {
                    ex.printStackTrace();
                }
                reset  = false;
                wantShoot = false;
            }
            else
            {
                try
                {
                    motor.setX(-0.25);
                    motor2.setX(-0.25);
                }
                catch(CANTimeoutException ex)
                {
                    ex.printStackTrace();
                }                
            }
        }
        else
        {
            
        }
    }
}
