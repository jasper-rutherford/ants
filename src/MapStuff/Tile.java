package MapStuff;

import BigPicture.Vec2;
import Simulation.Ants.Ant;

import java.awt.*;
import java.util.ArrayList;

public class Tile
{
    /**
     * the amount of food this tile started with
     */
    private int startFood;

    /**
     * the amount of food on this tile
     */
    private int food;

    /**
     * the amount of pheromones on this tile
     */
    private int pheromoneCount;

    /**
     * the ant on this tile (null if no ant is on the tile)
     */
    private Ant ant;

    /**
     * the coordinates of the center of this tile
     */
    private Vec2 centerPos;

    /**
     * the tiles adjacent to this tile
     * 0 - above
     * 1 - right
     * 2 - below
     * 3 - left
     */
    private ArrayList<Tile> adjacents;

    /**
     * the maximum amount of food that can spawn on a tile
     */
    private final int maxFood = 10;

    /**
     * this tile's position in the map's tiles array
     */
    private Vec2 tilePos;

    /**
     * Sets up this tile
     *
     * @param tilePos   the coordinates of this tile in the map's tiles[][] array
     * @param tileWidth the width of a tile (in pixels)
     */
    public Tile(Vec2 tilePos, int tileWidth)
    {
        generateFood();
        this.pheromoneCount = 0;
        this.ant = null;
        this.centerPos = new Vec2((tilePos.x + 0.5) * tileWidth + 10, (tilePos.y + 0.5) * tileWidth + 10);
        this.adjacents = null;
        this.tilePos = tilePos;
    }

    /**
     * sets this tile's food according to some cool food function
     * (TODO)
     */
    public void generateFood()
    {
        this.food = (int)(Math.random() * maxFood);
        this.startFood = food;
        //behold: the old function
//        BigPicture.Vec2 mapCenter = new BigPicture.Vec2(650 / 2 + 10, 650 / 2 + 10);
//        double distance = mapCenter.minus(centerPos).length() / 15;
//        if (distance > 100)
//        {
//            distance = 100;
//        }
//        double trueFoodMax = -Math.sqrt(-(distance * distance) + (2 * distance * maxFood)) + maxFood;
//        food = (int) trueFoodMax;
    }

    /**
     * reverts the tile back to its initial values
     */
    public void reset()
    {
        food = startFood;
        pheromoneCount = 0;
        ant = null;
    }

    /**
     * set the tile's food
     *
     * @param food the amount of food to be on this tile
     */
    public void setFood(int food)
    {
        this.food = food;
    }

    /**
     * changes the amount of food on this tile by the supplied amount
     *
     * @param amount the amount to change this tile's food by
     */
    public void changeFood(int amount)
    {
        food += amount;

        if (food < 0)
        {
            System.out.println("MapStuff.Tile at " + tilePos + " has less than zero food");
        }
    }

    /**
     * adds one pheromone to the tile's pheromone count
     */
    public void addPheromone()
    {
        pheromoneCount++;
    }

    /**
     * static function that allows you to add all the tiles within a certain radius from another tile to a list.
     * <p>
     * Note - this does check if the supplied tile is already in the list. it will not be duplicated.
     *
     * Note - this radius forms a square. The following radii form the following areas:
     * 1	9
     * 2	25
     * 3	49
     * 4	81
     * 5	121
     * 6	169
     * etc
     *
     * @param tile   the tile at the center. tiles that are near this tile will be added to the list
     * @param radius the radius.
     * @param list   the list to add tiles to. (must be an arrayList of MapStuff.Tile's)
     */
    public static void addTiles(Tile tile, int radius, ArrayList<Tile> list)
    {
        if (tile == null)
        {
            System.out.println("MapStuff.Tile addTiles method received a null tile. you should probably figure out why that happened");
        }

        //if the supplied tile is not in the list
        if (!list.contains(tile))
        {
            //add it to the list
            list.add(tile);
        }

        //add tiles directly to the left of the central tile to the list
        Tile left = tile;
        for (int lcv = 0; lcv < radius; lcv++)
        {
            left = left.getAdjacentTile(3);

            //only add the tile to the list if it is not null
            if (left != null)
            {
                list.add(left);
            }
        }

        //add tiles directly to the right of the central tile to the list
        Tile right = tile;
        for (int lcv = 0; lcv < radius; lcv++)
        {
            right = right.getAdjacentTile(1);

            //only add the tile to the list if it is not null
            if (right != null)
            {
                list.add(right);
            }
        }

        //create a temporary list to add tiles to (this will be combined into list at the end)
        ArrayList<Tile> tempList = new ArrayList<>();

        //loop through this horizontal list of tiles to add all above/below tiles to the temporary list
        for (Tile horizonTile : list)
        {
            //add all tiles above the central row of tiles to the temporary list
            Tile up = horizonTile;
            for (int lcv = 0; lcv < radius; lcv++)
            {
                up = up.getAdjacentTile(0);

                //only add the tile to the list if it is not null
                if (up != null)
                {
                    tempList.add(up);
                }
            }

            //add all tiles below the central row of tiles to the temporary list
            Tile down = horizonTile;
            for (int lcv = 0; lcv < radius; lcv++)
            {
                down = down.getAdjacentTile(2);

                //only add the tile to the list if it is not null
                if (down != null)
                {
                    tempList.add(down);
                }
            }
        }

        //combine the temporary list onto the real list
        list.addAll(tempList);
    }

    /**
     * gets how much food is on this tile
     *
     * @return the amount of food on this tile
     */
    public int getFood()
    {
        return food;
    }

    /**
     * renders this tile
     *
     * @param g         the graphics object used to render everything
     * @param tileWidth the width of this tile (in pixels)
     */
    public void render(Graphics g, int tileWidth)
    {
        //color is green with an opacity based on the amount of food on the tile
        g.setColor(new Color(48, 89, 39, (int) (food * 1.0 / maxFood * 255)));
        g.fillRect((int) (centerPos.x - tileWidth / 2.0), (int) (centerPos.y - tileWidth / 2.0), tileWidth, tileWidth);
    }

    //TODO: is this necessary?
    public Vec2 getCenter()
    {
        return centerPos;
    }

    /**
     * sets this tile's ant to the supplied ant
     *
     * @param ant the new ant for this tile to have
     */
    public void setAnt(Ant ant)
    {
        this.ant = ant;
    }

    /**
     * gets this tile's ant
     *
     * @return the ant on this tile
     */
    public Ant getAnt()
    {
        return ant;
    }

    /**
     * sets this tile's adjacent tiles
     *
     * @param adjs an arraylist of tiles representing the tiles adjacent to this tile
     */
    public void setAdjacents(ArrayList<Tile> adjs)
    {
        this.adjacents = adjs;
    }

    /**
     * gets a tile adjacent to this one (null if there is no such tile)
     * [0 - above]
     * [1 - right]
     * [2 - below]
     * [3 - left]
     * <p>
     * (numbers outside the range [0, 3] are converted via mod 4
     *
     * @param index the index of the tile that is desired
     * @return an adjacent tile in the direction of index
     */
    public Tile getAdjacentTile(int index)
    {
        return adjacents.get(index % 4);
    }
}
