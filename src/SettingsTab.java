import java.awt.event.KeyEvent;

public class SettingsTab extends Tab
{
    private Simulation simulation;

    private int currentGeneration;      //the current generation of ants
    private int generationsRemaining;   //how many more generations are planned
    private int totalGenerations;       //how many generations are planned -

    private int ticksPerSecond;         //the number of ticks simulated per second -

    private int ticksRemaining;         //how many ticks are left until the current generation is complete
    private int ticksPerGeneration;     //how many ticks it takes to complete a generation -

    private int remainingAnts;          //how many ants alive in the current generation
    private int antsPerGeneration;              //the total number of ants to be simulated per generation -

    public SettingsTab(Simulation simulation)
    {
        super();

        this.simulation = simulation;
        currentGeneration = 0;
        generationsRemaining = 0;
        totalGenerations = 0;

        ticksPerSecond = 30;

        ticksRemaining = 0;
        ticksPerGeneration = 120 * ticksPerSecond;

        remainingAnts = 0;
        antsPerGeneration = 160;
    }

    public void tick()
    {
        //if the simulation has not been set up yet, then this tab has different behavior
        if (currentGeneration != 0)
        {
            //update stats
        }
        else
        {

        }
    }

    public void render()
    {
        //
    }

    /**
     * reacts to the supplied key presses
     * @param e a KeyEvent representing the key that has been pressed
     */
    public void keyPressed(KeyEvent e)
    {

    }

    /**
     *   Updates the total number of generations by 1.
     *   increases if increase is true, decreases otherwise
     *   will not go below 1
     */
    private void changeTotalGenerations(boolean increase)
    {
        if (increase)
        {
            totalGenerations++;
        }
        else if (totalGenerations > 1)
        {
            totalGenerations--;
        }
    }

    /**
     *   Updates the total number of ticks per second by 1.
     *   increases if increase is true, decreases otherwise
     *   will not go below 1
     */
    private void changeTicksPerSecond(boolean increase)
    {
        if (increase)
        {
            ticksPerSecond++;
        }
        else if (ticksPerSecond > 1)
        {
            ticksPerSecond--;
        }
    }

    /**
     *   Updates the total number of ticks per generation by 1.
     *   increases if increase is true, decreases otherwise
     *   will not go below 1
     */
    private void changeTicksPerGeneration(boolean increase)
    {
        if (increase)
        {
            ticksPerGeneration++;
        }
        else if (ticksPerGeneration > 1)
        {
            ticksPerGeneration--;
        }
    }

    /**
     *   Updates the total number of ants per generation by 1.
     *   increases if increase is true, decreases otherwise
     *   will not go below 1
     */
    private void changeAntsPerGeneration(boolean increase)
    {
        if (increase)
        {
            antsPerGeneration++;
        }
        else if (antsPerGeneration > 1)
        {
            antsPerGeneration--;
        }
    }
}
