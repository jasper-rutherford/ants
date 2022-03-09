package BigPicture;

import MenuStuff.Menu;
import Simulation.Simulation;

/**
 * Gets everything setup and running
 */
public class Runner
{
    public static void main(String args[])
    {
        //make the frame
        Frame frame = new Frame();

        //make the simulation
        Simulation simulation = new Simulation(frame);

        //make the menu
        Menu menu = new Menu(simulation);

        //make the panel
        Panel panel = new Panel(simulation, menu);

        //add panel to frame
        frame.add(panel);

        //add menu to frame (as keyListener)
        frame.addKeyListener(menu);

        //make the frame visible
        frame.setVisible(true);                     //setvisible calls paintcomponent() in panel, make sure this is always last to prevent bugs

        //start the simulation
        simulation.run();
    }
}
