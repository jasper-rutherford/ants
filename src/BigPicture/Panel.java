package BigPicture;

import MenuStuff.Menu;
import Simulation.Simulation;

import javax.swing.*;
import java.awt.*;

/**
 * Used to render the simulation and the menu
 */
public class Panel extends JPanel
{
    /**
     * Handles key inputs. Panel just needs to render it.
     */
    private final Menu menu;

    /**
     * runs ant simulations. Panel just needs to render it.
     */
    private final Simulation simulation;

    /**
     * Constructor for the panel.
     * @param simulation the simulation to render
     * @param menu the menu to render
     */
    public Panel(Simulation simulation, Menu menu)
    {
            menu.tick();
        this.simulation = simulation;
        this.menu = menu;
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
        simulation.render(g);

        //render the menu
        menu.render(g);
    }
}
