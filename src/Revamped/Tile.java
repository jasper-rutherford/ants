package Revamped;

import java.awt.*;
import java.util.ArrayList;

/**
 * Tile class
 * Represents one hex tile on the board
 */
public class Tile
{
    /**
     * How wide a square should be when rendered
     */
    private final int squareWidth = 20;

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
    private double foodPheromones;

    /**
     * How many colony pheromones are on this tile
     * Theoretically these lead back to the stash
     */
    private double colonyPheromones;

    /**
     * A list of all tiles who are adjacent to this tile
     */
    private ArrayList<Tile> adjacentTiles;

    private ArrayList<Edge> edges;

    /**
     * Default constructor
     * Defaults to basically zero values for everything
     */
    public Tile()
    {
        edges = new ArrayList<>();
        ants = new ArrayList<>();
        isStash = false;
        food = 0;
        foodPheromones = 1;
        colonyPheromones = 1;
        adjacentTiles = new ArrayList<>();
    }

    /**
     * Adds the supplied ant to this tile's list of ants
     *
     * @param ant an ant to put onto this tile
     */
    public void addAnt(Ant ant)
    {
        ants.add(ant);
    }

    /**
     * Removes the supplied ant from this tile's list of ants
     *
     * @param ant an ant to remove from this tile
     */
    public void removeAnt(Ant ant)
    {
        ants.remove(ant);
    }

    /**
     * Gets whether or not this tile is in the stash
     *
     * @return true if this tile is in the stash, false otherwise
     */
    public boolean isStash()
    {
        return isStash;
    }

    /**
     * Sets whether or not this tile is in the stash
     *
     * @param isStash whether or not this tile is to be in the stash
     */
    public void setIsStash(boolean isStash)
    {
        this.isStash = isStash;
    }

    /**
     * Gets the amount of food on this tile
     *
     * @return a positive integer representing how much food is on this tile
     */
    public int getFood()
    {
        return food;
    }

    /**
     * Sets the amount of food on this tile
     *
     * @param food the amount of food to be on this tile
     */
    public void setFood(int food)
    {
        this.food = food;
    }

    /**
     * Change the amount of food on this tile by the supplied amount
     *
     * @param change the amount of food to add to this tile
     */
    public void changeFood(int change)
    {
        food += change;
    }

    /**
     * Gets how many food pheromones are on this tile
     *
     * @return an integer representing how many food pheromones are on this tile
     */
    public double getFoodPheromones()
    {
        return foodPheromones;
    }

    /**
     * Sets the amount of food pheromones on this tile
     *
     * @param foodPheromones the amount of food pheromones to be on this tile
     */
    public void setFoodPheromones(double foodPheromones)
    {
        this.foodPheromones = foodPheromones;
    }

    /**
     * Change how many food pheromones are on this tile by the supplied amount
     * Will automatically set negative values to zero (ex: there are 5 pheromones on this tile and you add -6, leaving -1. The -1 is then set to zero.)
     *
     * @param change the amount of food pheromones to add to this tile
     */
    public void changeFoodPheromones(double change)
    {
        foodPheromones += change;
        if (foodPheromones < 0)
        {
            foodPheromones = 0;
        }
    }

    /**
     * Multiplies the amount of food pheromones on this tile by the provided amount
     * Pheromone amounts less than 1 are rounded up to 1
     *
     * @param change how much to multiply the pheromones on this tile by
     */
    public void multiplyFoodPheromones(double change)
    {
        foodPheromones *= change;

        if (foodPheromones < 1)
        {
            foodPheromones = 1;
        }
    }

    /**
     * Gets how many colony pheromones are on this tile
     *
     * @return an integer representing how many colony pheromones are on this tile
     */
    public double getColonyPheromones()
    {
        return colonyPheromones;
    }

    /**
     * Sets the amount of colony pheromones on this tile
     *
     * @param colonyPheromones the amount of colony pheromones to be on this tile
     */
    public void setColonyPheromones(double colonyPheromones)
    {
        this.colonyPheromones = colonyPheromones;
    }

    /**
     * Change how many colony pheromones are on this tile by the supplied amount
     * Will automatically set negative values to zero (ex: there are 5 pheromones on this tile and you add -6, leaving -1. The -1 is then set to zero.)
     *
     * @param change the amount of colony pheromones to add to this tile
     */
    public void changeColonyPheromones(double change)
    {
        colonyPheromones += change;
        if (colonyPheromones < 0)
        {
            colonyPheromones = 0;
        }
    }

