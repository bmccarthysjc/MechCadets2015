package org.usfirst.frc.team4456.robot;

import java.util.TimerTask;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;

public class Lidar implements PIDSource
{
	private I2C i2cLidar;
	private byte[] distance;
	private int processedDistance;
	private java.util.Timer updater;
	
	public Lidar(Port port)
	{
		i2cLidar = new I2C(port, Constants.LIDAR_ADDR);
		distance = new byte[2];
		distance[0] = 0;
		distance[1] = 0;
		updater = new java.util.Timer();
		processedDistance = 1;
	}
	
	public int getDistance()
	{
		return processedDistance;
	}
	
	public double pidGet()
	{
		return (double)getDistance();
	}
	
	public void start()
	{
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
	}
	
	public void start(int period)
	{
		updater.scheduleAtFixedRate(new LIDARUpdater(), 0, period);
	}
	
	public void stop()
	{
		updater.cancel();
		updater = new java.util.Timer();
	}
	
	public void update()
	{
		long ms0 = System.nanoTime();
		boolean wr = i2cLidar.write(Constants.LIDAR_CONFIG_REGISTER,  0x04);
		long ms1 = System.nanoTime();
		System.out.println("write:" + (wr) + " time: " + (ms1-ms0));
		Timer.delay(0.05);
		boolean rd = i2cLidar.read(Constants.LIDAR_DISTANCE_REGISTER, 2, distance);
		long ms2 = System.nanoTime();
		System.out.println("read:" + (rd) + " time: " + (ms2-ms1));
	    Timer.delay(0.005);
		System.out.println("Updating\tdistance[0]: " + distance[0] + "\tdistance[1]: " + distance[1]);
		processedDistance = (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
		//System.out.println(getDistance());
	}
	
	public void updateWNackack()
	{
		boolean nackack = true;
		
		//write and wait 1ms until transaction successful
		while(nackack)
		{
			nackack = i2cLidar.write(Constants.LIDAR_CONFIG_REGISTER,  0x04);
			Timer.delay(0.001);
		}
		
		nackack = true;
		
		//read and wait 1ms until transaction successful
		while(nackack)
		{
			nackack = i2cLidar.read(Constants.LIDAR_DISTANCE_REGISTER, 2, distance);
			Timer.delay(0.001);
		}
		
		System.out.println("Updating\tdistance[0]: " + distance[0] + "\tdistance[1]: " + distance[1]);
		processedDistance = (int)Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
	}
	
	private class LIDARUpdater extends TimerTask
	{
		public void run()
		{
			updateWNackack();
		}
	}
}