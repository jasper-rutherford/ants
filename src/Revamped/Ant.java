package Revamped;

/**
 * Ant class
 * Represents one ant
 */
public class Ant
{
    /**
     * The tile that this ant is currently on
     */
    private Tile tile;

    /**
     * How much food this ant is currently holding
     */
    private int heldFood;

    /**
     * Whether or not this ant is dropping food pheromones
     */
    private boolean foodPheromonesActive;

    /**
     * Whether or not this ant is dropping colony pheromones
     */
    private boolean colonyPheromonesActive;

    /**
     * How many food pheromones to drop per drop
     */
    private int foodPheromoneStrength;

    /**
     * How many colony pheromones to drop per drop
     */
    private int colonyPheromoneStrength;

    /**
     * Generates an ant with default settings (aka find food mode) on the specified tile
     * @param tile the tile for this ant to spawn on
     */
    public Ant(Tile tile)
    {
        //add this ant to the tile
        this.tile = tile;
        tile.addAnt(this);

        //default stuff
        heldFood = 0;
        foodPheromonesActive = false;   //food pheromones off
        colonyPheromonesActive = true;  //colony pheromones on
        foodPheromoneStrength = 0;      //will be set when food is found
        colonyPheromoneStrength = 1;    //default is 1
    }
}
