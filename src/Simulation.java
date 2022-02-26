import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Simulation
{
    private Map map;
    private ArrayList<Colony> colonies;
    private ArrayList<Ant> ants;

    private int generation; //current generation
    private int generationsRemaining;

    private int ticksPerGeneration;
    private int ticksRemainingInCurrentGeneration;
    private int ticksPerSecond;

    private boolean paused;

    //creates a blank simulation, waiting to be set up with setup()
    public Simulation()
    {
        this.generation = 0;

        map = new Map();
        colonies = new ArrayList<>();
        ants = new ArrayList<>();

        paused = true;
    }

    public void setup(int numColonies, int numAnts, int generationsRemaining, int ticksPerGeneration, int ticksPerSecond)
    {
        this.generationsRemaining = generationsRemaining;
        this.ticksPerGeneration = ticksPerGeneration;
        this.ticksRemainingInCurrentGeneration = ticksPerGeneration;
        this.ticksPerSecond = ticksPerSecond;

        //sets up the map (for now just fixes pheromones?)
// TODO:       setupMap(); (create this method)

        //generate colonies
        generateColonies(numColonies);

        //generates the initial batch of ants
        generateAnts(numAnts);

        //assigns the ants to random colonies
        reassignAnts();

        //updates the generation counter
        generation++;
        this.generationsRemaining--;
    }

    public ArrayList<Ant> getAnts()
    {
        return ants;
    }

    public int getTicksPerSecond()
    {
        return ticksPerSecond;
    }

    /**
     * generates all the colonies. Does not generate any ants.
     * Used purely to set up where the colonies will go/what tiles go with what colony.
     */
    private void generateColonies(int numColonies)
    {
        //set up the blank list to store the colonies in
        colonies = new ArrayList<>();

        //TODO: replace this with some method that spawns the colonies randomly or something
        //corners of all the stashes in a 13x13 grid
        Vec2[] corners = {
                new Vec2(8, 11),
                new Vec2(11, 8),
                new Vec2(11, 4),
                new Vec2(8, 1),
                new Vec2(4, 1),
                new Vec2(1, 4),
                new Vec2(1, 8),
                new Vec2(4, 11)};

        int stashWidth = map.getMapWidth() / 13;

        //generate numColonies colonies and store them in the list
        for (int lcv = 0; lcv < numColonies; lcv++)
        {
            //TODO: set colony constructor to take a list of tiles. the stash should be generated here, not in the constructor.
            colonies.add(new Colony(lcv, corners[lcv].times(stashWidth), stashWidth, map.getTiles()));
        }
    }

    /**
     * generates the initial batch of ants.
     */
    private void generateAnts(int numAnts)
    {
        //creates a blank arraylist for ants to be stored in
        ants = new ArrayList<>();

        //generates numAnts ants with random stats and adds them to the list
        for (int lcv = 0; lcv < numAnts; lcv++)
        {
            ants.add(new Ant());
        }
    }

    //advances to the next generation of ants
    public void nextGeneration()
    {
        //cull the least fit half of ants then repopulate from survivors
        reproduceAnts();

        //reassign all ants to random colonies (and set their positions around those new colonies)
        reassignAnts();

        //increase the generation counter by one
        generation++;
        generationsRemaining--;
    }

    //cull and repopulate according to fitness.
    private void reproduceAnts()
    {
        //cull colonies until half remain
        ArrayList<Colony> culled = new ArrayList<Colony>();
        int numColonies = colonies.size();
        while (colonies.size() > numColonies / 2)
        {
            //find the worst colony (the lowest fitness)
            Colony worstColony = colonies.get(0);
            int lowestFitness = worstColony.getFitness();
            for (Colony colony : colonies)
            {
                //ignore colonies that have already been culled
                if (!colony.getCulled() && colony.getFitness() < lowestFitness)
                {
                    lowestFitness = colony.getFitness();
                    worstColony = colony;
                }
            }

            //cull the worst colony (kill all the ants and take the colony out of consideration)
            worstColony.cull(ants);
        }

        //mark all colonies as not culled
        for (Colony colony : colonies)
        {
            colony.deCull();
        }

        //repopulate global ant population
        ArrayList<Ant> newAnts = new ArrayList<Ant>();
        for (Ant ant : ants)
        {
            //generate a random index that isn't the index for the current ant
            int randomIndex = (int) (Math.random() * (ants.size() - 1));
            if (randomIndex >= ants.indexOf(ant))
            {
                randomIndex++;
            }

            //generate a new ant
            newAnts.add(new Ant(ant, ants.get(randomIndex)));

            ant.setAge(ant.getAge() + 1);
        }

        //add all new ants to the global population
        ants.addAll(newAnts);

        //shuffle ants when finished
        Collections.shuffle(ants);
    }

    //redistribute ants among colonies
    private void reassignAnts()
    {
        //clear all colony affiliations
        for (Colony colony : colonies)
        {
            colony.clearAnts();
        }

        //assign ants to new colonies and spawn the ant relative to the new colony
        for (int lcv = 0; lcv < ants.size(); lcv++)
        {
            //the current ant being reassigned
            Ant ant = ants.get(lcv);

            //the id of the colony that the ant will be assigned to
            int colonyID = lcv % colonies.size();

            //the colony that the ant will be assigned to
            Colony colony = colonies.get(colonyID);

            //assign the ant to the colony
            colony.addAnt(ant);

            //assign the colony to the ant
            ant.setColony(colonyID);

            //reset the ant
            ant.reset(colony.getCorner(), colony.getStashWidth(), map.getTileWidth());
        }
    }

    //updates all ants
    public void tick()
    {
        //if there are ants (aka if the generation has been setup) and the simulation is not paused
        if (ants != null && !paused)
        {
            //get ready to count the amount of ants that are alive
            int numLivingAnts = 0;

            //for each ant
            for (Ant ant : ants)
            {
                //update the ant
                ant.update();

                //check if the ant is alive
                if (ant.getHealth() > 0)
                {
                    //update the count accordingly
                    numLivingAnts++;
                }
            }

            //decrease the number of ticks remaining in the current generation by one
            ticksRemainingInCurrentGeneration--;

            //if the generation is complete (aka there are no more ticks remaining or all the ants are dead)
            if (ticksRemainingInCurrentGeneration == 0 || numLivingAnts == 0)
            {
                //do all the stuff that needs to be done when a generation is complete
                completeGeneration();
            }
        }
    }

    /**
     * does all the stuff that needs to be done when a generation finishes
     */
    public void completeGeneration()
    {
        //if this is the last generation
        if (generationsRemaining == 0)
        {
            //pause the simulation
            paused = true;
        }

        //if this is not the last generation
        else
        {
            //advance to next generation of ants
            nextGeneration();

            //reset ticks remaining
            ticksRemainingInCurrentGeneration = ticksPerGeneration;
        }
    }

    /**
     * gets the simulation's list of colonies
     *
     * @return an ArrayList of Colonys
     */
    public ArrayList<Colony> getColonies()
    {
        return colonies;
    }

    //renders the map and the ants
    public void render(Graphics g)
    {
        //render map
        map.render(g);

        //render ants only if ants exists
        if (ants != null)
        {
            //render ants
            for (Ant ant : ants)
            {
                ant.render(g);
            }
        }
    }

    public void togglePause()
    {
        if (ants.size() > 0)
        {
            paused = !paused;
            System.out.println("pause toggled to " + paused);
        }
        else
        {
            System.out.println("did not toggle pause, simulation not yet setup");
        }
    }
}
