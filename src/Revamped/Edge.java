package Revamped;

import java.util.ArrayList;

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
}
