package Revamped;

import java.util.ArrayList;

/**
 * Gets everything setup and running
 */
public class Main
{
    /**
     * How many ticks to let the ants search for paths
     */
    private static int searchLength = 50;

    public static void main(String args[])
    {
        //make the map
        Map map = new Map(10, 2);

        //make the panel
        Panel panel = new Panel(map);

        //make the frame
        Frame frame = new Frame(panel);

        //make the menu
        Menu menu = new Menu(map);

        //add the menu to the frame
        frame.addKeyListener(menu);

        //make the frame visible
        frame.setVisible(true);                     //setvisible calls paintcomponent() in panel, make sure this is always last to prevent bugs

        boolean temp = true;

        //simulate the map
        double ticksPerSecond = 30.0;
        long lastTime = System.nanoTime();
        double ns = 1000000000 / ticksPerSecond;    //Amount of time between ticks
        double delta = 0;
        while (temp)
        {
            //the time since the last loop
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            //render everything every step of the loop
            frame.repaint();

            int searching = searchLength;
            int returning = 0;

            //completes however many ticks are due
            while (delta >= 1)
            {
                delta--;

                //if the menu is not paused
                if (!menu.isPaused())
                {
                    //if we are currently searching
                    if (searching > 0)
                    {
                        //update the map with zero to indicate search mode
                        map.update(0);

                        //update how many ticks are remaining for the search
                        searching--;

                        //if there is no more time for searching
                        if (searching == 0)
                        {
                            //start the return counter
                            returning = searchLength;
                        }
                    }
                    //if we are currently returning
                    else if (returning > 0)
                    {
                        //update the map with 1 to indicate return mode
                        map.update(1);

                        //update how many ticks are remaining for the search
                        returning--;

                        //if there is no more time for returning
                        if (returning == 0)
                        {
                            //update the pheromones on the map according to the paths which were found
                            map.updatePheromones();

                            //restart the search counter
                            searching = searchLength;
                        }
                    }
                }
            }
        }
    }
}
