package Simulation;

import BigPicture.Frame;
import Simulation.Ants.Ant;
import Simulation.Ants.Worker;

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
    public static final int stashRadius = 2;

    /**
     * each queen's worker radius (this dictates how far away from the queen that workers can spawn)
     */
    public static final int workerRadius = 2;

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
            //repaint the screen
            frame.repaint();

            //simulates the colonies
            simulateColonies();
            System.out.println("Finished simulating generation " + generation);

            //update the generations remaining
//            generationsRemaining--;

            //removes the bottom 50% of colonies (by fitness) and sorts the remaining colonies by fitness (best to worst)
            getBestColonies();
            System.out.println("The best colony produced " + colonies.get(0).getFitness() + " eggs");

            //displays/simulates the best colony of the generation to the screen
            showBestColony();
            System.out.println("showed the best colony (" + colonies.get(0).getFitness() + " eggs)");

            //repopulates the bottom 50% of colonies from the remaining best colonies
            repopulateColonies();
            System.out.println("repopulated colonies\n");

            //update the generation coun
            generation++;
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
            if (ticksRemainingInCurrentGeneration % (ticksPerSecond * 10) == 0)
            {
                System.out.println(ticksRemainingInCurrentGeneration + " ticks remaining in generation " + generation);
            }

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
//        //removes the bottom 50%
//        for (int lcv = 0; lcv < numColonies / 2; lcv++)
//        {
//            //get the colony with the lowest fitness
//            int minFitness = colonies.get(0).getFitness();
//            int mindex = 0; //like min index? get it?
//
//            for (int checkIndex = 1; checkIndex < colonies.size(); checkIndex++)
//            {
//                int checkFitness = colonies.get(checkIndex).getFitness();
//                if (checkFitness < minFitness)
//                {
//                    mindex = checkIndex;
//                }
//            }
//
//            //remove the worst colony from the list
//            colonies.remove(mindex);
//        }

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

                int oldQueenHealth = best.getQueen().getHealth();
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

                            if (oldQueenHealth != best.getQueen().getHealth())
                            {
                                oldQueenHealth = best.getQueen().getHealth();
                                System.out.println("The queen has " + best.getQueen().getHealth() + " health");
                            }

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
        //combine all workers into one list (old list)
        ArrayList<Worker> oldWorkers = getAllAnts(colonies);

        //trim down to fittest 50% of workers
        trim(oldWorkers);

        //create another list (new workers) from this old list
        ArrayList<Worker> newWorkers = populateAnts(oldWorkers);

        //combine new workers with old workers
        oldWorkers.addAll(newWorkers);

        //shuffle the cumulative list
        Collections.shuffle(oldWorkers);

        //create new colonies using the workers from this new cumulative list of workers
        colonies = createColoniesFromAnts(oldWorkers);

//        //where the repopulated colonies will temporarily be stored
//        ArrayList<Colony> repopulated = new ArrayList<>();
//
//        //populate each colony with one other colony
//        for (int parent1index = 0; parent1index < colonies.size(); parent1index++)
//        {
//            //pick a random other colony as a parent
//            int parent2index = (int) (Math.random() * (colonies.size() - 1));
//            if (parent2index >= parent1index)
//            {
//                parent2index++;
//            }
//
//            Colony parent1 = colonies.get(parent1index);
//            Colony parent2 = colonies.get(parent2index);
//            repopulated.add(new Colony(parent1, parent2, stashRadius, workerRadius));
//        }
//
//        //combine the temporary list onto the main one
//        colonies.addAll(repopulated);
//
//        //reset all colonies
//        for (Colony colony : colonies)
//        {
//            colony.reset();
//        }
    }

    /**
     * creates a list of all the workers in all the provided colonies
     *
     * @param colonies an ArrayList of Colonies
     * @return an ArrayList of Workers
     */
    public ArrayList<Worker> getAllAnts(ArrayList<Colony> colonies)
    {
        //start with an empty list
        ArrayList<Worker> workers = new ArrayList<>();

        //loop through all provided colonies
        for (Colony colony : colonies)
        {
            //loop through each colony's ants
            for (Ant ant : colony.getAnts())
            {
                //if the ant is a worker
                if (ant instanceof Worker)
                {
                    //add it to the list of workers
                    workers.add((Worker) ant);
                }
            }
        }

        //return the list of workers
        return workers;
    }

    /**
     * trims down the list of workers to the fittest workers
     *
     * @param workers the list to trim
     */
    public void trim(ArrayList<Worker> workers)
    {
        //sort the workers by fitness
        Collections.sort(workers);
        workerStats(workers);

        //delete the back half of the list
        int len = workers.size() / 2;
        for (int lcv = 0; lcv < len; lcv++)
        {
            workers.remove(len);
        }
    }

    /**
     * creates new workers from the supplied workers
     *
     * @param oldWorkers the ants to populate from
     * @return an ArrayList of Workers
     */
    public ArrayList<Worker> populateAnts(ArrayList<Worker> oldWorkers)
    {
        //start with an empty list
        ArrayList<Worker> newWorkers = new ArrayList<>();

        //loop through all supplied workers
        for (Worker worker1 : oldWorkers)
        {
            //get a random index in oldWorkers to repopulate with
            int index = (int) (Math.random() * (oldWorkers.size() - 1));

            //offset the index by one such that a worker will never reproduce with itself
            if (index >= oldWorkers.indexOf(worker1))
            {
                index++;
            }

            //get the worker to reproduce with
            Worker worker2 = oldWorkers.get(index);

            newWorkers.add(new Worker(null, worker1, worker2, 0));
        }

        return newWorkers;
    }

    /**
     * creates half of numColonies from the given list of workers
     *
     * @param newWorkers the workers to create colonies from
     * @return an ArrayList of Colonies
     */
    public ArrayList<Colony> createColoniesFromAnts(ArrayList<Worker> newWorkers)
    {
        //start with empty list of colonies
        ArrayList<Colony> newColonies = new ArrayList<>();

        //calculate how many workers will be assigned to each colony
        int numWorkersPerColony = (int) Math.pow(workerRadius * 2 + 1, 2) - 1;

        //create numColonies colonies
        for (int colNum = 0; colNum < numColonies; colNum++)
        {
            //get the next numWorkersPerColony and add them to a list
            ArrayList<Worker> chunk = new ArrayList<>();

            for (int chunkNum = 0; chunkNum < numWorkersPerColony; chunkNum++)
            {
                //add a worker to the chunk
                chunk.add(newWorkers.get(0));

                //remove that same worker from the big list of workers
                newWorkers.remove(0);
            }

            newColonies.add(new Colony(chunk));
        }

        return newColonies;
    }

    //renders best colony, if it is currently playing back.
    public void render(Graphics g)
    {
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

    public void workerStats(ArrayList<Worker> workers)
    {
        System.out.println("\nBest Ant:\n" + workers.get(0));
        System.out.println("Median Ant:\n" + workers.get(workers.size() / 2));
        System.out.println("Worst Ant:\n" + workers.get(workers.size() - 1));

        double avgFitness = 0;
        for (Worker worker : workers)
        {
            avgFitness += worker.getFitness();
        }

        avgFitness = ((int) (avgFitness / workers.size() * 100)) / 100.0;

        System.out.println("Average Fitness:\t\t" + avgFitness);
    }
}