    /**
     * Multiplies the amount of colony pheromones on this tile by the provided amount
     * Pheromone amounts less than 1 are rounded up to one
     *
     * @param change how much to multiply the pheromones on this tile by
     */
    public void multiplyColonyPheromones(double change)
    {
        colonyPheromones *= change;

        if (colonyPheromones < 1)
        {
            colonyPheromones = 1;
        }
    }

    /**
     * Gets the list of tiles who are adjacent to this tile
     *
     * @return an ArrayList of tiles who are adjacent to this tile
     */
    public ArrayList<Tile> getAdjacentTiles()
    {
        return adjacentTiles;
    }

    /**
     * Set the list of tiles who are adjacent to this tile
     *
     * @param adjacentTiles an ArrayList of tiles who are adjacent to this tile
     */
    public void setAdjacentTiles(ArrayList<Tile> adjacentTiles)
    {
        this.adjacentTiles = adjacentTiles;
    }

    /**
     * Checks if the supplied tile is adjacent to this tile
     *
     * @param tile a tile to check for adjacency
     * @return true if the supplied tile is adjacent to this tile, false otherwise
     */
    public boolean isAdjacent(Tile tile)
    {
        return adjacentTiles.contains(tile);
    }

    /**
     * Gets the length of the shortest path between the two tiles
     * Ex: Tile A is adjacent to Tile B. A.distanceTo(B) = 1
     *
     * @param tile a tile to find the distance to
     * @return an int representing the number of times something would have to move to get from this to tile
     */
    public int distanceTo(Tile tile)
    {
        return 0; //TODO: was this a thing
    }

    public void render(Graphics g, int x, int y, int maxFood, Ant first, int overlay)
    {
        //calculate x position
        int xpos = 100 + ((y + 1) % 2 * (squareWidth / 2)) + x * squareWidth;
        int ypos = 100 + y * squareWidth;
        //fill the square's background
        if (isStash)
        {
            //blue if stash
            g.setColor(new Color(174, 221, 255));
        }
        else
        {
            //tan otherwise
            g.setColor(new Color(241, 212, 159));
        }
        g.fillRect(xpos, ypos, squareWidth, squareWidth);

        //render food onto this tile
        //sets a color which is darker if more food, lighter with less
        g.setColor(new Color(2, 139, 0, (int) (1.0 * food / maxFood * 255)));
        g.fillRect(xpos, ypos, squareWidth, squareWidth);

        //fill the square's outline
        g.setColor(new Color(0, 0, 0));
        g.drawRect(xpos, ypos, squareWidth, squareWidth);

        //render ants overlay
        if (overlay == 1 && ants.size() > 0)
        {
            if (ants.contains(first))
            {
                g.setColor(new Color(208, 82, 255));
            }
            else if (ants.get(0).foodPheromonesActive)
            {
                g.setColor(new Color(117, 255, 114));
            }
            g.drawString(ants.size() + "", xpos + squareWidth * 2 / 5, ypos + squareWidth * 3 / 4);
        }
        //render food overlay
        else if (overlay == 2)
        {
            //count all the food on this tile (including what the ants are holding)
            int food = this.food;
            for (Ant ant : ants)
            {
                food += ant.getHeldFood();
            }

            //render food count if greater than zero
            if (food > 0)
            {
                g.drawString(food + "", xpos + squareWidth * 1 / 5, ypos + squareWidth * 3 / 4);
            }
        }
        //render food pheromone overlay
        else if (overlay == 3 && foodPheromones > 1)
        {
            g.drawString(foodPheromones - 1 + "", xpos + squareWidth * 1 / 5, ypos + squareWidth * 3 / 4);
        }
        //render colony pheromone overlay
        else if (overlay == 4 && colonyPheromones > 1)
        {
            g.drawString(colonyPheromones - 1 + "", xpos + squareWidth * 1 / 5, ypos + squareWidth * 3 / 4);
        }
    }

    public void addEdge(Edge edge)
    {
        edges.add(edge);
    }

    public ArrayList<Edge> getEdges()
    {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges)
    {
        this.edges = edges;
    }
}
