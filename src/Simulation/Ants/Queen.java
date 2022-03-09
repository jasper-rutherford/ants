package Simulation.Ants;

import java.awt.*;
import java.util.ArrayList;

import MapStuff.Tile;

public class Queen extends Ant
{
    /**
     * the number of eggs that this queen has produced. (used to calculate fitness)
     */
    private int eggs;

    /**
     * the radius of the stash (but like, the radius of the square. a radius of 2 means 25 tiles)
     */
    private int stashRadius;

    /**
     * all the tiles within the range of the queen
     */
    private ArrayList<Tile> stash;

    public Queen(Tile startTile, int stashRadius)
    {
        super(startTile);

        eggs = 0;
        this.stashRadius = stashRadius;
        setTile(startTile);

        //queen is a little beefier than other ants
        maxHungerBar = 100;
        hungerBar = maxHungerBar;
        maxHealth = 100;
        health = maxHealth;
    }

    /**
     * reverts the queen back to its starting values
     */
    @Override
    public void reset()
    {
        super.reset();
        eggs = 0;
    }

    /**
     * gets this queen's tile
     *
     * @return the tile that the queen currently resides on
     */
    @Override
    public Tile getTile()
    {
        return tile;
    }

    /**
     * gets how many eggs this queen has laid
     *
     * @return a number representing how many eggs this queen has laid
     */
    public int getEggs()
    {
        return eggs;
    }

    /**
     * updates the queen by one tick
     */
    public void update()
    {
        //if full of food, lay an egg
        if (hungerBar == maxHungerBar)
        {
            eggs++;
            hungerBar /= 2;
        }

        //otherwise, look for food to eat
        else
        {
            eat();
        }

        //lay down pheromones to stash
        for (Tile stashTile : stash)
        {
            stashTile.addPheromone();
        }
    }

    /**
     * looks for food in the stash to eat.
     * if there is food, then the queen eats 1 food.
     * if there is not food, then the queen's hungerBar decreases by 1
     */
    public void eat()
    {
        //setup the check for if the ant finds any food (defaults to false)
        boolean ate = false;

        //loop through stash
        for (Tile stashTile : stash)
        {
            //check tile in stash for food
            if (stashTile.getFood() > 0)
            {
                //decrease the tile's food by 1
                stashTile.setFood(stashTile.getFood() - 1);

                //increase this ant's hungerBar by 10
                changeHungerBar(10);

                //mark the check as true
                ate = true;

                //stop looping
                break;
            }
        }

        //if the queen didn't find any food
        if (!ate)
        {
            //decrease hungerBar by 1
            changeHungerBar(-1);
        }
    }

    /**
     * sets this queen's central tile
     *
     * @param tile the tile to be the new center
     */
    @Override
    public void setTile(Tile tile)
    {
        //set this queen's tile to be the supplied tile
        this.tile = tile;

        //create a new stash
        stash = new ArrayList<>();

        //add all the tiles within a radius of two (from the supplied tile) to the stash
        Tile.addTiles(tile, stashRadius, stash);
    }

    /**
     * renders the queen on the map
     *
     * @param g the graphics object used for rendering
     */
    @Override
    public void render(Graphics g)
    {
        int x = (int) tile.getCenter().x - 4;
        int y = (int) tile.getCenter().y - 4;

        g.setColor(new Color(0, 0, 0));
        g.drawOval(x, y, 7, 7);
        g.drawLine(x + 5, y + 5, x + 7, y + 7);
    }
}
