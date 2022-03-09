package Simulation;

import BigPicture.Vec2;
import MapStuff.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Map
{
    /**
     * The width of the map (in tiles)
     */
    private int mapWidth = 65;

    /**
     * 2d array of tiles, this is really the heart of the map
     */
    private Tile tiles[][];

    /**
     * The width of one tile (in pixels)
     */
    private int tileWidth = 10;

    /**
     * whether or not to draw a grid of lines around the tiles
     */
    private boolean drawGrid;

    /**
     * default constructor for the map.
     * sets up the map.
     */
    public Map()
    {
        //fills tiles with new tiles
        generateTiles();

        //gets each tile's adjacent tile list
        generateAdjacents();

        //does not draw the grid by default
        drawGrid = false;
    }

    /**
     * initializes tiles and fills it with new tile objects
     */
    private void generateTiles()
    {
        tiles = new Tile[mapWidth][mapWidth];
        for (int x = 0; x < mapWidth; x++)
        {
            for (int y = 0; y < mapWidth; y++)
            {
                tiles[x][y] = new Tile(new Vec2(x, y), tileWidth);
            }
        }
    }

    /**
     * generates each tile's list of adjacent tiles
     *
     * 0 - above
     * 1 - right
     * 2 - below
     * 3 - left
     */
    public void generateAdjacents()
    {
        for (int x = 0; x < mapWidth; x++)
        {
            for (int y = 0; y < mapWidth; y++)
            {
                ArrayList<Tile> adjacents = new ArrayList<>();

                Tile temp;

                //add the above tile
                int xIndex = x;
                int yIndex = y - 1;

                if (yIndex < 0)
                {
                    temp = null;
                }
                else
                {
                    temp = tiles[xIndex][yIndex];
                }
                adjacents.add(temp);

                //add the right tile
                xIndex = x + 1;
                yIndex = y;

                if (xIndex >= mapWidth)
                {
                    temp = null;
                }
                else
                {
                    temp = tiles[xIndex][yIndex];
                }
                adjacents.add(temp);

                //add the below tile
                xIndex = x;
                yIndex = y + 1;

                if (yIndex >= mapWidth)
                {
                    temp = null;
                }
                else
                {
                    temp = tiles[xIndex][yIndex];
                }
                adjacents.add(temp);

                //add the left tile
                xIndex = x - 1;
                yIndex = y;

                if (xIndex < 0)
                {
                    temp = null;
                }
                else
                {
                    temp = tiles[xIndex][yIndex];
                }
                adjacents.add(temp);

                //add the list of adjacents to the tile
                tiles[x][y].setAdjacents(adjacents);
            }
        }
    }

    /**
     * reverts the map back to its initial values
     */
    public void reset()
    {
        //resets the tiles
        for (Tile[] line : tiles)
        {
            for (Tile tile : line)
            {
                tile.reset();
            }
        }
    }

    /**
     * toggles whether the gridlines are drawn
     */
    public void toggleDrawGrid()
    {
        drawGrid = !drawGrid;
    }

    /**
     * Render the map and everything in it.
     */
    public void render(Graphics g)
    {
        //fill background color
        g.setColor(new Color(189, 173, 121));
        g.fillRect(10, 10, 650, 650);

        //render tiles
        for (Tile[] line : tiles)
        {
            for (Tile tile : line)
            {
                tile.render(g, tileWidth);
            }
        }

        //draw grid if setting is on
        if (drawGrid)
        {
            for (int lcv = 0; lcv <= tiles.length; lcv++)
            {
                //draw horizontal line
                g.drawLine(10, 10 + lcv * tileWidth, 10 + tiles.length * tileWidth, 10 + lcv * tileWidth);

                //draw vertical line
                g.drawLine(10 + lcv * tileWidth, 10, 10 + lcv * tileWidth, 10 + tiles.length * tileWidth);
            }
        }
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getMapWidth()
    {
        return mapWidth;
    }

    public Tile[][] getTiles()
    {
        return tiles;
    }
}
