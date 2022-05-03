package Revamped;

import java.awt.*;
import java.util.ArrayList;

/**
 * Map class
 * Represents the board and does the board updates
 */
public class Map
{
    /**
     * The list of tiles on the board
     * A map with a radius of 2 looks a little something like this:
     * <p>
     * N123
     * 4567
     * 890AB
     * CDEF
     * XGHI
     * <p>
     * Where
     * - 5 is adjacent to 1, 2, 6, 0, 9, 4
     * - 0 is adjacent to 5, 6, A, E, D, 0
     * - etc
     */
    @SuppressWarnings("SpellCheckingInspection")
    private Tile[][] tiles;

    /**
     * A list of all the ants on the board
     */
    private ArrayList<Ant> ants;

    /**
     * Generates the tiles and the ants
     */
    public Map(int mapRadius, int stashRadius)
    {
        //Generates the tiles
        generateTiles(mapRadius);

        //Generates the ants
        generateAnts(stashRadius);
    }

    /**
     * Updates the map by one tick
     */
    public void update()
    {

    }

    /**
     * Renders the map to the screen
     *
     * @param g a graphics object passed from the Panel
     */
    public void render(Graphics g)
    {

    }

    /**
     * Generates all the tiles in the map
     * One big hexagon of tiles with the supplied radius
     * A radius of r creates a map which is r*2 + 1 tiles wide
     *
     * @param mapRadius the radius of the map. A radius of r creates a map which is r*2 + 1 tiles wide
     */
    public void generateTiles(int mapRadius)
    {
        int width = mapRadius * 2 + 1;
        tiles = new Tile[width][width];

        int left = 0;
        int right = width - 1;

        //generate the tiles lmao
        for (int y = mapRadius; y < width; y++)
        {
            //fill the row with new tiles or nulls
            for (int x = 0; x < width; x++)
            {
                //if this tile would be within the bounds [left, right] then it is a new tile, otherwise it is null
                if (x <= left && x >= right)
                {
                    //fills the grid symmetrically across the central row
                    tiles[x][y] = new Tile();
                    tiles[x][mapRadius * 2 - y] = new Tile();
                }
                else
                {
                    //fills the grid symmetrically across the central row
                    tiles[x][y] = null;
                    tiles[x][mapRadius * 2 - y] = null;
                }
            }

            //shrink the valid bounds
            if (y % 2 == 1)
            {
                //shrink right bound if this row is odd
                right--;
            }
            else
            {
                //shrink left bound if this row is even
                left++;
            }
        }
    }
}
