import airship.Airship;
import picking.Picker;
import processing.core.*;
import processing.opengl.*;
import processing.pdf.*;

/**
 * Comments Everywhere!
 * 
 * @author maximilian
 * 
 */
public abstract class AirshipViewer extends PApplet
{
    private boolean       ortho	     = false;
    private int	   width	     = 600, height = 600;

    private PVector       center	    = new PVector();
    private PVector       centerOffset      = new PVector();
    private float	 zoomFactor	= 50;

    private float	 worldXRotation    = 0;
    private float	 worldYRotation    = 0;

    //airship parameters
    private static double length	    = 18 * 1;
    private static double keelLength	= 20 * 1;
    private static int    numberOfKeels     = 3;
    private static int    ropePointsPerKeel = 15;
    private static int highLightedLayer = -1;

    public static Airship airship	   = makeAirship();

    private PFont	 bigFont	      = createFont("Arial", 72);
    private PFont	 smallFont	      = createFont("Arial", 16);

    private Picker	picker;
    
    private PImage human;
    
    private boolean export = false;

    protected abstract void initAirshipViewer();

    public void setup()
    {
	initAirshipViewer();
	center.x = width / 2;
	center.y = height / 2;

	
	size(width, height, P3D);
	hint(DISABLE_OPENGL_2X_SMOOTH);
	hint(ENABLE_DEPTH_MASK);
	hint(DISABLE_DEPTH_TEST);
	smooth();

	
	addMouseWheelListener(new java.awt.event.MouseWheelListener() {
	    public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt)
	    {
		mouseWheel(evt.getWheelRotation());
	    }
	});

	

	picker = new Picker(this);
	
	human = loadImage("human.png");
	
    }

    

    public void draw()
    {
	background(255);
	if (ortho)
	    ortho(0, width, 0, height, -1000, 1000);

	pushMatrix();
	translate(0, 6, 0);
	stroke(0);
	fill(0);
	textFont(smallFont);
	drawLabels();
	popMatrix();

	pushMatrix();

	translate(center.x + centerOffset.x, center.y + centerOffset.y, center.z + centerOffset.z);
	
	scale(zoomFactor);
	noStroke();
	image(human, -0.8125f/2.0f, -1.8f, 0.8125f, 1.8f);
	rotateX(worldXRotation);
	rotateY(worldYRotation);
	
	
	Painter.draw(this.g, airship, highLightedLayer, picker);
	
	drawGridsAndRulers();
	
	popMatrix();
	
	picker.stop();
	if(export)
	{
	    saveFrame("Frame-" + frameCount + ".png");
	    export = false;
	}
    }
    
    public void drawAirshipHere(PGraphics g)
    {
	Painter.draw(this.g, airship, highLightedLayer, picker);
    }

    protected abstract void drawGridsAndRulers();

    protected abstract void drawLabels();

    

    

    public void mouseWheel(int delta)
    {
	if(zoomFactor + delta*4 > 0)
	    zoomFactor += delta * 4;
    }

    PVector startDragMousePos  = null;
    float   startDragRotation  = 0;
    double  startLength	= 0;
    float   startCenterOffsetY = 0;

    public void mousePressed()
    {
	startDragMousePos = new PVector(mouseX, mouseY);
	startDragRotation = worldYRotation;
	startLength = airship.getLength();
	startCenterOffsetY = centerOffset.y;
    }

    public final void mouseDragged()
    {
	mouseDraggedActions();
	highLightedLayer = picker.get(mouseX, mouseY);
    }

    abstract void mouseDraggedActions();

    protected void mouseXdragsRotationY()
    {
	worldYRotation = startDragRotation + (mouseX - startDragMousePos.x) / 200;
    }

    protected void mouseYdragsLength()
    {
	length = startLength + (mouseY - startDragMousePos.y) / 20;
	airship.setLength(length);
    }

    protected void mouseYdragsCenterOffsetY()
    {
	centerOffset.y = startCenterOffsetY + (mouseY - startDragMousePos.y) / 5;
    }

    public void keyPressed()
    {
	if (key == CODED)
	{
	    switch (keyCode)
	    {
		case UP:
		    numberOfKeels++;
		    makeAirship();
		    break;

		case DOWN:
		    if (numberOfKeels > 2)
			numberOfKeels--;
		    makeAirship();

		    break;

		case RIGHT:
		    keelLength++;
		    makeAirship();
		    break;
		case LEFT:
		    keelLength--;
		    makeAirship();
		    break;
		default:
		    break;
	    }
	}
	switch(key)
	    {
		case 'e':
		    export = true;
		    break;
	    }
    }

    public void mouseMoved()
    {
	highLightedLayer = picker.get(mouseX, mouseY);
    }

    public int getWidth()
    {
	return width;
    }

    public void setWidth(int width)
    {
	this.width = width;
    }

    public int getHeight()
    {
	return height;
    }

    public void setHeight(int height)
    {
	this.height = height;
    }

    public float getZoomFactor()
    {
	return zoomFactor;
    }

    public void setZoomFactor(float zoomFactor)
    {
	this.zoomFactor = zoomFactor;
    }

    public float getWorldXRotation()
    {
	return worldXRotation;
    }

    public void setWorldXRotation(float worldRotation)
    {
	this.worldXRotation = worldRotation;
    }

    public float getWorldYRotation()
    {
	return worldYRotation;
    }

    public void setWorldYRotation(float worldYRotation)
    {
	this.worldYRotation = worldYRotation;
    }

    /*public PFont font()
    {
	return font;
    }*/

    static private Airship makeAirship()
    {
	airship = new Airship(length, keelLength, numberOfKeels, ropePointsPerKeel);
	return airship;
    }

    public PVector getCenterOffset()
    {
	return centerOffset;
    }
    
    public void setCenterOffset(PVector centerOffset)
    {
	this.centerOffset = centerOffset;
    }

    public boolean isOrtho()
    {
	return ortho;
    }

    public void setOrtho(boolean ortho)
    {
	this.ortho = ortho;
    }
    
    

    public static void main(String[] args)
    {
	Main.main(null);
    }

}
