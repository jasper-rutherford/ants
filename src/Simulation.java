import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Simulation
{
    private Map map;
    private ArrayList<Colony> colonies;
    private ArrayList<Ant> ants;

    private int generation;
    private int ticksPerGeneration;

    //creates a blank simulation, waiting to be set up with setup()
    public Simulation()
    {
        this.generation = 0;

        map = null;
        colonies = null;
        ants = null;
    }

    public void setup(int numColonies, int numAnts)
    {
        map = new Map(numColonies);

        //generate colonies
        generateColonies(numColonies);

        //generates the initial batch of ants
        generateAnts(numAnts);

        //assigns the ants to random colonies
        reassignAnts();

        //updates the generation counter
        generation++;
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

            //assign the ant to the colony
            colonies.get(colonyID).addAnt(ant);

            //reset the ant relative to its new colony
            ant.reset(colonyID);
        }
    }

    //updates all ants
    public void tick()
    {
        if (ants != null)
        {
            for (Ant ant : ants)
            {
                ant.update();
            }
        }
    }

    //renders the map and the ants
    public void render(Graphics g)
    {
        //render map if it exists
        if (map != null)
        {
            //render map
            map.render();
        }

        //render ants only if ants exists
        if (ants != null)
        {
            //render ants
            for (Ant ant : ants)
            {
                ant.render();
            }
        }
    }
}
