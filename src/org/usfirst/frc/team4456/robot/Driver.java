package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class Driver
{
	RobotDrive robotDrive;
	
	public Driver(int FL, int RL, int FR, int RR) //FrontLeft, RearLeft, FrontRear, RearRight
	{
		robotDrive = new RobotDrive(FL, RL, FR, RR);
	}
	
	// This will be used if we do not have a gyroscope
	public void drivePolar(Joystick controller)
	{
		// Magnitude, Direction, Rotation
		robotDrive.mecanumDrive_Polar(lowerSensitivity(controller.getMagnitude()), 
    			controller.getDirectionDegrees(), 
    			lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_X)));
	}
	
	// This will be used if we do have a gyroscope
	public void driveCartesian(Joystick controller, Gyro gyro)
	{
		// X, Y, Rotation, Gyro Angle
		robotDrive.mecanumDrive_Cartesian(lowerSensitivity(controller.getRawAxis(Constants.axis_leftStick_X)),
				lowerSensitivity(controller.getRawAxis(Constants.axis_leftStick_Y)),
				lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_X)),
    			gyro.getAngle());	
	}
	
	// This will be used if we are using TankDrive instead of Mechanum
	public void driveTank(Joystick controller)
	{
		robotDrive.tankDrive(lowerSensitivity(controller.getRawAxis(Constants.axis_leftStick_Y)),
				lowerSensitivity(controller.getRawAxis(Constants.axis_rightStick_Y)));
	}
	
	private double lowerSensitivity(double value)
	{
		if(Math.abs(value) < .1)
		{
			value = 0;
		}
		return value;
	}
}
