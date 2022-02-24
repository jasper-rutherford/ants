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
        age = 0;
        inputs = new double[numInputs];
        outputs = new double[numOutputs];

        generateGenome();
    }

    /**
     * Generate a child ant from two parent ants
     */
    public Ant(Ant a1, Ant a2)
    {
        age = 0;
        //TODO
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
                genome[x][y] = Math.random() * 20000 - 10000;
            }
        }
    }

    /**
     * Resets the ant to the position/colonyID specified.
     */
    public void reset(int colonyID)
    {
        //TODO: make this work
        this.colonyID = colonyID;

        health = maxHealth;
        hunger = 0;
        heldFood = 0;

        //hey make this face away from the center of the stash that's fun
        direction = new Vec2(0, 0);
//        position = pos;

        speed = 0;
    }

    public void render()
    {
        //TODO
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
        //TODO
    }

    public int getNetStashContribution()
    {
        return netStashContribution;
    }
}
