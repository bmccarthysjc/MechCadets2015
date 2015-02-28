package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;

/**
 * Class for the winch on the hooks that pick up totes.
 * @author oom2013
 */
public class Hooks
{
	/*
	 * Controls:
	 * LBumper, RBumper, LTrigger, RTrigger
	 */
	private CANTalon talon;
	private boolean leftBumperPress, rightBumperPress;
	private int currentTargetIndex;
	
	/** 
	 * Constructor makes the motor for the winch and sets it up for use
	 * @param id
	 * @author oom2013
	 */
	public Hooks(int id)
	{
		talon = new CANTalon(id);
		talon.changeControlMode(CANTalon.ControlMode.Position);
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.setPID(1.00, 0.000001, 0);
		talon.set(talon.get());  // don't move when started...
		//talon1.enableControl();
	}
	
	/**
	 * Gets current target index
	 * @return currentTargetIndex
	 * @author oom2013
	 */
	public int getCurrentTargetIndex()
	{
		return currentTargetIndex;
	}

	/**
	 * Takes inputs from the XBoxController and performs actions based on them
	 * @param controller
	 * @author oom2013
	 */
	public void cycle(XBoxController controller)
	{
		// Left bumper lowers winch by one level
		boolean rawLeftBumperState = controller.getLBumper();
		if(rawLeftBumperState && !leftBumperPress)
		{
			leftBumperPress = true;
			this.lowerHooks();
		}
		else if(!rawLeftBumperState && leftBumperPress)
		{
			leftBumperPress = false;
		}
		else
		{
			/*
			 * This will trigger if the bumperBPress == true and rawLeftBumperState == true
			 * Or if they're both false
			 */
		}
		
		// Right bumper raises winch by one level
		boolean rawRightBumperState = controller.getRBumper();
		if(rawRightBumperState && !rightBumperPress)
		{
			rightBumperPress = true;
			this.raiseHooks();
		}
		else if(!rawRightBumperState && rightBumperPress)
		{
			rightBumperPress = false;
		}
		else
		{
			/*
			 * This will trigger if the bumperBPress == true and rawLeftBumperState == true
			 * Or if they're both false
			 */
		}
		
		talon.set(talon.getSetpoint() + (Constants.MAX_WINCH_NUDGE * controller.getAxisTriggers()));
		
		//System.out.println("fwd:" + forwardNudge + " rev:" + reverseNudge);
		
	}
	
	/** 
	 * Returns what the current winch position is at
	 * @author oom2013
	 */
	public double getWinchPosition()
	{
		return talon.get();
	}
	
	/**
	 *  Raises winch to closest default winch position above it 
	 *  unless the current position is above a certain threshold. 
	 *  If so, it goes to the next highest position.
	 *  @author oom2013
	 */
	private void raiseHooks()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex >= Constants.WINCH_LOADER_POSITIONS.length-1)
		{
			targetIndex = Constants.WINCH_LOADER_POSITIONS.length - 1;
		}
		else
		{
			targetIndex = closestIndex + 1;
		}
		//System.out.println("raise from: " + closestIndex + " @" + Constants.WINCH_POSITIONS[closestIndex] + " to " +  targetIndex + " @" + Constants.WINCH_POSITIONS[targetIndex] );
		talon.set(Constants.WINCH_LOADER_POSITIONS[targetIndex]);
		this.currentTargetIndex = targetIndex;
	}
	
	/** 
	 * Raises winch to the maximum position
	 * @author oom2013
	 */
	private void raiseHooksMax()
	{
		talon.set(Constants.WINCH_LOADER_POSITIONS[Constants.WINCH_LOADER_POSITIONS.length-1]);
		this.currentTargetIndex = Constants.WINCH_LOADER_POSITIONS.length-1;
	}
	
	/**
	 *  Lowers winch to closest default winch position below it 
	 *  unless the current position is above a certain threshold. 
	 *  If so, it goes to the next lowest position.
	 *  @author oom2013
	 */
	private void lowerHooks()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex <= 0)
		{
			targetIndex = 0;
		}
		else
		{
			targetIndex = closestIndex-1;
		}
		talon.set(Constants.WINCH_LOADER_POSITIONS[targetIndex]);
		//System.out.println("lower: " + closestIndex + " " + targetIndex );
		this.currentTargetIndex = targetIndex;
	}
	
	/** 
	 * Lowers winch to the minimum position
	 * @author oom2013
	 */
	private void lowerHooksMin()
	{
		talon.set(Constants.WINCH_LOADER_POSITIONS[0]);
		this.currentTargetIndex = 0;
	}
	
	/** 
	 * @return Closest default position to the current position
	 * @author oom2013
	 */
	private int findClosestPosition()
	{
		double currentPos = talon.get();
		double closestDistance = 0;
		int closestIndex = 0;
		double highestPos = Constants.WINCH_LOADER_POSITIONS[Constants.WINCH_LOADER_POSITIONS.length-1];
		double lowestPos = Constants.WINCH_LOADER_POSITIONS[0];
		if(currentPos > highestPos)
		{
			return Constants.WINCH_LOADER_POSITIONS.length-1;
		}
		else if(currentPos < lowestPos)
		{
			return 0;
		}
		else
		{
			for(int i = 0; i < Constants.WINCH_LOADER_POSITIONS.length; i++)
			{
				double distance = Math.abs(currentPos - Constants.WINCH_LOADER_POSITIONS[i]);
				//System.out.println("distance:" + distance + " i:" + i + " w[i]:" + Constants.WINCH_POSITIONS[i]);
				if(distance < closestDistance || i == 0)
				{
					closestDistance = distance;
					closestIndex = i;
				}
			}
		}
		return closestIndex;
	}
}