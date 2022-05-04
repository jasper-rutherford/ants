package Revamped;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_SPACE;

public class Menu implements KeyListener
{
    /**
     * The simulation. This is needed when the menu interacts with the simulation according to key inputs
     */
    private Map map;

    /**
     * Whether or not the simulation is paused
     */
    private boolean paused = true;

    /**
     * Creates a Keylistener that syncs with the supplied simulation
     *
     * @param map the simulation to connect the menu to
     */
    public Menu(Map map)
    {
        this.map = map;
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
        // SPACE - pause/play simulation
        if (e.getKeyCode() == VK_SPACE)
        {
            //tells the simulation to try to toggle pause (see togglePause() for more details)
            paused = !paused;
        }

        // R - restarts the map
        else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R')
        {
            map.restart();
        }

        // ESC - close the program
        else if (e.getKeyCode() == VK_ESCAPE)
        {
            System.exit(0);
        }

        // 1 - Switches to overlaying ants
        else if (e.getKeyChar() == '1')
        {
            map.switchOverlay(1);
        }

        // 2 - Switches to overlaying food
        else if (e.getKeyChar() == '2')
        {
            map.switchOverlay(2);
        }

        // 3 - Switches to overlaying
        else if (e.getKeyChar() == '3')
        {
            map.switchOverlay(2);
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

    /**
     * gets whether or not the simulation should be paused
     * @return true is paused, false otherwise
     */
    public boolean isPaused()
    {
        return paused;
    }
}
