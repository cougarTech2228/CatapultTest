package cougartech.aerialassist.prototypes;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import java.util.Date;

public class RobotMain extends IterativeRobot
{

    Joystick joy;
    Talon motor;
    Date nowTime;
    boolean wantShoot = false;
    long sTime;
    int motorTimeOut = 1000;
        
    public void robotInit()
    {
        joy = new Joystick(1);
        motor = new Talon(1);
        nowTime = new Date();
    }

    public void autonomousPeriodic()
    {
    }

    public void teleopPeriodic()
    {
        if(wantShoot)
        {
            if((sTime + motorTimeOut) > nowTime.getTime())
            {
                motor.set(0.0);
            }
            else
            {
                
            }
        }
        else if(!wantShoot && joy.getRawButton(1))
        {
            sTime = nowTime.getTime();
            motor.set(1.0);
            wantShoot = true;
        }
        else
        {
            motor.set(0.0);
        }
    }
}
