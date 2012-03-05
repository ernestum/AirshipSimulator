import processing.core.PGraphics;
import processing.core.PVector;


public class TopView extends AirshipView
{
 

    public TopView(MasterViewer a, PVector pos)
    {
	super(a, pos);
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void initAirshipView()
    {
	//setOrtho(true);
    }
    
    public void drawLabels(PGraphics g)
    {
	g.text("Top View", 30, 30);
	
    }
    
    
    
    protected void drawGridsAndRulers(PGraphics g)
    {
	
	//drawRuler(15, 5, 20, new PVector(0, 1, 0));
	Painter.drawRuler(g, 15, 5, 20, new PVector(0, 0, 1));
	Painter.drawRuler(g, 15, 5, 20, new PVector(1, 0, 0));
	Painter.drawRuler(g, 15, 5, 20, new PVector(-1, 0, 0));
	Painter.drawGrid(g, new PVector(-20, 0, -20), new PVector(40, 0, 0), new PVector(0, 0, 40), 40, 40);
    }

    @Override
    void mouseDraggedActions()
    {
	a.mouseXdragsRotation();
	a.mouseYdragsRoll();
    }
}
