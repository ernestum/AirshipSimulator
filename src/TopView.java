import processing.core.PVector;


public class TopView extends AirshipViewer
{
 

    @Override
    protected void initAirshipViewer()
    {
	setOrtho(true);
	setWorldXRotation(HALF_PI);
	setZoomFactor(24);
    }
    
    public void drawLabels()
    {
	double volume = Math.round(airship.calcVolume() * 10) / 10.0;
	double liftH2 = Math.round(volume * 0.9 * 10) / 10.0;
	double liftM4 = Math.round(volume * 0.4 * 10) / 10.0;
	double l = Math.round(airship.getLength() * 10) / 10.0;
	double maxR = Math.round(airship.getMaxRadius() * 10) / 10.0;

	textAlign(LEFT);
	textFont(font(), 16);
	fill(0);
	text("Drag the mouse, play with the arrow keys!", 30, 30);
	text("Length:\n" + "Keel Length: \n" + "Maximum Radius:\n" + "Volume:\n" + "Lift with hydrogen:\n"
		+ "Lift with methane:", 30, 50);
	textAlign(RIGHT);
	text(l + "m\n" + airship.getKeelLength() + "m\n" + maxR + "m\n" + volume + "m3\n~" + liftH2 + "kg\n~" + liftM4 + "kg",
		300, 50);
    }
    
    protected void drawGridsAndRulers()
    {
	textAlign(LEFT);
	//drawRuler(15, 5, 20, new PVector(0, 1, 0));
	drawRuler(15, 5, 20, new PVector(0, 0, 1));
	drawRuler(15, 5, 20, new PVector(1, 0, 0));
	drawRuler(15, 5, 20, new PVector(-1, 0, 0));
	drawGrid(new PVector(-20, 0, -20), new PVector(40, 0, 0), new PVector(0, 0, 40), 40, 40);
    }

    @Override
    void mouseDraggedActions()
    {
	mouseYdragsLength();
    }
}
