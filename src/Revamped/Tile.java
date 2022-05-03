package Revamped;

import java.util.ArrayList;

/**
 * Tile class
 * Represents one hex tile on the board
 */
public class Tile
{
    /**
     * A list of ants who are on this tile
     */
    private ArrayList<Ant> ants;

    /**
     * Whether or not this tile is in the stash
     */
    private boolean isStash;

    /**
     * How much food is on this tile
     */
    private int food;

    /**
     * How many food pheromones are on this tile
     * Theoretically these lead to food
     */
    private int foodPheromones;

    /**
     * How many colony pheromones are on this tile
     * Theoretically these lead back to the stash
     */
    private int colonyPheromones;

    /**
     * A list of all tiles who are adjacent to this tile
     */
    private ArrayList<Tile> adjacentTiles;

    /**
     * Default constructor
     * Defaults to basically zero values for everything
     */
    public Tile()
    {
        ants = new ArrayList<>();
        isStash = false;
        food = generateFood();
        foodPheromones = 0;
        colonyPheromones = 0;
        adjacentTiles = new ArrayList<>();
    }

    /**
     * Adds the supplied ant to this tile's list of ants
     * @param ant an ant to put onto this tile
     */
    public void addAnt(Ant ant)
    {
        ants.add(ant);
    }

    /**
     * Removes the supplied ant from this tile's list of ants
     * @param ant an ant to remove from this tile
     */
    public void removeAnt(Ant ant)
    {
        ants.remove(ant);
    }

    /**
     * Gets whether or not this tile is in the stash
     * @return true if this tile is in the stash, false otherwise
     */
    public boolean isStash()
    {
        return isStash;
    }

    /**
     * Sets whether or not this tile is in the stash
     * @param isStash whether or not this tile is to be in the stash
     */
    public void setIsStash(boolean isStash)
    {
        this.isStash = isStash;
    }

    /**
     * Gets the amount of food on this tile
     * @return a positive integer representing how much food is on this tile
     */
    public int getFood()
    {
        return food;
    }

    /**
     * Sets the amount of food on this tile
     * @param food the amount of food to be on this tile
     */
    public void setFood(int food)
    {
        this.food = food;
    }

    /**
     * Change the amount of food on this tile by the supplied amount
     * @param change the amount of food to add to this tile
     */
    public void changeFood(int change)
    {
        food += change;
    }

    /**
     * Gets how many food pheromones are on this tile
     * @return an integer representing how many food pheromones are on this tile
     */
    public int getFoodPheromones()
    {
        return foodPheromones;
    }

    /**
     * Sets the amount of food pheromones on this tile
     * @param foodPheromones the amount of food pheromones to be on this tile
     */
    public void setFoodPheromones(int foodPheromones)
    {
        this.foodPheromones = foodPheromones;
    }

    /**
     * Change how many food pheromones are on this tile by the supplied amount
     * @param change the amount of food pheromones to add to this tile
     */
    public void changeFoodPheromones(int change)
    {
        foodPheromones += change;
    }

    /**
     * Gets how many colony pheromones are on this tile
     * @return an integer representing how many colony pheromones are on this tile
     */
    public int getColonyPheromones()
    {
        return colonyPheromones;
    }

    /**
     * Sets the amount of colony pheromones on this tile
     * @param colonyPheromones the amount of colony pheromones to be on this tile
     */
    public void setColonyPheromones(int colonyPheromones)
    {
        this.colonyPheromones = colonyPheromones;
    }

    /**
     * Change how many colony pheromones are on this tile by the supplied amount
     * @param change the amount of colony pheromones to add to this tile
     */
    public void changeColonyPheromones(int change)
    {
        colonyPheromones += change;
    }

    /**
     * Gets the list of tiles who are adjacent to this tile
     * @return an ArrayList of tiles who are adjacent to this tile
     */
    public ArrayList<Tile> getAdjacentTiles()
    {
        return adjacentTiles;
    }

    /**
     * Set the list of tiles who are adjacent to this tile
     * @param adjacentTiles an ArrayList of tiles who are adjacent to this tile
     */
    public void setAdjacentTiles(ArrayList<Tile> adjacentTiles)
    {
        this.adjacentTiles = adjacentTiles;
    }

    /**
     * Generates an amount of food to spawn on this tile
     * @return an integer number of food to spawn on this tile
     */
    public int generateFood()
    {
        //TODO: something better here
        return 0;
    }
}
