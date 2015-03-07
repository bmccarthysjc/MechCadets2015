package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.util.Util;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * Class for the wheels on the chassis that drive the robot.
 * @author oom2013
 */
public class Driver
{
	RobotDrive robotDrive;
	private CANTalon talon1, talon2, talon3, talon4;
	
	/** 
	 * Takes in robot type and initializes talon controllers dependeing on the robot type.
	 * @param roboType RobotType to use. MAIN_ PRACTICE_ or BREADBOARD_BOT
	 * @author samega15
	 */
	public Driver(RobotType roboType)
	{
		if(roboType != null)
		{
			talon1 = new CANTalon(roboType.idRL);
			talon2 = new CANTalon(roboType.idFL);
			talon3 = new CANTalon(roboType.idRR);
			talon4 = new CANTalon(roboType.idFR);
		}
		else
		{
			System.err.println("ERROR: Talon Controllers > RobotType\n"
					+ "Driver.java\n"
					+ "public Driver(RobotType roboType)");
		}
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		//order RL FL RR FR
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	/** 
	 * Constructor checks whether or not we are using the test robot and switches between the test motors and the robot motors
	 * @param useTest
	 * @author oom2013
	 */
	public Driver(boolean useTest)
	{
		if(useTest)
		{
			talon1 = new CANTalon(11);
			talon2 = new CANTalon(10);
			talon3 = new CANTalon(15);
			talon4 = new CANTalon(22);
		}
		else
		{
			talon1 = new CANTalon(14);
			talon2 = new CANTalon(21);
			talon3 = new CANTalon(16);
			talon4 = new CANTalon(12);
			
			/*
			talon1 = new CANTalon(17);
			talon2 = new CANTalon(18);
			talon3 = new CANTalon(20);
			talon4 = new CANTalon(19);
			*/
		}
		
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	
	/**
	 *  Executes the Polar, Cartesian, or Tank method based on the useMechanum and useGyro booleans
	 * @param controller
	 * @param gyro
	 * @param robot
	 * @author oom2013
	 */
	public void drive(XBoxController controller, Gyro gyro, Robot robot)
	{
		if(robot.useMechanum)
		{
			if(robot.useGyro)
	    	{
	    		this.driveCartesian(controller, gyro);
	    	}
	    	else
	    	{
	    		this.drivePolar(controller);
	    	}
		}
		// Tank drive is most likely not needed at all - This method will never be called
		else
		{
			this.driveTank(controller);
		}
	}
	
	/**
	 *  This will be used if we do not have a gyroscope
	 * @param controller
	 * @author oom2013
	 */
	private void drivePolar(XBoxController controller)
	{
		// Parameters are Magnitude, Direction, Rotation
		// Arguments are the magnitude of the joysticks, the direction of the joysticks, and the value given by the right-stick x-value
		robotDrive.mecanumDrive_Polar(Util.lowerSensitivity(controller.getMagnitude()), 
    			controller.getDirectionDegrees(), 
    			Util.lowerSensitivity(controller.getAxisRStickX()));
	}
	
	/**
	 * This will be used if we do have a gyroscope
	 * @param controller
	 * @param gyro
	 * @author oom2013
	 */
	private void driveCartesian(XBoxController controller, Gyro gyro)
	{
		// Parameters are X, Y, Rotation, and Gyro Angle
		// Arguments are the values given by the left-stick x-value, left-stick y-value, right-stick x-value, and the angle produced by the gyroscope
		robotDrive.mecanumDrive_Cartesian(Util.lowerSensitivity(controller.getAxisLStickX()),
				Util.lowerSensitivity(controller.getAxisLStickY()),
				Util.lowerSensitivity(controller.getAxisRStickX()),
    			gyro.getAngle());
	}
	
	/**
	 * This will be used if we are using TankDrive instead of Mechanum,
	 * but will probably not be used
	 * @param controller
	 * @author oom2013
	 */
	private void driveTank(XBoxController controller)
	{
		robotDrive.tankDrive(Util.lowerSensitivity(controller.getAxisLStickY()),
				Util.lowerSensitivity(controller.getAxisRStickY()));
	}
	
}