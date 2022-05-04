package Revamped;

import java.util.ArrayList;

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
    private ArrayList<Tile> visitedTiles;
    private int currentPathPosition;

    private boolean returning;

    private boolean reachedFood;

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
        returning = false;
        //default stuff
        heldFood = 0;
        reachedFood = false;

        //initialize path list
        path = new ArrayList<>();
        visitedTiles = new ArrayList<>();
        currentPathPosition = 0;
    }

    /**
     * Updates this ant by one tick
     *
     * @param indicator used to indicate if this update is during the search phase or the return phase. 0 for search, 1 for return.
     */
    public void update(int indicator)
    {
        //if returning
        if (indicator == 1)
        {
            //mark this ant as returning
            returning = true;
        }

        //get all adjacent edges
        ArrayList<Edge> edges = new ArrayList<>(tile.getEdges());

        //if not returning
        if (!returning)
        {
            //loop through all edges
            for (int i = edges.size() - 1; i >= 0; i--)
            {
                //if this edge leads to a tile which has already been visited
                if (visitedTiles.contains(edges.get(i).getOtherTile(tile)))
                {
                    //ignore it
                    edges.remove(i);
                }
            }

            //if all tiles have been visited, then mark this ant as returning
            if (edges.size() == 0)
            {
                returning = true;
            }
        }

        Edge chosenEdge = null;
        Tile nextTile;

        //if not returning
        if (!returning)
        {
            //this hell-code chooses an adjacent edge randomly, weighted by how much pheromone is on the edges
            double[] pheromoneCaps = new double[edges.size()];
            pheromoneCaps[0] = edges.get(0).getPheromoneCount();
            for (int i = 1; i < edges.size(); i++)
            {
                pheromoneCaps[i] = pheromoneCaps[i - 1] + edges.get(0).getPheromoneCount();
            }
            double choice = Math.random() * pheromoneCaps[pheromoneCaps.length - 1];

            for (int i = 0; i < pheromoneCaps.length && chosenEdge == null; i++)
            {
                if (choice < pheromoneCaps[i])
                {
                    chosenEdge = edges.get(i);
                }
            }
        }
        //if returning
        else
        {
            //if there is more path to traverse
            if (currentPathPosition > 0)
            {
                //go backwards along the path
                chosenEdge = path.get(currentPathPosition - 1);
            }
        }

        //if no edge has been chosen
        if (chosenEdge == null)
        {
            //assign the current tile as the tile to move to
            nextTile = tile;
        }
        //if an edge has been chosen
        else
        {
            //get the next tile to move to
            nextTile = chosenEdge.getOtherTile(tile);
        }

        //move to the new tile
        tile.removeAnt(this);       //remove from current tile
        nextTile.addAnt(this);      //add to new tile
        tile = nextTile;            //set new tile to be the current tile

        //ants who are not returning
        if (!returning)
        {
            //add this new edge to their path
            path.add(chosenEdge);

            //mark themselves as one step further into their path
            currentPathPosition++;

            //if food is reached (and it's not in the stash)
            if (tile.getFood() > 0 && !tile.isStash())
            {
                //mark ant as returning
                returning = true;

                //remove a food from the tile
                tile.changeFood(-1);

                //mark the ant as holding a food
                heldFood = 1;

                //mark the ant as reached food
                reachedFood = true;
            }
        }
        //ants who are returning
        else
        {
            //mark themselves as one step further back in their path
            currentPathPosition--;

            //check if entire path has been reversed
            if (currentPathPosition <= 0)
            {
                //if holding food, drop it on the tile
                if (heldFood > 0)
                {
                    tile.changeFood(heldFood);
                    heldFood = 0;
                }
            }
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

    public boolean isReturning()
    {
        return returning;
    }

    public ArrayList<Edge> getPath()
    {
        return path;
    }

    public boolean reachedFood()
    {
        return reachedFood;
    }

    public void reset()
    {
        //reset variables
        returning = false;
        heldFood = 0;
        reachedFood = false;

        //reinitialize lists
        path = new ArrayList<>();
        visitedTiles = new ArrayList<>();
        currentPathPosition = 0;
    }
}
