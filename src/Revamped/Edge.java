package Revamped;

import java.awt.*;
import java.util.ArrayList;

import static Revamped.Tile.squareWidth;

public class Edge
{
    private ArrayList<Tile> tiles;

    private double pheromoneCount;

    public Edge(Tile t1, Tile t2)
    {
        tiles = new ArrayList<>();
        tiles.add(t1);
        tiles.add(t2);
        pheromoneCount = 1;
    }

    public ArrayList<Tile> getTiles()
    {
        return tiles;
    }

    public void changePheromoneCount(double change)
    {
        pheromoneCount += change;

        if (pheromoneCount < 0)
        {
            pheromoneCount = 0;
        }
    }

    public void multiplyPheromoneCount(double change)
    {
        pheromoneCount *= change;
    }

    public double getPheromoneCount()
    {
        return pheromoneCount;
    }

    /**
     * Gets the tile connected to this edge that isnt the supplied tile
     *
     * @param aTile the tile to not get from this edge
     * @return the other tile connected to this edge
     */
    public Tile getOtherTile(Tile aTile)
    {
        return tiles.get(0) == aTile ? tiles.get(1) : tiles.get(0);
    }

    public void render(Graphics g)
    {
        Tile t1 = tiles.get(0);
        Tile t2 = tiles.get(1);

        int x1 = t1.getX();
        int y1 = t1.getY();

        int x2 = t2.getX();
        int y2 = t2.getY();

        //calculate coords position
        int xpos1 = 100 + ((y1 + 1) % 2 * (squareWidth / 2)) + x1 * squareWidth;
        int ypos1 = 100 + y1 * squareWidth;

        //calculate coords position
        int xpos2 = 100 + ((y2 + 1) % 2 * (squareWidth / 2)) + x2 * squareWidth;
        int ypos2 = 100 + y2 * squareWidth;

        int xpos = (xpos1 + xpos2) / 2 + squareWidth * 2 / 5;
        int ypos = (ypos1 + ypos2) / 2 + squareWidth * 2 / 5;

        int alpha = (int)(pheromoneCount / 50 * 255);
        g.setColor(new Color(0, 0, 0, Math.min(alpha, 255)));
        g.fillRect(xpos, ypos, squareWidth / 4, squareWidth / 4);

        g.setColor(new Color(1, 1, 1));
//        g.drawString(pheromoneCount + "", xpos, ypos);
    }
}
