import processing.core.PVector;


public class TopView extends AirshipViewer
{
 

    @Override
    protected void initAirshipViewer()
    {
	setOrtho(true);
	setWorldXRotation(HALF_PI);
	setHeight(500);
	setWidth(700);
	setZoomFactor(24);
    }
    
    public void drawLabels()
    {
	text("Top View", 30, 30);
	
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
