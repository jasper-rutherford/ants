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

    public SettingsTab()
    {
        simulation = null;
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
        if (currentGeneration != 0)
        {
            //update stats
        }
    }

    public void render()
    {
        //
    }

    public void keyPressed()
    {
        if (currentGeneration == 0)
        {
            //press 1 to change the number of generations
            if (key == '1')
            {
                changeTotalGenerations();
            }

            //press 2 to change the number of ticks per second
            else if (key == '2')
            {
                changeTicksPerSecond();
            }

            //press 3 to change the number of ticks per generation
            else if (key == '3')
            {
                changeTicksPerGeneration();
            }

            //press 4 to change the number of ants per generation
            else if (key == '4')
            {
                changeAntsPerGeneration();
            }
        }
        else
        {

        }
    }

    /**
     *   Updates the total number of generations by 1.
     *   increases if shift not held, decreases if shift held
     *   will not go below 1
     */
    private void changeTotalGenerations()
    {
        if (!shiftHeld)
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
     *   increases if shift not held, decreases if shift held
     *   will not go below 1
     */
    private void changeTicksPerSecond()
    {
        if (!shiftHeld)
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
     *   increases if shift not held, decreases if shift held
     *   will not go below 1
     */
    private void changeTicksPerGeneration()
    {
        if (!shiftHeld)
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
     *   increases if shift not held, decreases if shift held
     *   will not go below 1
     */
    private void changeAntsPerGeneration()
    {
        if (!shiftHeld)
        {
            antsPerGeneration++;
        }
        else if (antsPerGeneration > 1)
        {
            antsPerGeneration--;
        }
    }
}
