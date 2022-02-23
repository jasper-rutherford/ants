public class Colony
{
    private int colonyID;

    
    private Vec2 corner;    //index of the tile in the upper left corner of the stash

    private ArrayList<Tile> stash;
    private int stashWidth;

    private ArrayList<Ant> ants; //all the ants associated with this colony

    public Colony(int colonyID, Vec2 corner, int stashWidth, Tile tiles[][])
    {   
        this.colonyID = colonyID;

        this.stashWidth = stashWidth;
        this.corner = corner;

        // add all relevant tiles to the stash
        stash = new ArrayList<Tile>();
        for (int x = 0; x < stashWidth; x++)
        {
            for (int y = 0; y < stashWidth; y++)
            {
                int trueX = (int)corner.x * stashWidth + x;
                int trueY = (int)corner.y * stashWidth + y;
                stash.add(tiles[trueX][trueY]);
            }
        }
    }

    public int getFood()
    {
        return food;
    }

    public int getNumAliveAnts()
    {
        int numAlive = 0;
        for (Ant ant : ants)
        {
            if (ant.getHealth() > 0)
            {
                numAlive++;
            }
        }
        return numAlive;
    }

    /**
    *   Returns the fitness of the colony
    */
    public int getFitness()
    {
        //count how many ants are dead
        int dead = 0;
        for (int lcv = 0; lcv < ants.size(); lcv++)
        {
            if (ants.get(lcv).getHealth() <= 0)
            {
                dead++;
            }
        }

        //count how much food is in the stash
        int food = 0;
        for (int lcv = 0; lcv < stash.size(); lcv++)
        {
            food += stash.get(lcv).getFood();
        }

        //calculate and return the fitness
        return food - dead;
    }

    /**
    *   Culls all the ants in this colony
    *   the ants die. 
    *   to remove the ants from this colony without killing them, use clearAnts()
    */
    public void cull(ArrayList<Ant> globalAnts)
    {
        //remove this colony's ants from the global population list
        for (Ant ant : ants)
        {
            globalAnts.remove(ant);
        }

        //remove this colony's ants from this colony
        ants.clear();
    }

    /**
    *   clears all ants from this colony.
    *   does not kill the ants! Use cull() for that.
    */
    public void clearAnts()
    {
        ants.clear();
    }

    /**
    *   adds the given ant to the colony.
    */
    public void addAnt(Ant ant)
    {
        ants.add(ant);
        ant.setColony(colonyID);
    }

    /**
    *   resets all ants in this colony relative to this colony.
    */
    public void resetAnts()
    {
        for (Ant ant : ants)
        {
            //each ant gets a random position within the stash
            int x = (int)((Math.random() * (stashWidth + 1)) + corner.x * stashWidth);
            int y = (int)((Math.random() * (stashWidth + 1)) + corner.y * stashWidth);

            //and this colony's id
            ant.reset(new Vec2(x, y), colonyID);
        }
    }

    public void render(int tileWidth, int scaleFactor)
    {
        noFill();
        stroke(1);
        Vec2 corner = stash.get(0).getCenter().minus(new Vec2(tileWidth / 2, tileWidth / 2));
        rect(corner.x, corner.y, tileWidth * scaleFactor, tileWidth * scaleFactor);
    }
}
