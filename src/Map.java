import java.awt.*;

public class Map
{
    private int scaleFactor = 5;
    private int mapWidth = 13 * scaleFactor;
    private Tile tiles[][];
    private int tileWidth = 10;

    //the maximum amount of food allowed to generate on a tile
    private int maxFood = 100;

    private boolean drawGrid; //whether or not to draw a grid of lines around the tiles

    public Map()
    {
        generateTiles();
    }
    //TODO: create setup method that sets up pheromones (and stashes?)

    private void generateTiles()
    {
        tiles = new Tile[mapWidth][mapWidth];
        for (int x = 0; x < mapWidth; x++)
        {
            for (int y = 0; y < mapWidth; y++)
            {
                tiles[x][y] = new Tile(new Vec2(x, y), tileWidth, maxFood);
            }
        }
    }

    /**
     *   Render the map and everything in it.
     */
    public void render(Graphics g)
    {
        //TODO: fix map rendering
        //fill background color
        g.setColor(new Color(189, 173, 121));
        g.fillRect(10, 10, 650, 650);//TODO: make scaleable :/

        //render tiles
        for (Tile[] line : tiles)
        {
            for (Tile tile : line)
            {
                tile.render(g, tileWidth, maxFood);
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

    public int getScaleFactor()
    {
        return scaleFactor;
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
