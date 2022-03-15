package Simulation;

import Simulation.MapStuff.Map;
import Simulation.Ants.*;
import Simulation.MapStuff.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Colony implements Comparable<Colony>
{
    /**
     * the map associated with this colony
     */
    private Map map;

    /**
     * all the ants associated with this colony
     */
    private ArrayList<Ant> ants;

    /**
     * the queen associated with this colony
     */
    private Queen queen;

    /**
     * whether or not this colony should be rendered
     */
    private boolean render;

    /**
     * generates a colony with the given radii
     *
     * @param stashRadius  the radius of the queen's stash
     * @param workerRadius the radius of the workers' spawn area around the queen
     */
    public Colony(int stashRadius, int workerRadius)
    {
        //generate a new map
        map = new Map();

        //setup the ants list
        ants = new ArrayList<>();

        //spawn a queen randomly on the map
        spawnQueen(stashRadius, workerRadius);

        //generates and spawns fresh workers on the map around the queen
        generateAndSpawnNewWorkers(workerRadius);
    }

//    /**
//     * Creates a colony from two parents
//     *
//     * @param parent1      the first parent
//     * @param parent2      the second parent
//     * @param stashRadius  the radius of the queen's stash
//     * @param workerRadius the radius of the workers' spawn area around the queen
//     */
//    public Colony(Colony parent1, Colony parent2, int stashRadius, int workerRadius)
//    {
//        //generate a new map
//        map = new Map();
//
//        //setup the ants list
//        ants = new ArrayList<>();
//
//        //spawn a queen randomly on the map
//        spawnQueen(stashRadius, workerRadius);
//
//        //generate workers for this colony from the parents' workers and spawn them around the queen
//        generateAndSpawnChildWorkers(parent1, parent2, workerRadius);
//    }

    /**
     * creates a colony and assigns it the supplied workers
     *
     * @param workers the workers for the colony to start with
     */
    public Colony(ArrayList<Worker> workers)
    {
        //generate a new map
        map = new Map();

        //setup the ants list
        ants = new ArrayList<>();

        //spawn a queen randomly on the map
        spawnQueen(Simulation.stashRadius, Simulation.workerRadius);

        //assign the workers tiles and add them to the list
        assignWorkers(workers);
    }

    /**
     * assign the workers tiles and add them to the list
     *
     * @param workers the workers to get assigned all up and over the place
     */
    public void assignWorkers(ArrayList<Worker> workers)
    {
        //the list of all the tiles where a worker should spawn
        ArrayList<Tile> spawnPoints = new ArrayList<>();

        //add all the tiles within workerRadius of the queen to the spawnPoints list
        Tile.addTiles(queen.getTile(), Simulation.workerRadius, spawnPoints);

        //don't spawn a worker on the queen
        spawnPoints.remove(queen.getTile());

        //combine each worker with a tile and add it to the list of ants
        for (Worker worker : workers)
        {
            //get a tile to spawn on
            Tile spawnTile = spawnPoints.get(0);
            spawnPoints.remove(0);

            //set the worker to the tile appropriately
            worker.setStartTile(spawnTile);
            worker.setTile(spawnTile);
            spawnTile.setAnt(worker);

            //adds the new worker to this colony's list
            ants.add(worker);
        }
    }

//    /**
//     * generates ants from the supplied parent colonies and spawns them around the queen
//     *
//     * @param parent1      the first parent colony
//     * @param parent2      the second parent colony
//     * @param workerRadius the radius of the worker' spawn area around the queen
//     */
//    public void generateAndSpawnChildWorkers(Colony parent1, Colony parent2, int workerRadius)
//    {
//        //the list of all the tiles where a worker should spawn
//        ArrayList<Tile> spawnPoints = new ArrayList<>();
//
//        //add all the tiles within workerRadius of the queen to the spawnPoints list
//        Tile.addTiles(queen.getTile(), workerRadius, spawnPoints);
//
//        //don't spawn a worker on the queen
//        spawnPoints.remove(queen.getTile());
//
//        //get the parents' ant lists
//        ArrayList<Ant> ants1 = parent1.ants;
//        ArrayList<Ant> ants2 = parent2.ants;
//
//        //ok so this little chunk gets an arraylist with all the indexes in ants2, and then randomizes that list
//        //this way each ant in the first list can be paired with a random ant in the second list.
//        //the queen's index is not in this list
//        ArrayList<Integer> ants2Indices = new ArrayList<>();
//        {
//            for (int lcv = 0; lcv < ants2.size(); lcv++)
//            {
//                ants2Indices.add(lcv);
//            }
//            ants2Indices.remove(0);
//            Collections.shuffle(ants2Indices);
//        }
//
//        //populates each ant in ants1 with a random ant in ants2
//        for (int p1Index = 1; p1Index < ants1.size(); p1Index++)    //skips p1Index=0 because thats the queen
//        {
//            //gets the index for an ant in ants2 from the list of indexes.
//            int p2Index = ants2Indices.get(p1Index - 1); //its p1Index - 1 because the loop ranges from [1, ants.size() - 1] but the ants2Indices only has (ants.size() - 1) elements
//
//            //gets the parent workers
//            Worker worker1 = (Worker) ants1.get(p1Index);
//            Worker worker2 = (Worker) ants2.get(p2Index);
//
//            //spawns a new worker from those two parents
//            Worker child = new Worker(spawnPoints.get(0), worker1, worker2, 75); //TODO: fix age
//
//            //ensures that each worker spawns on a different tile
//            spawnPoints.remove(0);
//
//            //adds the new worker to this colony's list
//            ants.add(child);
//        }
//    }

    /**
     * gets the colony's queen
     *
     * @return the queen that belongs to this colony
     */
    public Queen getQueen()
    {
        return queen;
    }

    /**
     * updates the colony by one tick
     */
    public void update()
    {
        //update the map
        map.update();

        //loop through all ants
        for (Ant ant : ants)
        {
            //update each ant
            ant.update();
        }
    }

    /**
     * spawns the queen on a random position on the map
     * (sufficiently far away from the map's edges so that all radius requirements are satisfied)
     *
     * @param stashRadius  the radius of the queen's stash
     * @param workerRadius the radius of where workers will need to be spawned
     */
    public void spawnQueen(int stashRadius, int workerRadius)
    {
        //find a random tile on the map for the queen to spawn
        //the tile must be far enough from the edge that all tiles within the stashRadius and the workerRadius are on the map

        //pick which radius is bigger
        int radius = Math.max(stashRadius, workerRadius);

        //find random tile indices that fit the requirements
        int xPos = (int) (Math.random() * (map.getMapWidth() - (2 * radius))) + radius;
        int yPos = (int) (Math.random() * (map.getMapWidth() - (2 * radius))) + radius;

        //the tile at the randomly chosen position
        Tile queenTile = map.getTiles()[xPos][yPos];

        //generate a queen
        queen = new Queen(queenTile, stashRadius);

        //add the queen to the ants arrayList
        ants.add(queen);
    }

    /**
     * spawns a worker on every tile within workerRadius of the queen
     *
     * @param workerRadius dictates which tiles should spawn a worker. A workerRadius of 1 will spawn 8 ants, etc
     */
    public void generateAndSpawnNewWorkers(int workerRadius)
    {
        //the list of all the tiles where a worker should spawn
        ArrayList<Tile> spawnPoints = new ArrayList<>();

        //add all the tiles within workerRadius of the queen to the spawnPoints list
        Tile.addTiles(queen.getTile(), workerRadius, spawnPoints);

        //don't spawn a worker on the queen
        spawnPoints.remove(queen.getTile());

        //spawn a worker on every spawnPoint
        for (Tile spawnPoint : spawnPoints)
        {
            //create a new worker
            Worker newWorker = new Worker(spawnPoint);

            //add the worker to the list of ants
            ants.add(newWorker);
        }
    }

    /**
     * Returns the fitness of the colony (aka the number of eggs that the queen produced)
     */
    public int getFitness()
    {
        return queen.getEggs();
    }

    /**
     * renders this colony
     *
     * @param g the graphics object used to draw things
     */
    public void render(Graphics g)
    {
        //only renders if it is supposed to
        if (render)
        {
            //render the map
            map.render(g);

            //render all the ants
            for (Ant ant : ants)
            {
                ant.render(g);
            }
        }
    }

    /**
     * marks this colony for rendering/for not rendering
     *
     * @param val whether or not this colony should be rendered
     */
    public void setRender(boolean val)
    {
        render = val;
    }

    /**
     * reverts the colony back to its initial values
     */
    public void reset()
    {
        //reset the map
        map.reset();

        //reset the ants
        for (Ant ant : ants)
        {
            ant.reset();
        }
    }

    /**
     * compares two colonies by their fitness
     * this might be reversed from standard.
     *
     * @param colony the colony being compared with this one
     * @return 1 if the provided colony has a larger fitness, -1 if smaller, 0 otherwise
     */
    @Override
    public int compareTo(Colony colony)
    {
        if (getFitness() > colony.getFitness())
        {
            return -1;
        }
        if (getFitness() < colony.getFitness())
        {
            return 1;
        }
        else return 0;
    }

    /**
     * gets this colony's ants
     *
     * @return an ArrayList of Ants
     */
    public ArrayList<Ant> getAnts()
    {
        return ants;
    }
}
