package Simulation.Ants;

import Util.Vec2;
import Simulation.MapStuff.Tile;

import java.awt.*;

public class Ant
{
    /**
     * the maximum health an ant can have
     */
    protected int maxHealth = 100;

    /**
     * the health the ant currently has
     */
    protected int health;

    /**
     * the maximum value that the ant's hunger bar can have
     */
    protected int maxHungerBar = 20;

    /**
     * the current value of the ant's hunger bar
     */
    protected int hungerBar;

    /**
     * the tile that this ant starts on
     */
    protected Tile startTile;

    /**
     * the pixel coordinates of the center of this ant
     */
    protected Tile tile;

    /**
     * this ant's color
     */
    protected Color color;

    protected final int foodWorth = 10;

    /**
     * Default constructor for the ant class.
     * initializes health/hunger to max, and sets tile to null
     * automatically adds itself to the provided startTile
     */
    public Ant(Tile startTile)
    {
        health = maxHealth;
        hungerBar = maxHungerBar;

        this.startTile = startTile;
        setTile(startTile);

        if (startTile != null)
        {
            startTile.setAnt(this);
        }

        int restrict = 64;
        int r = (int) (Math.random() * (255 - restrict)) + restrict;
        int g = (int) (Math.random() * (255 - restrict)) + restrict;
        int b = (int) (Math.random() * (255 - restrict)) + restrict;

        color = new Color(r, g, b);
    }

    /**
     * reverts the ant back to its initial values
     */
    public void reset()
    {
        health = maxHealth;
        hungerBar = maxHungerBar;
        setTile(startTile);
    }

    /**
     * adds the supplied value to the hungerBar
     * if hungerBar goes negative, it is set to 0 and the ant takes 1 damage
     * if the hungerBar goes above 100, it is set to 100
     *
     * @param change the amount to change the hungerBar by
     */
    public void changeHungerBar(int change)
    {
        //add the amount to the hungerBar
        hungerBar += change;

        //if hungerBar goes negative, it is set to 0 and the ant takes 1 damage
        if (hungerBar < 0)
        {
            hungerBar = 0;
            health--;
        }

        //if the hungerBar goes above 100, it is set to 100
        else if (hungerBar > 100)
        {
            System.out.println("gluttony :*(");
            hungerBar = 100;
        }
    }

    public void render(Graphics g)
    {
        //only render the living
        if (health > 0)
        {
            //get the tile's center coordinates
            Vec2 center = tile.getCenter();

            //draw an x at the ant's position ¯\_(ツ)_/¯
            g.setColor(color);
            g.drawLine((int) center.x - 4, (int) center.y - 4, (int) center.x + 3, (int) center.y + 3);
            g.drawLine((int) center.x + 3, (int) center.y - 4, (int) center.x - 4, (int) center.y + 3);
        }
    }

    public int getHealth()
    {
        return health;
    }

    /**
     * updates the ant
     * expected to be overridden
     */
    public void update()
    {
    }

    /**
     * gets this ant's tile
     *
     * @return the tile that this ant is on
     */
    public Tile getTile()
    {
        return tile;
    }

    /**
     * sets this ant's starting tile
     *
     * @param tile the new tile for this ant to start on
     */
    public void setStartTile(Tile tile)
    {
        this.startTile = tile;
    }

    /**
     * sets this ant's tile
     *
     * @param tile the new tile for this ant to be on
     */
    public void setTile(Tile tile)
    {
        this.tile = tile;
    }
}
