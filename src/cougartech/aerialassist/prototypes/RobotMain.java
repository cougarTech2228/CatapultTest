package cougartech.aerialassist.prototypes;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import java.util.Date;

public class RobotMain extends IterativeRobot
{

    Joystick joy;
    Victor motor;
    Date nowTime;
    boolean wantShoot = false;
    boolean oneTime = false;
    long sTime;
    long pTime;
    int time;
    int motorTimeOut = 1000;
    double scaledVoltage;
        
    public void robotInit()
    {
        joy = new Joystick(1);
        motor = new Victor(1);
        nowTime = new Date();
    }

    public void autonomousPeriodic()
    {
    }

    public void teleopPeriodic()
    {
          
        if(!oneTime)
        {
            pTime = nowTime.getTime();
            scaledVoltage = (5/2) * AnalogModule.getInstance(1).getVoltage(1);
            System.out.println("System Current Meter");
            System.out.println("0: " + scaledVoltage);
            oneTime = true;
        }
        
        if((pTime + 10) >= nowTime.getTime())
        {
            time = time + 10;
            scaledVoltage = (5/2) * AnalogModule.getInstance(1).getVoltage(1);
            pTime = nowTime.getTime();
            System.out.println(time + ": " + scaledVoltage);
        }
        
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
