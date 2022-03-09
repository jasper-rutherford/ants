package MenuStuff;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tab
{
    //represents if this tab is the current active tab
    protected boolean selected;

    /**
     * default consrtuctor for the MenuStuff.Tab class
     */
    public Tab()
    {
        //tabs default to not selected
        selected = false;
    }

    /**
     * ticks/updates the tab
     * will typically be overridden
     */
    public void tick()
    {
    }

    /**
     * renders the tab
     * will typically be overridden
     * @param g a graphics object for rendering
     */
    public void render(Graphics g)
    {
    }

    /**
     * reacts to the supplied key presses
     * @param e a KeyEvent representing the key that has been pressed
     */
    public void keyPressed(KeyEvent e)
    {

    }

    /**
     * selects the tab
     */
    public void select()
    {
        //set selected to true
        selected = true;

        //update the tab's stats so that old information is never shown
        tick();
    }

    /**
     * deselect the tab
     */
    public void deselect()
    {
        //set selected to false
        selected = false;
    }
}
