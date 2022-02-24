import javax.swing.*;
import java.awt.*;

/**
 * Used to run the simulation and update the menu
 */
public class Ticker extends JPanel
{
    private Frame frame;

    private Menu menu;
    private Simulation simulation;

    private long lastTime;
    private double ticksPerSecond;      //The desired number of ticks per second
    private double ns;                  //Amount of time between ticks
    private double delta = 0;           //the number of ticks due for completion

    private boolean restart = false;

    public Ticker(Frame frame)
    {
        this.frame = frame;

        menu = new Menu();
        simulation = new Simulation();

        lastTime = System.nanoTime();
        setTicksPerSecond(30);
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void run()
    {
        while (!restart)
        {
            frame.repaint();                    //renders as much as possible
        }
    }

    public void tryTick()
    {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;     //calculates how many ticks are due for completion
        lastTime = now;
        while (delta >= 1)                  //completes that many ticks
        {
            simulation.tick();
            menu.tick();

            delta--;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //try to tick all the time
        tryTick();

        //fill a black background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 960, 960);

        //render the menu
        menu.render(g);

        //render the simulation
        simulation.render(g);
    }

    public void setTicksPerSecond(double tps)
    {
        ticksPerSecond = tps;
        ns = 1000000000 / ticksPerSecond;
    }
}
