package Revamped;

import javax.swing.*;
import java.awt.*;

/**
 * Used to render the simulation and the menu
 */
public class Panel extends JPanel
{
    /**
     * runs ant simulations. Panel just needs to render it.
     */
    private final Map map;

    /**
     * Constructor for the panel.
     * @param map the simulation to render
     */
    public Panel(Map map)
    {
        this.map = map;
    }

    /**
     * Used to render stuff.
     * @param g the Graphics object needed for rendering
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //fill a black background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 980, 670); //TODO: fix this

        //render the simulation
        map.render(g);
    }
}
