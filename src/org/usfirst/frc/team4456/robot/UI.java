package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.SDashUI.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UI
{
    public UI(Robot robot)
    {
    	// Driver Values
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_X));
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_Y));
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getRawAxis(Constants.axis_rightStick_X));
    	
    	// Encoder
    	SmartDashboard.putNumber("Encoder distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder count", robot.encoder.get());
    	SmartDashboard.putBoolean("Reset Encoder", false);
    	
    	// PID
    	SmartDashboard.putNumber("P Value", robot.pValue);
    	SmartDashboard.putBoolean("Set PID", false);
    	//SmartDashboard.putNumber("Get PIDController", robot.driver.talon2.get());
    	
        System.out.println("UI Running");
        
        // Button for whether or not we use a gyroscope
        SmartDashboard.putBoolean("Using Gyro", false);
        
        // Gyro Values
        SmartDashboard.putNumber("Gyro Value", get360Angle(robot.gyro.getAngle()));
    	SmartDashboard.putNumber("Gyro Rate", robot.gyro.getRate());
    	SmartDashboard.putBoolean("Reset Gyro", false);
        
        // Button for whether we use Mechanum or Tank
        SmartDashboard.putBoolean("Using Mechanum", true);
        
        
        
        // Lidar Values 
        // SmartDashboard.putNumber("Get PID Lidar", robot.lidar.pidGet());
    	// SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    }
    
    public void update(Robot robot)
    {
    	// Enabled red/green light
    	SmartDashboard.putBoolean("Enabled", robot.isEnabled());
    	
    	// Driver Values
    	SmartDashboard.putNumber("Current Magnitude", robot.xboxController.getMagnitude());
    	SmartDashboard.putNumber("Cartesian X Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_X));
    	SmartDashboard.putNumber("Cartesian Y Value", robot.xboxController.getRawAxis(Constants.axis_leftStick_Y));
    	SmartDashboard.putNumber("Current Rotation", robot.xboxController.getRawAxis(Constants.axis_rightStick_X));
    	
    	// Resets encoder
    	if (SmartDashboard.getBoolean("Reset Encoder"))
    	{
    		robot.encoder.reset();
    		SmartDashboard.putBoolean("Reset Encoder", false);
    	}
    	
    	// Encoder Values
    	SmartDashboard.putNumber("Encoder Distance", robot.encoder.getDistance());
    	SmartDashboard.putNumber("Encoder Count", robot.encoder.get());
    	
    	// Sets pValue to the number in pValue widget
    	
    	/*
    	if (SmartDashboard.getBoolean("Set P Value"))
    	{
    		robot.driver.talon2.setPID(SmartDashboard.getNumber("pValue"), 0.0, 0.0);
    		SmartDashboard.putBoolean("Reset", false);
    	}
    	*/
    	
    	// Toggles Gyro and Mechanum Buttons
    	robot.useGyro = SmartDashboard.getBoolean("Using Gyro");
    	robot.useMechanum = SmartDashboard.getBoolean("Using Mechanum");
    	
    	//SmartDashboard.putNumber("Get PIDController", robot.driver.talon2.get());
    	
    	// Gyro Values
    	SmartDashboard.putNumber("Gyro Value", robot.gyro.getAngle());
    	SmartDashboard.putNumber("Gyro Rate", robot.gyro.getRate());
    	
    	if(SmartDashboard.getBoolean("Reset Gyro"))
    	{
    		robot.gyro.reset();
    		SmartDashboard.putBoolean("Reset Gyro", false);
    	}
    	
    	// Lidar
    	// SmartDashboard.putNumber("Get PID Lidar", robot.lidar.pidGet());
    	// SmartDashboard.putNumber("Lidar Distance", robot.lidar.getDistance());
    	
    	//ultrasonic
    	SmartDashboard.putNumber("Ultrasonic Value", robot.ultrasonic.getValueInches());
    	SmartDashboard.putNumber("Arduino", robot.arduinoLidar.getDistance());
    	
    	//talon encoder
    	SmartDashboard.putNumber("Talon Encoder Position", robot.driver.getMotorDistance());
    	
    	// PID
    	
    	SmartDashboard.putBoolean("Set P Value", false);
    	
    	SmartDashboard.putNumber("P Value", robot.pValue);
    	//SmartDashboard.putNumber("PIDControllerGet", robot.driver.talon2.get());
    }
    
    // This will just set an angle in between 0 and 360 to make it easier for the user to understand
    private double get360Angle(double value)
    {
    	while((value < 0 || value > 360))
    	{
    		if(value < 0)
    		{
    			value = value + 360;
    		}
    		if(value > 360)
    		{
    			value = value - 360;
    		}
    	}
    	return value;
    }
    
}
