public class Tab
{
    protected boolean shiftHeld;

    public Tab()
    {
        shiftHeld = false;
    }

    public void tick()
    {
    }

    public void render()
    {
    }

    public void keyPressed()
    {
        if (keyCode == SHIFT)
        {
            shiftHeld = true;
        }
    }

    public void keyReleased()
    {
        if (keyCode == SHIFT)
        {
            shiftHeld = false;
        }
    }
}
