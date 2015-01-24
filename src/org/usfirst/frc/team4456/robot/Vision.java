package org.usfirst.frc.team4456.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.RGBValue;
import com.ni.vision.NIVision.Range;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.vision.AxisCamera;

public class Vision
{
	int session;
    Image frame;
    Image frameDest;
    AxisCamera camera;
    
    NIVision.RGBValue rgbVal;
    
    public Vision()
    {
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	frameDest = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	
        camera = new AxisCamera("10.50.0.30");
        
        
        rgbVal = new RGBValue(1, 1, 1, 1);
    }
    
    public void cycle()
    {
    	NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
    	
    	camera.getImage(frame);
        NIVision.imaqDrawShapeOnImage(frameDest, frame, rect,
                DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.5f);
        
        
        CameraServer.getInstance().setImage(frameDest);

        Timer.delay(0.005);
    }
    
    public void writeThresholdImg()
    {
    	NIVision.Image tmpImg = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    	frameDest = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    	camera.getImage(frame);
    	
    	NIVision.imaqThreshold(frameDest, frame, 0.5f, 0.6f, 1, 0.5f);
    	
    	NIVision.imaqWriteBMPFile(frameDest, Constants.filePathRoborio + "thresImg.bmp", 10, rgbVal);
    	System.out.println("img written to: " + Constants.filePathRoborio + "thresImg.bmp");
    	
    	Timer.delay(0.005);
    	
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    }
}