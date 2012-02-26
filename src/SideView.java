import processing.core.PVector;


public class SideView extends AirshipViewer
{

    @Override
    protected void initAirshipViewer()
    {
	setWorldYRotation(0);
	setHeight(500);
	setWidth(700);
	setOrtho(true);
    }

    @Override
    protected void drawGridsAndRulers()
    {
	drawRuler(10, 5, 20, new PVector(1, 0, 0));
	drawRuler(10, 5, 20, new PVector(0, -1, 0));
	//drawRuler(15, 5, 20, new PVector(1, 0, 0));
	//drawRuler(15, 5, 20, new PVector(-1, 0, 0));
	drawGrid(new PVector(-10, -10, 0), new PVector(20, 0, 0), new PVector(0, 20, 0), 20, 20);
    }

    @Override
    protected void drawLabels()
    {
	text("Side View", 30, 30);
    }

    @Override
    void mouseDraggedActions()
    {
	mouseYdragsLength();
	
    }

}
