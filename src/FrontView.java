import processing.core.PVector;


public class FrontView extends AirshipViewer
{

    @Override
    protected void initAirshipViewer()
    {
	setWorldYRotation(HALF_PI);
	setHeight(500);
	setWidth(700);
	setOrtho(true);
    }

    @Override
    protected void drawGridsAndRulers()
    {
	Painter.drawRuler(this.g, 10, 5, 20, new PVector(0, 0, 1));
	Painter.drawRuler(this.g, 10, 5, 20, new PVector(0, -1, 0));
	//drawRuler(15, 5, 20, new PVector(1, 0, 0));
	//drawRuler(15, 5, 20, new PVector(-1, 0, 0));
	Painter.drawGrid(this.g, new PVector(0, -10, -10), new PVector(0, 0, 20), new PVector(0, 20, 0), 20, 20);
    }

    @Override
    protected void drawLabels()
    {
	text("Front View", 30, 30);
    }

    @Override
    void mouseDraggedActions()
    {
	mouseYdragsLength();
	
    }

}
