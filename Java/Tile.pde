public class Tile
{
    private int food;
    private int pheromones[];
    private Vec2 tilePos;   //this tile's position in the map's tiles array
    private Vec2 centerPos; //coordinates of the center of this tile

    // private boolean isStash;
    
    public Tile(int numColonies, Vec2 tilePos, int tileWidth, int maxFood)
    {
        this.tilePos = tilePos;
        this.centerPos = new Vec2((tilePos.x + 0.5) * tileWidth + 10, (tilePos.y + 0.5) * tileWidth + 10);
        Vec2 mapCenter = new Vec2(650 / 2 + 10, 650 / 2 + 10);      //todo make this scaleable
        double distance = mapCenter.minus(centerPos).length() / 15;
        if (distance > 100)
        {
            distance = 100;
        }

        double trueFoodMax = -Math.sqrt(-(distance * distance) + (2 * distance * maxFood)) + maxFood;
        food = (int)trueFoodMax;
        // food = (int) (Math.random() * trueFoodMax);
        pheromones = new int[numColonies];
        for (int lcv = 0; lcv < numColonies; lcv++)
        {
            pheromones[lcv] = 0;
        }
    }
    
    public int getFood()
    {
       return food; 
    }
    
    public void render(int tileWidth, int maxFood)
    {
        // if (isStash)
        // {
        //     noFill();
        //     stroke(1);
        //     rect(centerPos.x - tileWidth / 2, centerPos.y - tileWidth / 2, tileWidth, tileWidth);
        // }
        fill(48, 89, 39, food * 1.0 / maxFood * 255);
        noStroke();
        rect(centerPos.x - tileWidth / 2, centerPos.y - tileWidth / 2, tileWidth, tileWidth);
    }

    public Vec2 getCenter()
    {
        return centerPos;
    }
}
