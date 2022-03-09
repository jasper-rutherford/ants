package Simulation.Ants;

import MapStuff.Tile;

import java.util.ArrayList;

public class Worker extends Ant
{
    //the generation this ant was born in
    private int age;
    private boolean foodHeld;

    private ArrayList<Integer> inputs;
    private ArrayList<Double> outputs;

    /**
     * default constructor for the worker class
     */
    public Worker(Tile startTile)
    {
        super(startTile);

        age = 0;
        foodHeld = false;

        generateGenome();
    }

    /**
     * reverts the worker back to its starting values
     */
    @Override
    public void reset()
    {
        super.reset();
        foodHeld = false;
    }

    //TODO:
    public void generateGenome()
    {

    }

    /**
     * generates a new worker ant from two parents
     *
     * @param parent1 the first parent
     * @param parent2 the second parent
     * @param age     the generation this ant was born in
     */
    public Worker(Tile startTile, Worker parent1, Worker parent2, int age)
    {
        super(startTile);
        //TODO
    }

    /**
     * updates the worker by one tick
     */
    public void update()
    {
        think();
    }

    /**
     * makes a decision on what to do
     */
    public void think()
    {
        calculateInputs();
        calculateOutputs();
        decide();
    }

    public void calculateInputs()
    {
        //TODO:
    }

    public void calculateOutputs()
    {
        //TODO
    }

    /**
     * use the output values to decide what to do
     */
    public void decide()
    {
        //TODO: remove this, its a temp fix
        outputs = new ArrayList<>();
        outputs.add(0.0);
        outputs.add(0.0);
        outputs.add(0.0);
        outputs.add(0.0);
        outputs.add(1.0);

        //find the largest value in the outputs
        int maxIndex = 0;
        double maxValue = outputs.get(0);

        for (int lcv = 0; lcv < outputs.size(); lcv++)
        {
            double value = outputs.get(lcv);

            if (value > maxValue)
            {
                maxIndex = lcv;
                maxValue = value;
            }
        }

        //first four values mean move (direction depending on which value)
        if (maxIndex <= 3)
        {
            move(maxIndex);
        }

        //fifth value means eat
        else if (maxIndex == 4)
        {
            eat();
        }

        //sixth value means pickup food
        else if (maxIndex == 5)
        {
            pickup();
        }

        //seventh value means drop food
        else if (maxIndex == 6)
        {
            drop();
        }
    }

    /**
     * tries to eat (must be holding food to do so)
     */
    public void eat()
    {
        if (foodHeld)
        {
            foodHeld = false;
            changeHungerBar(10);
        }
    }

    /**
     * tries to pick up a food from the worker's current tile
     */
    public void pickup()
    {
        //if not holding food
        if (!foodHeld)
        {
            //if the worker's current tile has food
            if (tile.getFood() > 0)
            {
                //take a food off the tile
                tile.changeFood(-1);

                //mark the worker as holding food
                foodHeld = true;
            }
        }
    }

    /**
     * tries to drop food onto the worker's current tile
     */
    public void drop()
    {
        //if the worker is holding food
        if (foodHeld)
        {
            //add one food to the worker's current tile
            tile.changeFood(1);

            //mark the worker as not holding food
            foodHeld = false;
        }
    }

    /**
     * tries to move this ant in the supplied direction
     * [0 - above]
     * [1 - right]
     * [2 - below]
     * [3 - left]
     *
     * @param dir the direction to move the ant.
     */
    public void move(int dir)
    {
        //get the tile in the chosen direction
        Tile toTile = tile.getAdjacentTile(dir);

        //if the tile exists (ie is not outside the map) and the tile has no current ant
        if (toTile != null && toTile.getAnt() == null)
        {
            //add this ant to that new tile
            toTile.setAnt(this);

            //remove this ant from its current tile
            tile.setAnt(null);

            //set this ant's tile to the new tile
            tile = toTile;
        }
    }
}
