import processing.core.PVector;


public class FrontView extends AirshipViewer
{

    @Override
    protected void initAirshipViewer()
    {
	setWorldYRotation(HALF_PI);
	setOpenGL(true);
	setOrtho(true);
    }

    @Override
    protected void drawGridsAndRulers()
    {
	drawRuler(15, 5, 20, new PVector(0, 0, 1));
	drawRuler(15, 5, 20, new PVector(1, 0, 0));
	drawRuler(15, 5, 20, new PVector(-1, 0, 0));
	drawGrid(new PVector(0, -5, -5), new PVector(0, 0, 10), new PVector(0, 10, 0), 10, 10);
    }

    @Override
    protected void drawLabels()
    {
	// TODO Auto-generated method stub

    }

    @Override
    void mouseDraggedActions()
    {
	mouseYdragsLength();
	
    }

}
