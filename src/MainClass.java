import processing.core.*;
import proxml.*;

public class MainClass extends PApplet 
{
	/* Properties
	__________________________________________________ */

	String xmlName = "1.xml";
	
	FrequencyImage frequencyImage;
	XMLElement rootNode;
	XMLInOut xmlInOut;
	
	boolean loaded = false;

	/* Setup
	__________________________________________________ */

	public void setup()
	{
		size(1920, 1080);
		frameRate(24);
		//smooth();

		//size(screen.width, screen.height);
		background(0);
		
		// Load XML Data
		xmlInOut = new XMLInOut(this);
		
		try 
		{
			xmlInOut.loadElement(xmlName); 
		}
		catch(Exception e)
		{
		    print(e);
		}
	}
	
	public void xmlEvent(XMLElement element)
	{
		rootNode = element;
		  
		frequencyImage = new FrequencyImage(this, rootNode.getChild(0).getAttribute("song"), 
												rootNode.getChild(0).getAttribute("img1"), 
												rootNode.getChild(0).getAttribute("img2"), 
												rootNode.getChild(0).getIntAttribute("lowestFreq"), 
												rootNode.getChild(0).getIntAttribute("bandsPerOctave"), 
												rootNode.getChild(0).getIntAttribute("sensitivity"));
		
		
		XMLElement[] shapes = rootNode.getChild(1).getChildren();
		
		for(int i = 0; i < shapes.length;i++)
		{
		    XMLElement shape = shapes[i];
		    
		    FrequencyShape theShape = new FrequencyShape(this, 
		    									shape.getIntAttribute("lowBand"), 
		    									shape.getIntAttribute("highBand"), 
		    									shape.getIntAttribute("triggerNum"),
		    									shape.getIntAttribute("sensitivity"),
		    									shape.getFloatAttribute("minVolume"));
		    
		    XMLElement[] positions = shape.getChild(0).getChildren();
			
			for(int j = 0; j < positions.length; j++)
			{
				theShape.addPos(positions[j].getFloatAttribute("x"), positions[j].getFloatAttribute("y"));
			}
			
			frequencyImage.addRange(theShape);
		    
		}
		
		frequencyImage.play();
		
		loaded = true;
	}

	/* Draw
	__________________________________________________ */

	public void draw()
	{
		if(loaded)
		{
			frequencyImage.update();
			
			//saveFrame();
		}
	}
	
	public void keyPressed()
	{
		if(keyCode == 'g')
		{
			frequencyImage.setGraph(true);
		}
		else if(keyCode == 'f')
		{
			frequencyImage.setGraph(false);
		}
	}
}




