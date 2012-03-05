import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PFont;


public class Main
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	//System.out.println(Arrays.toString(PFont.list()));
	
	PApplet.main(new String[] {"--location=0,0",  "MasterViewer" });
	//PApplet.main(new String[] {"--location=1210,510", "SideView" });
	//PApplet.main(new String[] {"--location=1210,510", "FrontView" });
	//PApplet.main(new String[] {"--location=1210,0", "TopView" });
	//PApplet.main(new String[] { "AirshipViewer" });
    }

}
