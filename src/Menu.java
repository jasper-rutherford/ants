import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class Menu implements KeyListener
{
    //The simulation. This is needed when the menu interacts with the simulation according to key inputs
    private Simulation simulation;

    //the tabs contained in the menu
    private Tab[] tabs;

    //the index of the tab that is currently selected/active.
    private int currentTab;

    /**
     * Creates a Menu that syncs with the supplied simulation
     * @param simulation the simulation to connect the menu to
     */
    public Menu(Simulation simulation)
    {
        this.simulation = simulation;

        //initialize tabs
        tabs = new Tab[3];
        tabs[0] = new AntTab(simulation.getAnts());
        tabs[1] = new ColonyTab(simulation.getColonies());
        tabs[2] = new SettingsTab(simulation);

        //current tab defaults to settings tab.
        currentTab = 2;
        tabs[2].select();
    }

    /**
     * ticks the menu.
     * Currently just ticks whichever tab is selected
     */
    public void tick()
    {
        tabs[currentTab].tick();
    }

    /**
     * renders all the tabs in the menu
     * @param g a graphics object for use with rendering
     */
    public void render(Graphics g)
    {
        //loop through all tabs
        for (Tab tab : tabs)
        {
            //render the tab
            tab.render(g);
        }
    }

    /**
     * listens for when keys are typed.
     * implemented from KeyListener.
     * Currently not used.
     * @param e the KeyEvent passed in via the KeyListener
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * listen for when keys are pressed and react accordingly.
     * implemented from KeyListener.
     * @param e the KeyEvent passed in via the KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        // TAB - advance to next tab
        if (e.getKeyChar() == 't' || e.getKeyChar() == 'T')
        {
            //deselect current tab
            tabs[currentTab].deselect();

            //advance to next tab
            currentTab = (currentTab + 1) % 3;

            //select new tab
            tabs[currentTab].select();

            //tick the new tab so that old information is not displayed
            tabs[currentTab].tick();
            System.out.println("tabbed to tab " + currentTab);
        }

        // SPACE - pause/play simulation
        else if (e.getKeyCode() == VK_SPACE)
        {
            //tells the simulation to try to toggle pause (see togglePause() for more details)
            simulation.togglePause();
        }

        // ENTER - sets up the simulation (only if not already set up)
        else if (e.getKeyCode() == VK_ENTER && simulation.getAnts().size() == 0)
        {
            //sets up the simulation
            System.out.println("setting up the simulation");
            simulation.setup(8, 160, 10,30 * 60 * 2,30);
            System.out.println("set up the simulation");
        }

        // R - resets the current generation back to the beginning
        else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R')
        {
            //TODO: reset generation button
            System.out.println("no reset functionality yet :) TODO");
        }

        // ESC - close the program
        else if (e.getKeyCode() == VK_ESCAPE)
        {
            System.exit(0);
        }

        // All other key input is sent to the selected tab
        else
        {
            tabs[currentTab].keyPressed(e);
        }
    }

    /**
     * listens for when keys are released.
     * implemented from KeyListener.
     * Currently not used.
     * @param e the KeyEvent passed in via the KeyListener
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
