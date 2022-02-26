import java.awt.*;

public class Ant
{
    private int colonyID;
    private int age;

    private int maxHealth = 100;
    private int health;

    private int hunger;
    private int heldFood;

    private Vec2 direction;
    private Vec2 position;

    private int topSpeed = 100;
    private double speed;

    private int numInputs = 10;
    private double inputs[];

    private int numOutputs = 10;
    private double outputs[];

    private int genomeCols = 2;
    private int genomeRows = 2;
    private double genome[][];

    //TODO: do this
    private int netStashContribution;


    /**
     * Default constructor for the ant class.
     * simply initializes the inputs/outputs and generates a random genome.
     * sets up no other variables. To setup other variables, use reset()
     */
    public Ant()
    {
        generateStandardInitialStats();

        generateGenome();
    }

    public void generateStandardInitialStats()
    {
        age = 0;
        inputs = new double[numInputs];
        outputs = new double[numOutputs];
    }

    /**
     * Generate a child ant from two parent ants
     */
    public Ant(Ant a1, Ant a2)
    {
        generateStandardInitialStats();

        generateGenomeFrom(a1.genome, a2.genome);
    }

    public void generateGenomeFrom(double[][] genome1, double[][] genome2)
    {
        double[][][] parentGenomes = new double[2][genomeCols][genomeRows];
        parentGenomes[0] = genome1;
        parentGenomes[1] = genome2;

        genome = new double[genomeCols][genomeRows];
        for (int x = 0; x < genomeCols; x++)
        {
            for (int y = 0; y < genomeRows; y++)
            {
                genome[x][y] = Math.random() * 20000 - 10000; //generates a random value between -10,000 and 10,000
            }
        }
    }

    /**
     * generates a random genome for the ant. This will only be used when the initial batch of ants are created.
     */
    private void generateGenome()
    {
        genome = new double[genomeCols][genomeRows];
        for (int x = 0; x < genomeCols; x++)
        {
            for (int y = 0; y < genomeRows; y++)
            {
                genome[x][y] = Math.random() * 20000 - 10000; //generates a random value between -10,000 and 10,000
            }
        }
    }

    /**
     * Resets the ant to the position/colonyID specified.
     */
    public void reset(Vec2 corner, int stashWidth, int tileWidth)
    {
        health = maxHealth;
        hunger = 0;
        heldFood = 0;
        speed = 0;

        //TODO: make this face away from the center of the stash
        direction = new Vec2(0, 0);

        //set the ant's position to be a random location in the stash
        position = new Vec2(Math.random() * stashWidth * tileWidth + corner.x * tileWidth, Math.random() * stashWidth * tileWidth + corner.y * tileWidth);
    }

    public void render(Graphics g)
    {
        //TODO make this better
        //only render the living
        if (health > 0)
        {
            //draw an x at the ant's position ¯\_(ツ)_/¯
            g.setColor(new Color(0, 0, 0));
            g.drawLine((int) position.x - 5, (int) position.y - 5, (int) position.x + 5, (int) position.y + 5);
            g.drawLine((int) position.x + 5, (int) position.y - 5, (int) position.x - 5, (int) position.y + 5);
        }
    }

    public void setColony(int colonyID)
    {
        this.colonyID = colonyID;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getHealth()
    {
        return health;
    }

    public void update()
    {
        if (health > 0)
        {
            //TODO
        }
    }

    public int getNetStashContribution()
    {
        return netStashContribution;
    }
}
