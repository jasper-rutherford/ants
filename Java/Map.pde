import java.util.*;

public class Map
{
  private int scaleFactor = 5;
  private int mapWidth = 13 * scaleFactor;
  private Tile tiles[][];
  private int tileWidth = 10;

  //the maximum amount of food allowed to generate on a tile
  private int maxFood = 100;

  public Map()
  {
    generateTiles();
  }  

  private void generateTiles()
  {
    tiles = new Tile[mapWidth][mapWidth];
    for (int x = 0; x < mapWidth; x++)
    {
      for (int y = 0; y < mapWidth; y++)
      {
        tiles[x][y] = new Tile(numColonies, new Vec2(x, y), tileWidth, maxFood);
      }
    }
  }

  /**
   *   Render the map and everything in it.
   */
  public void render()
  {
    //draw background
    fill(189, 173, 121);
    rect(10, 10, 650, 650);

    //render colonies
    for (Colony colony : colonies)
    {
      colony.render(tileWidth, scaleFactor);
    }

    //render tiles
    for (Tile[] line : tiles)
    {
      for (Tile tile : line)
      {
        tile.render(tileWidth, maxFood);
      }
    }

    //render ants
    for (Ant ant : ants)
    {
      ant.render();
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
