package MenuStuff;

import Simulation.Simulation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class Menu implements KeyListener
{
    /**
     * The simulation. This is needed when the menu interacts with the simulation according to key inputs
     */
    private Simulation simulation;

    /**
     * Creates a Menu that syncs with the supplied simulation
     *
     * @param simulation the simulation to connect the menu to
     */
    public Menu(Simulation simulation)
    {
        this.simulation = simulation;
    }

    /**
     * ticks the menu.
     * Currently does nothing (TODO)
     */
    public void tick()
    {
    }

    /**
     * renders all the tabs in the menu (TODO)
     *
     * @param g a graphics object for use with rendering
     */
    public void render(Graphics g)
    {
    }

    /**
     * listens for when keys are typed.
     * implemented from KeyListener.
     * Currently not used.
     *
     * @param e the KeyEvent passed in via the KeyListener
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * listen for when keys are pressed and react accordingly.
     * implemented from KeyListener.
     *
     * @param e the KeyEvent passed in via the KeyListener
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
//        // TAB - advance to next tab
//        if (e.getKeyChar() == 't' || e.getKeyChar() == 'T')
//        {
//            //deselect current tab
//            tabs[currentTab].deselect();
//
//            //advance to next tab
//            currentTab = (currentTab + 1) % 3;
//
//            //select new tab
//            tabs[currentTab].select();
//
//            //tick the new tab so that old information is not displayed
//            tabs[currentTab].tick();
//            System.out.println("tabbed to tab " + currentTab);
//        }

        // SPACE - pause/play simulation
        if (e.getKeyCode() == VK_SPACE)
        {
            //tells the simulation to try to toggle pause (see togglePause() for more details)
            simulation.togglePause();
        }

        // G - toggles grid lines display
        else if (e.getKeyChar() == 'g' || e.getKeyChar() == 'G')
        {
//            simulation.getMap().toggleDrawGrid(); TODO: fix this (gridlines)
        }

        // L - toggles playback loop
        else if (e.getKeyChar() == 'l' || e.getKeyChar() == 'L')
        {
            simulation.togglePlaybackLoop();
        }

        // P - toggles playback
        else if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P')
        {
            simulation.togglePlayback();
        }

        // R - resets the playback colony to the beginning
        else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R')
        {
            //TODO: reset functionality?
            System.out.println("no reset functionality yet :) TODO");
        }

        // ESC - close the program
        else if (e.getKeyCode() == VK_ESCAPE)
        {
            System.exit(0);
        }

        // All other key input is sent to the selected tab todo
        else
        {
//            tabs[currentTab].keyPressed(e);
        }
    }

    /**
     * listens for when keys are released.
     * implemented from KeyListener.
     * Currently not used.
     *
     * @param e the KeyEvent passed in via the KeyListener
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
