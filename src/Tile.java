import java.awt.*;

public class Tile
{
    private int food;
    private int pheromones[];
    private Vec2 tilePos;   //this tile's position in the map's tiles array
    private Vec2 centerPos; //coordinates of the center of this tile
    private boolean isStash; //TODO: make sure this goes with map's setup or colony's setup or something idk

    // private boolean isStash;

    public Tile(Vec2 tilePos, int tileWidth, int maxFood)
    {
        this.tilePos = tilePos;
        this.centerPos = new Vec2((tilePos.x + 0.5) * tileWidth + 10, (tilePos.y + 0.5) * tileWidth + 10);
        isStash = false;


        //TODO: replace with cooler food function (also convert this to a method :/)
        Vec2 mapCenter = new Vec2(650 / 2 + 10, 650 / 2 + 10);      //todo make this scaleable
        double distance = mapCenter.minus(centerPos).length() / 15;
        if (distance > 100)
        {
            distance = 100;
        }
        double trueFoodMax = -Math.sqrt(-(distance * distance) + (2 * distance * maxFood)) + maxFood;
        food = (int) trueFoodMax;
        // food = (int) (Math.random() * trueFoodMax);

        //initialize pheromones to null (must wait for setup(), depends on numColonies)
        pheromones = null;
    }

    public int getFood()
    {
        return food;
    }

    public void render(Graphics g, int tileWidth, int maxFood)
    {
        if (isStash)
        {
            //TODO: render stash differently
//             noFill();
//             stroke(1);
//             rect(centerPos.x - tileWidth / 2, centerPos.y - tileWidth / 2, tileWidth, tileWidth);
        }

        //color is green with an opacity based on the amount of food on the tile
        g.setColor(new Color(48, 89, 39, (int) (food * 1.0 / maxFood * 255)));
        g.fillRect((int)(centerPos.x - tileWidth / 2.0), (int)(centerPos.y - tileWidth / 2.0), tileWidth, tileWidth);
    }

    public Vec2 getCenter()
    {
        return centerPos;
    }
}
