public class Ticker
{
    private long lastTime;
    private float ticksPerSecond;
    private double ns;
    private double delta;

    private boolean paused;

    private Simulation simulation;
    private Menu menu;
    
    /**
    *   Used to tick the map/menu at consistent rates.
    *   Defaults to 30 ticks per second
    */
    public Ticker()
    {
        lastTime = System.nanoTime();
        ticksPerSecond = 30.0;
        ns = 1000000000 / ticksPerSecond;
        delta = 0;

        paused = true;

        simulation = null;
        menu = new Menu();
    }
    
    /**
    *   updates the simulation
    */
    public void tick()
    {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        while(delta >= 1)
        {
            if (!paused)
            {
                if (simulation != null)
                {
                    simulation.tick();
                }
                menu.tick();
            }
            delta--;
        }
    }
    
    public void setTicksPerSecond(float ticksPerSecond)
    {
        this.ticksPerSecond = ticksPerSecond;
        ns = 1000000000 / ticksPerSecond;
    }

    public float getTicksPerSecond()
    {
        return ticksPerSecond;
    }

    public void render()
    {
        if (simulation != null)
        {
            simulation.render();
        }        
        menu.render();
    }

    public void keyPressed()
    {
        menu.keyPressed();
    }

    public void keyReleased()
    {
        menu.keyReleased();
    }

    public void setupSimulation(Simulation simulation, int ticksPerSecond)
    {
        this.simulation = simulation;
        setTicksPerSecond(ticksPerSecond);
    }
}