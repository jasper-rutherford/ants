import java.awt.*;

public class Tab
{
    protected boolean selected;
    protected boolean shiftHeld;

    public Tab()
    {
        selected = false;
        shiftHeld = false;
    }

    public void tick()
    {
    }

    public void render(Graphics g)
    {
    }

    public void keyPressed()
    {

    }

    public void keyReleased()
    {

    }

    public void select()
    {
        selected = true;
    }

    public void deselect()
    {
        selected = false;
    }
}
