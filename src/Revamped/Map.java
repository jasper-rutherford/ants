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
     * N123N
     * 4567N
     * 890AB
     * CDEFN
     * NGHIN
     * <p>
     * Where
     * - N means null
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
     * How much the pheromones on a tile should decay every tick
     */
    private final double pheromoneDecay = 0.99;

    /**
     * How many times food should be spawned on a random tile
     */
    private final int numFoodSpots = 1;

    /**
     * How much food should be added to a tile for each food spawn
     */
    private int foodSpawnAmount;

    /**
     * This is the maximum amount of food which was spawned on a tile
     */
    private int maxFood;

    private final int mapRadius;

    private final int stashRadius;

    /**
     * Which overlay to display (specified in more detail in switchOverlay())
     */
    private int overlay = 1;

    private double evaporationRate = .99;

    private double maxPheromoneReward = 10;

    private ArrayList<Edge> edges;

    private ArrayList<Tile> foodTiles;

    /**
     * Generates the tiles and the ants
     */
    public Map(int mapRadius, int stashRadius)
    {
        this.mapRadius = mapRadius;
        this.stashRadius = stashRadius;

        restart();
    }

    /**
     * Restarts the map according to the mapRadius/stashRadius that were specified in the constructor
     */
    public void restart()
    {
        //Generates the tiles
        generateTiles();

        //Generates the ants
        generateAnts();

        //Generates the food
        generateFood();
    }

    /**
     * Updates the map by one tick
     * @param indicator used to indicate if this update is during the search phase or the return phase. 0 for search, 1 for return.
     */
    public void update(int indicator)
    {
        //update all the ants
        for (Ant ant : ants)
        {
            ant.update(indicator);
        }
    }

    /**
     * Renders the map to the screen
     *
     * @param g a graphics object passed from the Panel
     */
    public void render(Graphics g)
    {
        //render the squares
        for (int x = 0; x < tiles.length; x++)
        {
            for (int y = 0; y < tiles.length; y++)
            {
                if (tiles[x][y] != null)
                {
                    tiles[x][y].render(g, x, y, maxFood, ants.get(0), overlay);
                }
            }
        }

        //render the edges
        for (Edge edge : edges)
        {
            edge.render(g);
        }
    }

    /**
     * Generates all the tiles in the map
     * One big hexagon of tiles with the supplied radius
     * A radius of r creates a map which is r*2 + 1 tiles wide
     */
    public void generateTiles()
    {
        edges = new ArrayList<>();

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
                if (x >= left && x <= right)
                {
                    //fills the grid symmetrically across the central row
                    tiles[x][y] = new Tile(x, y);
                    tiles[x][mapRadius * 2 - y] = new Tile(x, mapRadius * 2 - y);
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

        //tell the tiles which tiles they are adjacent to
        for (int x = 0; x < tiles.length; x++)          //loop through all tiles in the tiles array
        {
            for (int y = 0; y < tiles.length; y++)      //loop through all tiles in the tiles array
            {
                //get some tile
                Tile tile = tiles[x][y];

                //check if it is null
                if (tile != null)
                {
                    //finding its adjacent tiles
                    ArrayList<Tile> adjs = new ArrayList<>();

                    //finding its edges
                    ArrayList<Edge> edges = new ArrayList<>();

                    //the coordinates of each possible adjacent tile relative to this current tile
                    int[] relX;
                    int[] relY;

                    //tiles in an even row check differently than those in odd rows
                    //note: this checks the tiles clockwise starting with the middle left tile
                    if (y % 2 == 0)
                    {                                               //   OO
                        relX = new int[]{-1, 0, 1, 1, 1, 0};        //  OXO
                        relY = new int[]{0, -1, -1, 0, 1, 1};       //   OO
                    }
                    else
                    {
                        relX = new int[]{-1, -1, 0, 1, 0, -1};      //  OO
                        relY = new int[]{0, -1, -1, 0, 1, 1};       //  OXO
                    }                                               //  OO

                    //loop through all possible adjacent tiles
                    for (int i = 0; i < relY.length; i++)
                    {
                        //check if the relative coordinates are within the tiles array
                        if (x + relX[i] >= 0 && x + relX[i] < width && y + relY[i] >= 0 && y + relY[i] < width)
                        {
                            //check if the possibly adjacent tile is null
                            Tile adj = tiles[x + relX[i]][y + relY[i]];
                            if (adj != null)
                            {
                                //if the tile exists and is not null then it is added to the list of adjacent tiles
                                adjs.add(adj);

                                //check if this edge already exists
                                ArrayList<Edge> adjEdges = adj.getEdges();
                                Edge adjEdge = null;
                                for (Edge edge : adjEdges)
                                {
                                    if (edge.getTiles().contains(tile) && edge.getTiles().contains(adj))
                                    {
                                        adjEdge = edge;
                                    }
                                }

                                if (adjEdge != null)
                                {
                                    edges.add(adjEdge);
                                }
                                else
                                {
                                    //create the new edge
                                    Edge edge = new Edge(tile, adj);

                                    //add to this tile's list of edges
                                    edges.add(edge);

                                    //add to the map's list of edges
                                    this.edges.add(edge);
                                }
                            }
                        }
                    }

                    //tell the tile which tiles it is adjacent to
                    tile.setAdjacentTiles(adjs);

                    //tell the tile which edges belong to it
                    tile.setEdges(edges);
                }
            }
        }
    }

    /**
     * generate ants on all tiles within stashRadius of the center tile
     */
    private void generateAnts()
    {
        //initialize the arraylist
        ants = new ArrayList<>();

        //this uses the same logic as generateTiles does, just with a little more fiddling with the numbers
        //I probably should have generalized this to a function but oh well
        int offset = mapRadius - stashRadius;
        int left = offset;
        int right = mapRadius * 2 - offset;

        for (int y = mapRadius; y <= mapRadius + stashRadius; y++)
        {
            for (int x = offset; x <= mapRadius * 2 - offset; x++)
            {
                if (x >= left && x <= right)
                {
                    //fills the grid symmetrically across the central row
                    Tile t1 = tiles[x][y];
                    Tile t2 = tiles[x][mapRadius * 2 - y];

                    //generate an ant on both tiles
                    ants.add(new Ant(t1));

                    //don't spawn two ants on the same tile (this happens in the first pass of the y loop due to the symmetry thing)
                    if (t1 != t2)
                    {
                        ants.add(new Ant(t2));
                    }

                    //mark both tiles as in the stash
                    t1.setIsStash(true);
                    t2.setIsStash(true);
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

    /**
     * Generates food across the map
     */
    public void generateFood()
    {
        //each food spawn has one food for every ant
        foodSpawnAmount = ants.size();
        maxFood = foodSpawnAmount;

        foodTiles = new ArrayList<>();

        //spawn food on as many tiles as have been dictated
        for (int i = 0; i < numFoodSpots; i++)
        {
            //get a random tile which is on the map and is not in the stash
            //could this run forever? yes. It probably wont.
            Tile spawnFoodHere;
            do
            {
                int x = (int) (Math.random() * tiles.length);
                int y = (int) (Math.random() * tiles.length);

                spawnFoodHere = tiles[x][y];
            }
            while (spawnFoodHere == null || spawnFoodHere.isStash());

            //add food to the tile
            spawnFoodHere.changeFood(foodSpawnAmount);

            foodTiles.add(spawnFoodHere);

            //check if there is any new maximum amount of food (would happen if a tile were chosen to have food spawn on it multiple times)
            if (spawnFoodHere.getFood() > maxFood)
            {
                maxFood = spawnFoodHere.getFood();
            }
        }
    }

    /**
     * Switches to displaying the specified overlay
     * <p>
     * 1: Ants
     * <p>
     * 2: Food
     */
    public void switchOverlay(int overlay)
    {
        this.overlay = overlay;
    }

    public ArrayList<Tile> getTilesAsList()
    {
        ArrayList<Tile> out = new ArrayList<>();
        for (int y = 0; y < tiles.length; y++)
        {
            for (int x = 0; x < tiles.length; x++)
            {
                Tile aTile = tiles[x][y];
                if (aTile != null)
                {
                    out.add(aTile);
                }
            }
        }
        return out;
    }

    /**
     * updates the pheromones on the edges, also resets the ants to go back out into the world
     */
    public void updatePheromones()
    {
        //loop through every edge in the map
        for (Edge edge : edges)
        {
            //decay each edge's pheromones 
            edge.multiplyPheromoneCount(pheromoneDecay);
        }

        //loop through all the ants
        for (Ant ant : ants)
        {
            //if the ant found a path which leads to food
            if (ant.reachedFood())
            {
                //get the path
                ArrayList<Edge> path = ant.getPath();

                //loop through all edges in that path
                for (Edge edge : path)
                {
                    //longer paths receive lower rewards, shorter paths receive higher rewards
                    edge.changePheromoneCount(maxPheromoneReward / path.size());
                }
            }

            //reset the ant
            ant.reset();
        }

        //reset the food tiles to zero
        for (Tile tile : foodTiles)
        {
            tile.setFood(0);
        }
        //reset the food tiles to full
        // (this is two separate loops because I want to be able to support multiple food tiles, where one tile could get chosen multiple times)
        for (Tile tile : foodTiles)
        {
            tile.changeFood(foodSpawnAmount);
        }
    }
}
