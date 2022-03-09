package Simulation;

import BigPicture.Frame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Simulation
{
    /**
     * used for rendering
     */
    private Frame frame;

    /**
     * the colonies that will be simulated
     */
    private ArrayList<Colony> colonies;

    /**
     * the current generation
     */
    private int generation;

    /**
     * how many generations to simulate
     */
    private int generationsRemaining;

    /**
     * the amount of ticks per second to simulate at when showing the best colony to the user
     */
    private final double ticksPerSecond;

    /**
     * the maximum number of ticks to simulate per generation
     */
    private final int ticksPerGeneration;

    /**
     * the maximum amount of ticks remaining in the current generation
     */
    private int ticksRemainingInCurrentGeneration;

    /**
     * the number of colonies to simulate per generation
     */
    private final int numColonies;

    /**
     * the radius of each queen's stash
     */
    private int stashRadius;

    /**
     * each queen's worker radius (this dictates how far away from the queen that workers can spawn)
     */
    private int workerRadius;

    /**
     * whether or not the playback is paused
     */
    private boolean paused;

    /**
     * whether or not the simulation should playback the best colony of each generation to the user
     */
    private boolean playback;

    /**
     * whether or not to loop playback
     */
    private boolean playbackLoop;

    /**
     * Default simulation constructor.
     * initializes all values and runs the simulation.
     */
    public Simulation(Frame frame)
    {
        //setup initial values
        this.frame = frame;
        generation = 1;
        generationsRemaining = 20;
        ticksPerSecond = 5;
        ticksPerGeneration = 120 * (int) ticksPerSecond;
        ticksRemainingInCurrentGeneration = ticksPerGeneration;
        numColonies = 100;
        stashRadius = 2;
        workerRadius = 3;

        paused = false;
        playback = true;
        playbackLoop = false;

        //generate colonies
        generateColonies(numColonies, stashRadius, workerRadius);
    }

    /**
     * generates all the colonies.
     */
    private void generateColonies(int numColonies, int stashRadius, int workerRadius)
    {
        //set up the blank list to store the colonies in
        colonies = new ArrayList<>();

        //generate numColonies colonies and store them in the list
        for (int lcv = 0; lcv < numColonies; lcv++)
        {
            colonies.add(new Colony(stashRadius, workerRadius));
        }
    }

    /**
     * starts running the simulation
     */
    public void run()
    {
        while (generationsRemaining > 0)
        {
            //simulates the colonies
            simulateColonies();
            System.out.println("Simulated Generation "+ generation);

            //update the generations remaining
            generationsRemaining--;

            //removes the bottom 50% of colonies (by fitness) and sorts the remaining colonies by fitness (best to worst)
            getBestColonies();
            System.out.println("Got the best colonies");

            //displays/simulates the best colony of the generation to the screen
            showBestColony();
            System.out.println("showed the best colony (" + colonies.get(0).getFitness() + " eggs)");

            //repopulates the bottom 50% of colonies from the remaining best colonies
            repopulateColonies();
            System.out.println("repopulated colonies\n");

            //update the generation count
            generation++;

            frame.repaint();
        }
    }

    /**
     * simulates all the colonies by one tick
     */
    public void simulateColonies()
    {
        //the number of queens that are dead
        int numDeadQueens = 0;
        ticksRemainingInCurrentGeneration = ticksPerGeneration;

        //loops until tick limit is hit or until all queens are dead
        while (ticksRemainingInCurrentGeneration > 0 && numDeadQueens < colonies.size())
        {
            //reset number of dead queens
            numDeadQueens = 0;

            //loop through all colonies
            for (Colony colony : colonies)
            {
                //if the colony has a living queen
                if (colony.getQueen().getHealth() > 0)
                {
                    //update the colony
                    colony.update();
//                    System.out.println("Updated a colony: " + colonies.indexOf(colony));
                }

                //if the queen is dead
                else
                {
                    //increment the number of dead queens
                    numDeadQueens++;
                }
            }

            //decrease the number of ticks remaining by one
            ticksRemainingInCurrentGeneration--;
        }
    }

    /**
     * removes the bottom 50% of colonies (by fitness) and sorts the remaining colonies by fitness (best to worst)
     */
    public void getBestColonies()
    {
        //removes the bottom 50%
        for (int lcv = 0; lcv < numColonies / 2; lcv++)
        {
            //get the colony with the lowest fitness
            int minFitness = colonies.get(0).getFitness();
            int mindex = 0; //like min index? get it?

            for (int checkIndex = 1; checkIndex < colonies.size(); checkIndex++)
            {
                int checkFitness = colonies.get(checkIndex).getFitness();
                if (checkFitness < minFitness)
                {
                    mindex = checkIndex;
                }
            }

            //remove the worst colony from the list
            colonies.remove(mindex);
        }

        //sort remaining colonies by fitness, best to worst
        Collections.sort(colonies);
    }

    /**
     * displays the best colony to the screen
     */
    public void showBestColony()
    {
        //if playback is on or if there are no more generations remaining
        if (playback || generationsRemaining == 0)
        {
            //get the best colony
            Colony best = colonies.get(0);

            //mark the colony for rendering
            best.setRender(true);

            //playback at least once
            do
            {
                //reset the best colony
                best.reset();

                //reset the ticks remaining
                ticksRemainingInCurrentGeneration = ticksPerGeneration;

                //set up slow ticking
                long lastTime = System.nanoTime();
                double ns = 1000000000 / ticksPerSecond;    //Amount of time between ticks
                double delta = 0;                           //the number of ticks due for completion (this is a double!!! 🤯🤯🤯🤯)


                /*
                 * simulates the colony at a speed determined by ticksPerSecond
                 * simulates at ticksPerSecond ticks per second until:
                 *  - playback is turned off or
                 *  - until the tick limit is hit or
                 *  - until the queen dies
                 */
                while ((playback || generationsRemaining == 0) && ticksRemainingInCurrentGeneration > 0 && best.getQueen().getHealth() > 0)
                {
                    //the time since the last loop
                    long now = System.nanoTime();
                    delta += (now - lastTime) / ns;
                    lastTime = now;

                    //completes that many ticks
                    while (delta >= 1)
                    {
                        //does a tick only if the tick limit has not been hit and the playback is not paused
                        //the tick limit check is here as well as above because theoretically delta could add more ticks than should be done
                        if (ticksRemainingInCurrentGeneration > 0 && !paused)
                        {
                            //updates the colony
                            best.update();

                            //renders any changes
                            frame.repaint();

                            //update number of ticks remaining
                            ticksRemainingInCurrentGeneration--;
                        }

                        delta--;
                    }
                }
            }
            //loop until loop is disabled and there are more generations to simulate
            while (playbackLoop || generationsRemaining == 0);

            //unmark the colony for rendering
            best.setRender(false);
        }
    }

    /**
     * repopulates the bottom 50% of colonies from the remaining colonies
     */
    public void repopulateColonies()
    {
        //where the repopulated colonies will temporarily be stored
        ArrayList<Colony> repopulated = new ArrayList<>();

        //populate each colony with one other colony
        for (int parent1index = 0; parent1index < colonies.size(); parent1index++)
        {
            //pick a random other colony as a parent
            int parent2index = (int) (Math.random() * (colonies.size() - 1));
            if (parent2index >= parent1index)
            {
                parent2index++;
            }

            Colony parent1 = colonies.get(parent1index);
            Colony parent2 = colonies.get(parent2index);
            repopulated.add(new Colony(parent1, parent2, stashRadius, workerRadius));
        }

        //combine the temporary list onto the main one
        colonies.addAll(repopulated);

        //reset all colonies
        for (Colony colony : colonies)
        {
            colony.reset();
        }
    }

    //renders best colony, if it is currently playing back.
    public void render(Graphics g)
    {
        //TODO: render something under the map (something like "currently simulating")
        colonies.get(0).render(g);
    }

    /**
     * toggles whether or not the playback is paused
     */
    public void togglePause()
    {
        paused = !paused;
        System.out.println("pause toggled to " + paused);
    }

    /**
     * toggles whether or not the best colony from each generation is played back to the user
     */
    public void togglePlayback()
    {
        playback = !playback;
        System.out.println("playback toggled to " + playback);
    }

    /**
     * toggles whether or not the best colony from each generation is played back to the user on loop
     */
    public void togglePlaybackLoop()
    {
        playbackLoop = !playbackLoop;
        System.out.println("playback toggled to " + playbackLoop);
    }
}
