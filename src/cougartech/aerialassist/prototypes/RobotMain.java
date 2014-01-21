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
    boolean readyForNext = false;
    long sTime;
    long pTime;
    int movementPer = 61;
    int motorTimeOut = 30000;
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
            //pTime = nowTime.getTime();
            //scaledCurrent = AnalogModule.getInstance(1).getVoltage(1);
            //System.out.println("System Current Meter");
            //System.out.println("0: " + scaledCurrent);
            encoder.start();
            
            try{
                motor.enableControl();
            }catch(CANTimeoutException ex)
            {
                ex.printStackTrace();
            }
            
            oneTime = true;
        }
        
        //if((pTime + 10) >= nowTime.getTime())
        //{
        //    time = time + 10;
        //    scaledCurrent = AnalogModule.getInstance(1).getVoltage(1);
        //    pTime = nowTime.getTime();
        //    System.out.println(time + ": " + scaledCurrent);
        //}
        //resistance = AnalogModule.getInstance(1).getVoltage(2) / amperes;
        
        if(wantShoot)
        {
            if((sTime + motorTimeOut) >= nowTime.getTime())
            {
                encoder.reset();
                try
                {
                    motor.setX(0.0);
                }
                catch(CANTimeoutException ex)
                {
                    ex.printStackTrace();
                }
                readyForNext = true;
            }
            else
            {
                /*if(encoder.get() >= movementPer)
                {
                    motor.set(0.0);
                    encoder.reset();
                    readyForNext = true;
                }*/
            }
        }
        else if(!wantShoot && joy.getRawButton(1))
        {
            sTime = nowTime.getTime();
            //encoder.reset();
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
        
        if(joy.getRawButton(3) && readyForNext)
        {
            wantShoot = false;
            readyForNext = false;
        }
    }
}
