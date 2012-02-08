
import airship.Airship;
import picking.Picker;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import processing.opengl.*;

public class Visualisation extends PApplet
{
    float   centerX, centerY, centerZ;

    double  length	    = 18 * 1;
    double  keelLength	= 20 * 1;
    int     numberOfKeels     = 3;
    int     ropePointsPerKeel = 15;

    Airship a		 = makeAirship();

    PFont   font	      = createFont("Arial", 71);

    boolean openGL	    = true;

    Picker  picker;

    public void setup()
    {
	if (openGL)
	{
	    size(800, 600, OPENGL); //Faster, better looking, better fonts but not platform independent
	    hint(DISABLE_OPENGL_2X_SMOOTH);
	} else
	    size(800, 600, P3D);

	smooth();
	centerX = width / 2;
	centerY = height / 2 + 20;
	picker = new Picker(this);
    }

    float worldRotation    = 0;
    int   highLightedLayer = 6;

    public void draw()
    {
	stroke(0);
	fill(0);
	textFont(font, 16);
	background(0xFFFFFF);
	
	pushMatrix();

	translate(centerX, centerY, centerZ);
	scale(30);
	rotateY(worldRotation);
	Painter.draw(this, a, highLightedLayer, picker);
	textAlign(LEFT);
	drawRuler(15, 5, 20, new PVector(0, 1, 0));
	drawRuler(15, 5, 20, new PVector(0, 0, 1));
	drawRuler(15, 5, 20, new PVector(1, 0, 0));
	drawRuler(15, 5, 20, new PVector(-1, 0, 0));
	
	popMatrix();

	double volume = Math.round(a.calcVolume() * 10) / 10.0;
	double liftH2 = Math.round(volume * 0.9 * 10) / 10.0;
	double liftM4 = Math.round(volume * 0.4 * 10) / 10.0;
	double l = Math.round(a.getLength() * 10) / 10.0;
	double maxR = Math.round(a.getMaxRadius() * 10) / 10.0;

	textAlign(LEFT);
	textFont(font, 16);
	fill(0);
	text("Drag the mouse, play with the arrow keys!", 30, 30);
	text("Length:\n" + "Keel Length: \n" + "Maximum Radius:\n" + "Volume:\n" + "Lift with hydrogen:\n"
		+ "Lift with methane:", 30, 50);
	textAlign(RIGHT);
	text(l + "m\n" + a.getKeelLength() + "m\n" + maxR + "m\n" + volume + "m3\n~" + liftH2 + "kg\n~" + liftM4 + "kg",
		300, 50);
	
	picker.stop();
    }

    public void drawRuler(float length, float sectionsDistance, float SectionsSize, PVector direction)
    {
	fill(0, 150, 190);
	stroke(0, 150, 190);
	line(0, 0, 0, direction.x * length, direction.y * length, direction.z * length);
	textFont(font, 0.7f);
	ellipseMode(CENTER);
	for (int i = 0; i < (int) (length / sectionsDistance); i++)
	{
	    pushMatrix();
	    translate(direction.x * i * sectionsDistance, direction.y * i * sectionsDistance, direction.z * i
		    * sectionsDistance);
	    rotateY(-(worldRotation));
	    text(Math.round(i * sectionsDistance) + "", 0, 0);
	    popMatrix();
	}
	sectionsDistance /= 5;
	for (int i = 0; i < (int) (length / sectionsDistance); i++)
	{
	    pushMatrix();
	    translate(direction.x * i * sectionsDistance, direction.y * i * sectionsDistance, direction.z * i
		    * sectionsDistance);
	    line(0, -.1f, 0, 0,.1f, 0);
	    popMatrix();
	}
    }

    PVector startDragMousePos = null;
    float   startDragRotation = 0;
    double  startLength       = 0;

    public void mousePressed()
    {
	startDragMousePos = new PVector(mouseX, mouseY);
	startDragRotation = worldRotation;
	startLength = a.getLength();
    }

    public void mouseDragged()
    {
	worldRotation = startDragRotation + (mouseX - startDragMousePos.x) / 200;
	length = startLength + (mouseY - startDragMousePos.y) / 20;
	a.setLength(length);

    }

    public void keyPressed()
    {
	if (key == CODED)
	{
	    switch (keyCode)
	    {
		case UP:
		    /*numberOfKeels++;
		    a = makeAirship();*/
		    highLightedLayer++;
		    System.out.println(highLightedLayer);
		    break;

		case DOWN:
		    /*if (numberOfKeels > 2)
		    numberOfKeels--;
		    a = makeAirship();*/
		    highLightedLayer--;
		    System.out.println(highLightedLayer);
		    break;

		case RIGHT:
		    keelLength++;
		    a = makeAirship();
		    break;
		case LEFT:
		    keelLength--;
		    a = makeAirship();
		    break;
		default:
		    break;
	    }
	}
    }

    public void mouseMoved()
    {
	int id = picker.get(mouseX, mouseY);
	if(id != -1) System.out.println(id);
	highLightedLayer = id;
    }

    private Airship makeAirship()
    {
	return new Airship(length, keelLength, numberOfKeels, ropePointsPerKeel);
    }

    public static void main(String args[])
    {
	PApplet.main(new String[] { "Visualisation" });
    }
}
