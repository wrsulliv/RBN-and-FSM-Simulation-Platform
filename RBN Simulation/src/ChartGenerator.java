import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class ChartGenerator {

	//  The datapoints must be from least x/y to greatest x/y
	public static BufferedImage generateHeatMapFromDataPoint3DList(ArrayList<DataPoint3D> dataPointList, int xRange, int yRange, int heatRange)
	{
		
		//  Create the actual image object.  The number of rows is 1 more than the number of inputs because of the initial state.
		BufferedImage bf = new BufferedImage(xRange, yRange, BufferedImage.TYPE_INT_RGB);
		
		//  Loop through each input string item and determine the next state.
		for(int i = 0; i < dataPointList.size(); i++)
		{
			
			//  TODO:  Prove that this is true.
			//  As of right now I believe computational capability can be lower than 0... So, make sure it doesn't go below zero..
			Color heatColor;	
			if ((dataPointList.get(i).heatPoint >= 0) && (dataPointList.get(i).heatPoint <= 0.2f)) 
				{
					 heatColor = rgbFull(0, 0.2f, dataPointList.get(i).heatPoint);
				}
				else if(dataPointList.get(i).heatPoint > 0.2f)
				{
					heatColor = rgbFull(0, 0.2f, .2f);
				}
				else
				{
					heatColor = rgbFull(0, 0.2f, 0);
				}
				bf.setRGB(dataPointList.get(i).x-1, yRange - (dataPointList.get(i).y), heatColor.getRGB()); 

			
		}
		
		//  Save the bitmap
		File outputFile = new File("/Users/willimac/Desktop/heatMap_small.bmp");
		try {
			ImageIO.write(bf, "bmp", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bf;
		
	}
	
	
	public static Color rgbFull(float minimum, float maximum, float value)
	{
	    
		//  Invert the direction to make Red map to the highest value instead of the lowest...
		value = maximum - value;
		
		//  Setup variables
	    float interval = (maximum - minimum) / 4;
	    float r = 0;
	    float g = 0;
	    float b = 0;
	    
	    if((value >= minimum) && (value < interval*1))
	    {
	    	
	    	r = 255;
	    	g = 255*(value - minimum)/(interval);
	    	b = 0;
	    }
	    else if((value >= interval*1) && (value < interval*2))
	    {
	    	r = 255 - 255*(value - interval*1)/(interval);
	    	g = 255;
	    	b = 0;
	    	
	    }
	    else if((value >= interval*2) && (value < interval*3))
	    {
	    	r = 0;
	    	g = 255;
	    	b = 255*(value - interval*2)/(interval);
	    	
	    }
	    else if((value >= interval*3) && (value <= interval*4))
	    {

	    	r = 0;
	    	g = 255 - 255*(value - interval*3)/(interval);
	    	b = 255;
	    }  
	    else
	    {
	    	//  Error State!
	    	r = -1;
	    	g = -1;
	    	b = -1;
	    }
	    
	    Color color = new Color(r/255,g/255,b/255);
	    return color;
	}
	
}
