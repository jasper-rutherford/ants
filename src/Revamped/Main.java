package Revamped;

/**
 * Gets everything setup and running
 */
public class Main
{
    public static void main(String args[])
    {
        //make the map
        Map map = new Map(5, 2);

        //make the panel
        Panel panel = new Panel(map);

        //make the frame
        Frame frame = new Frame(panel);

        //make the frame visible
        frame.setVisible(true);                     //setvisible calls paintcomponent() in panel, make sure this is always last to prevent bugs


        boolean temp = true;

        //simulate the map
        double ticksPerSecond = 5.0;
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

            //completes however many ticks are due
            while (delta >= 1)
            {
                map.update();
            }
        }
    }
}
