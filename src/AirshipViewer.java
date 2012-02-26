import airship.Airship;
import picking.Picker;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.opengl.*;

/**
 * Comments Everywhere!
 * 
 * @author maximilian
 * 
 */
public abstract class AirshipViewer extends PApplet
{
    private boolean       openGL	    = false;
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

    private PFont	 font	      = createFont("Arial", 71);

    private Picker	picker;

    protected abstract void initAirshipViewer();

    public void setup()
    {
	initAirshipViewer();

	center.x = width / 2;
	center.y = height / 2;

	if (ortho)
	{
	    if (openGL)
	    {
		System.err.println("Ortho will not work with OpenGL -> OpenGL is deactivated!");
		openGL = false;

	    } 
	    center.x = width;
	    center.y = height;
	}

	if (openGL)
	{
	    size(width, height, OPENGL); //Faster, better looking, better fonts but not platform independent
	    //hint(ENABLE_DEPTH_SORT);
	    hint(DISABLE_OPENGL_2X_SMOOTH);
	}
	else
	{
	    size(width, height, P3D);
	}

	
	addMouseWheelListener(new java.awt.event.MouseWheelListener() {
	    public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt)
	    {
		mouseWheel(evt.getWheelRotation());
	    }
	});

	smooth();

	picker = new Picker(this);
    }

    

    public void draw()
    {
	if (ortho)
	    ortho(0, width, 0, height, -1000, 1000);

	pushMatrix();
	translate(0, 6, 0);
	drawLabels();
	popMatrix();
	
	stroke(0);
	fill(0);
	textFont(font, 16);
	background(0xFFFFFF);

	
	pushMatrix();

	translate(center.x + centerOffset.x, center.y + centerOffset.y, center.z + centerOffset.z);
	scale(zoomFactor);
	rotateX(worldXRotation);
	rotateY(worldYRotation);
	
	Painter.draw(this, airship, highLightedLayer, picker);
	if(ortho)
	    translate(-width/2, -height/2);
	
	drawGridsAndRulers();
	
	popMatrix();
	
	picker.stop();
	
    }

    protected abstract void drawGridsAndRulers();

    protected abstract void drawLabels();

    public void drawRuler(float length, float sectionsDistance, float SectionsSize, PVector direction)
    {
	fill(100);
	stroke(0, 150, 190);
	line(0, 0, 0, direction.x * length, direction.y * length, direction.z * length);
	textFont(font, 0.7f);
	ellipseMode(CENTER);
	for (int i = 0; i < (int) (length / sectionsDistance); i++)
	{
	    pushMatrix();
	    translate(direction.x * i * sectionsDistance, direction.y * i * sectionsDistance, direction.z * i
		    * sectionsDistance);
	    rotateY(-(worldYRotation));
	    rotateX(-(worldXRotation));
	    text(Math.round(i * sectionsDistance) + "", 0, 0);
	    popMatrix();
	}
	sectionsDistance /= 5;
	for (int i = 0; i < (int) (length / sectionsDistance); i++)
	{
	    pushMatrix();
	    translate(direction.x * i * sectionsDistance, direction.y * i * sectionsDistance, direction.z * i
		    * sectionsDistance);
	    line(0, -.1f, 0, 0, .1f, 0);
	    popMatrix();
	}
    }

    public void drawGrid(PVector pos, PVector span1, PVector span2, int span1Steps, int span2Steps)
    {
	stroke(100);
	strokeWeight(0.1f);
	for (int i = 0; i < span1Steps; i++)
	{
	    PVector start = PVector.add(pos, PVector.mult(span1, (1 / (float) span1Steps) * i));
	    PVector end = PVector.add(start, span2);
	    line(start.x, start.y, start.z, end.x, end.y, end.z);
	}

	for (int i = 0; i < span2Steps; i++)
	{
	    PVector start = PVector.add(pos, PVector.mult(span2, (1 / (float) span2Steps) * i));
	    PVector end = PVector.add(start, span1);
	    line(start.x, start.y, start.z, end.x, end.y, end.z);
	}

    }

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

    public PFont font()
    {
	return font;
    }

    static private Airship makeAirship()
    {
	airship = new Airship(length, keelLength, numberOfKeels, ropePointsPerKeel);
	return airship;
    }

    public boolean isOpenGL()
    {
	return openGL;
    }

    public void setOpenGL(boolean openGL)
    {
	this.openGL = openGL;
    }

    public PVector getCenterOffset()
    {
	return centerOffset;
    }
    
    

    public void setCenterOffset(PVector centerOffset)
    {
	System.out.println("somebody setting the offset");
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
