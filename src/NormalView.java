import processing.core.PVector;


public class NormalView extends AirshipViewer
{

    @Override
    protected void initAirshipViewer()
    {
	setCenterOffset(new PVector(0, 30, 0));
    }

    @Override
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
    protected void drawLabels()
    {
	// TODO Auto-generated method stub

    }

    @Override
    void mouseDraggedActions()
    {
	mouseXdragsRotationY();
	mouseYdragsCenterOffsetY();
    }

}
