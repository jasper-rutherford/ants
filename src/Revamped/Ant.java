package Revamped;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Ant class
 * Represents one ant
 */
public class Ant
{
    /**
     * The tile that this ant is currently on
     */
    private Tile tile;

    /**
     * How much food this ant is currently holding
     */
    private int heldFood;

    private ArrayList<Edge> path;

    /**
     * Generates an ant with default settings (aka find food mode) on the specified tile
     *
     * @param tile the tile for this ant to spawn on
     */
    public Ant(Tile tile)
    {
        //add this ant to the tile
        this.tile = tile;
        tile.addAnt(this);

        //default stuff
        heldFood = 0;

        //initialize path list
        path = new ArrayList<>();
    }

    /**
     * Updates this ant by one tick
     *
     * @param indicator used to indicate if this update is during the search phase or the return phase. 0 for search, 1 for return.
     */
    public void update(int indicator)
    {
        //get all adjacent edges and tiles
        ArrayList<Edge> edges = new ArrayList<>(tile.getEdges());

        //ignore any edges which have already been visited
        for (int i = edges.size() - 1; i >= 0; i--)
        {
            //if this edge is already in the path
            if (path.contains(edges.get(i)))
            {
                //ignore it
                edges.remove(i);
            }
        }

        //if all tiles have been visited, then mark this ant as lost and move back along the path back to the stash
        if (edges.size() == 0)
        {
            lost = true;
        }

        //if not lost
        if (!lost)
        {
            //this hell-code chooses an adjacent edge randomly, weighted by how much pheromone is on the edges
            double[] pheromoneCaps = new double[edges.size()];
            pheromoneCaps[0] = (edges.get(0).getPheromoneCount() - 1) * pheromoneWeight + 1;
            for (int i = 1; i < edges.size(); i++)
            {
                pheromoneCaps[i] = pheromoneCaps[i - 1] + (adjacentTiles.get(i).getColonyPheromones() - 1) * pheromoneWeight + 1;
            }
            double choice = Math.random() * pheromoneCaps[pheromoneCaps.length - 1];
            Tile chosenTile = null;
            for (int i = 0; i < pheromoneCaps.length && chosenTile == null; i++)
            {
                if (choice < pheromoneCaps[i])
                {
                    chosenTile = adjacentTiles.get(i);
                }
            }
        }

        if (chosenTile == null)
        {
            System.out.println("anger");
        }
        //move to the new tile
        tile.removeAnt(this);       //remove from current tile
        chosenTile.addAnt(this);    //add to new tile
        tile = chosenTile;          //set new tile to be the current tile

        //check if this new tile is in the stash
        if (tile.isStash())
        {
            //place food on the tile
            tile.changeFood(1);

            //drop the food
            heldFood = 0;

            //switch to colony pheromones
            foodPheromonesActive = false;
            colonyPheromonesActive = true;

            //forget all recent tiles
            recentTiles.clear();
        }
    }

    /**
     * Gets how much food this ant is holding
     *
     * @return an int representing how much food this ant is holding
     */
    public int getHeldFood()
    {
        return heldFood;
    }
}
