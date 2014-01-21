package cougartech.aerialassist.prototypes;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import java.util.Date;

public class RobotMain extends IterativeRobot
{

    Joystick joy;
    CANJaguar motor;
    Date nowTime;
    Encoder encoder;
    boolean wantShoot = false;
    boolean oneTime = false;
    boolean reset = false;
    long sTime;
    long pTime;
    int movementPer = 61;
    int motorTimeOut = 5000;
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
        encoder = new Encoder(1, 2);
    }

    public void autonomousPeriodic()
    {
    }

    public void teleopPeriodic()
    {
        System.out.println("Encoder: " + encoder.get());
        if(!oneTime)
        {
            encoder.start();
            
            try
            {
                motor.enableControl();
            }
            catch(CANTimeoutException ex)
            {
                ex.printStackTrace();
            }
            
            oneTime = true;
        }
        
        //Acuation of arm
        if(wantShoot)
        {
            //Motor timeout
            if((sTime + motorTimeOut) >= nowTime.getTime())
            {
                try
                {
                    motor.setX(0.0);
                }
                catch(CANTimeoutException ex)
                {
                    ex.printStackTrace();
                }
                
                reset = true;
            }
            else
            {
                //Foward Encoder Check
                if(encoder.get() >= movementPer)
                {
                    try
                    {
                        motor.setX(0.0);
                    }
                    catch(CANTimeoutException ex)
                    {
                        
                    }
                    reset = true;
                }
            }
        }
        else if(!wantShoot && joy.getRawButton(1))
        {
            sTime = nowTime.getTime();
            
            try
            {
                motor.setX(1.0);
            }
            catch(CANTimeoutException ex)
            {
                ex.printStackTrace();
            }
            
            wantShoot = true;
        }
        
        //Reset of arm
        if(reset)
        {
            if(encoder.get() <= 0)
            {
                try
                {
                    motor.setX(0.0);
                }
                catch(CANTimeoutException ex)
                {
                    ex.printStackTrace();
                }
                wantShoot = false;
            }
            else
            {
                try
                {
                    motor.setX(-0.5);
                }
                catch(CANTimeoutException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
