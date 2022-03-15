package Simulation.Ants;

import Simulation.MapStuff.Tile;
import Simulation.Simulation;
import Util.Matrix;
import Util.Vec2;

import java.util.ArrayList;

public class Worker extends Ant implements Comparable<Worker>
{
    //the generation this ant was born in
    private int age;
    private boolean foodHeld;

    /**
     * workers can detect information on all tiles within this radius
     */
    private final int detectionRadius = 2;

    /**
     * the range of values that weights can span
     */
    private final double[] weightRange = {-1, 1};

    /**
     * the range of values that biases can span
     */
    private final double[] biasRange = {-1, 1};

    //input matrix
    private final int numInputs = 103;
    private Matrix inputs;

    //hidden layer 1
    private final int numHiddenNeurons1 = 10;
    private Matrix hiddenWeights1;
    private Matrix hiddenBiases1;

    //hidden layer 2
    private final int numHiddenNeurons2 = 10;
    private Matrix hiddenWeights2;
    private Matrix hiddenBiases2;

    //output layer
    private final int numOutputNeurons = 8;
    private Matrix outputWeights;
    private Matrix outputBiases;

    //the output values
    private ArrayList<Double> outputs;

    private int lifeTime;

    private int stashContribution;

    private int gluttony;

    private final int stashContributionWorth = 50;

    /**
     * default constructor for the worker class
     */
    public Worker(Tile startTile)
    {
        super(startTile);

        age = 0;
        foodHeld = false;
        lifeTime = 0;
        stashContribution = 0;
        gluttony = 0;

        //generate random neuron values
        generateNeurons();
    }

    /**
     * reverts the worker back to its starting values
     */
    @Override
    public void reset()
    {
        super.reset();
        foodHeld = false;
        lifeTime = 0;
        stashContribution = 0;
        gluttony = 0;
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

        age = 0;
        foodHeld = false;
        lifeTime = 0;
        stashContribution = 0;
        gluttony = 0;

        hiddenWeights1 = new Matrix(parent1.hiddenWeights1, parent2.hiddenWeights1);
        hiddenBiases1 = new Matrix(parent1.hiddenBiases1, parent2.hiddenBiases1);

        hiddenWeights2 = new Matrix(parent1.hiddenWeights2, parent2.hiddenWeights2);
        hiddenBiases2 = new Matrix(parent1.hiddenBiases2, parent2.hiddenBiases2);

        outputWeights = new Matrix(parent1.outputWeights, parent2.outputWeights);
        outputBiases = new Matrix(parent1.outputBiases, parent2.outputBiases);

    }

    /**
     * generates random values for the neurons
     */
    public void generateNeurons()
    {
        //hidden layer 1
        hiddenWeights1 = new Matrix(numHiddenNeurons1, numInputs, weightRange[0], weightRange[1]);
        hiddenBiases1 = new Matrix(numHiddenNeurons1, 1, biasRange[0], biasRange[1]);

        //hidden layer 2
        hiddenWeights2 = new Matrix(numHiddenNeurons2, numHiddenNeurons1, weightRange[0], weightRange[1]);
        hiddenBiases2 = new Matrix(numHiddenNeurons2, 1, biasRange[0], biasRange[1]);

        //output layer
        outputWeights = new Matrix(numOutputNeurons, numHiddenNeurons2, weightRange[0], weightRange[1]);
        outputBiases = new Matrix(numOutputNeurons, 1, biasRange[0], biasRange[1]);
    }

    /**
     * updates the worker by one tick
     */
    public void update()
    {
        if (health > 0)
        {
            think();
            lifeTime++;
        }
    }

    /**
     * makes a decision on what to do
     */
    public void think()
    {
        calculateInputs();
        calculateOutputs();
        decide();

        //always lose 1 hunger
        changeHungerBar(-1);

        //release a pheromone on the tile
        tile.addPheromone();
        tile.addPheromone();
        tile.addPheromone();
        tile.addPheromone();
        tile.addPheromone();
    }

    /**
     * gets all the inputs and puts them in the input matrix
     */
    public void calculateInputs()
    {
        //get the tiles that this ant can detect
        Tile[][] organized = getDetectedTiles();

        //get all relevant inputs from those tiles
        ArrayList<Double> inputList = getInputsFromTiles(organized);

        //add this worker's stats to the inputList
        inputList.add(foodHeld ? 1.0 : 0.0);                //add a 1 if holding food, add a zero if not
        inputList.add((double) hungerBar / maxHungerBar);   //add the worker's hungerbar
        inputList.add((double) health / maxHealth);         //add the worker's health

        //convert the inputList to an array for matrix creation
        double[][] inputArray = new double[numInputs][1];

        for (int lcv = 0; lcv < numInputs; lcv++)
        {
            inputArray[lcv][0] = inputList.get(lcv);
        }

        //create a new matrix with the calculated inputs and make that this worker's input matrix
        inputs = new Matrix(inputArray);
    }

    /**
     * gets all the tiles that this worker can detect (stored/organized in a 2d array with null values for tiles outside the map)
     *
     * @return a 2d array of tiles that represents every tile within detectionRadius around this worker
     */
    public Tile[][] getDetectedTiles()
    {
        ArrayList<Tile> detectedTiles = new ArrayList<>();
        Tile.addTiles(tile, detectionRadius, detectedTiles);

        //organize the tiles by relative x/y
        Tile[][] organized = new Tile[detectionRadius * 2 + 1][detectionRadius * 2 + 1];
        for (Tile aTile : detectedTiles)
        {
            Vec2 relPos = aTile.getTilePos().minus(tile.getTilePos());
            organized[(int) relPos.x + detectionRadius][(int) relPos.y + detectionRadius] = aTile;
        }

        return organized;
    }

    /**
     * gets a list of all the relevant inputs from the supplied tiles
     *
     * @param organized the tiles to get inputs from
     * @return an arraylist of doubles representing all the relevant inputs from the supplied tiles
     */
    public ArrayList<Double> getInputsFromTiles(Tile[][] organized)
    {
        ArrayList<Double> inputList = new ArrayList<>();
        //loop through organized tiles and add relevant input data to input list
        for (Tile[] line : organized)
        {
            for (Tile aTile : line)
            {
                //if there is no tile (ie it is off the map)
                if (aTile == null)
                {
                    //all input values are -1
                    inputList.add(-1.0);
                    inputList.add(-1.0);
                    inputList.add(-1.0);
                    inputList.add(-1.0);
                }

                //otherwise, add relevant data from tile
                else
                {
                    //add pheromone count
                    inputList.add((double) aTile.getPheromoneCount() / aTile.getMaxPheromoneCount());

                    //add food count
                    inputList.add((double) aTile.getFood() / aTile.getMaxFood());

                    //if the tile has no ant
                    if (aTile.getAnt() == null)
                    {
                        //mark the tile as not worker
                        inputList.add(0.0);

                        //mark the tile as not queen
                        inputList.add(0.0);
                    }

                    //if the tile has a worker
                    else if (aTile.getAnt() instanceof Worker)
                    {
                        //mark the tile as a worker
                        inputList.add(1.0);

                        //mark the tile as not queen
                        inputList.add(0.0);
                    }

                    //if the tile has a queen
                    else if (aTile.getAnt() instanceof Queen)
                    {
                        //mark the tile as not worker
                        inputList.add(0.0);

                        //mark the tile as a queen
                        inputList.add(1.0);
                    }
                }
            }
        }

        return inputList;
    }

    /**
     * calculates the output list from the neuron layers
     */
    public void calculateOutputs()
    {
        Matrix layer1Results = Matrix.add(Matrix.dot(inputs, hiddenWeights1), hiddenBiases1);
        Matrix layer2Results = Matrix.add(Matrix.dot(layer1Results, hiddenWeights2), hiddenBiases2);
        Matrix outputResults = Matrix.add(Matrix.dot(layer2Results, outputWeights), outputBiases);

        //convert output results to arraylist
        outputs = new ArrayList<>();
        double[][] vals = outputResults.getVals();

        for (int lcv = 0; lcv < vals.length; lcv++)
        {
            outputs.add(vals[lcv][0]);
        }
    }

    /**
     * use the output values to decide what to do
     */
    public void decide()
    {
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

        else if (maxIndex == 7)
        {
            //do nothing
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
            changeHungerBar(foodWorth);
        }
    }

    /**
     * adds the supplied value to the hungerBar
     * if hungerBar goes negative, it is set to 0 and the ant takes 1 damage
     * if the hungerBar goes above 100, it is set to 100
     *
     * @param change the amount to change the hungerBar by
     */
    public void changeHungerBar(int change)
    {
        //add the amount to the hungerBar
        hungerBar += change;

        //if hungerBar goes negative, it is set to 0 and the ant takes 1 damage
        if (hungerBar < 0)
        {
            hungerBar = 0;
            health--;
        }

        //if the hungerBar goes above 100, it is set to 100
        else if (hungerBar > 100)
        {
            System.out.println("Gluttony!");
            gluttony += hungerBar - 100;

            hungerBar = 100;
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

                //if the food is picked up near the queen
                if (closeToQueen())
                {
                    //decrease the ant's fitness
                    stashContribution--;
                }
            }
        }
    }

    /**
     * tries to drop food onto the worker's current tile
     */
    public void drop()
    {
        //if the worker is holding food and the current tile has less than max food
        if (foodHeld && tile.getFood() < tile.getMaxFood())
        {
            //add one food to the worker's current tile
            tile.changeFood(1);

            //mark the worker as not holding food
            foodHeld = false;

            //if the food is dropped near the queen
            if (closeToQueen())
            {
                //increase the ant's fitness
                stashContribution++;
            }
        }
    }

    /**
     * checks if this worker is within Simulation.stashRadius of the queen
     * @return a boolean - true if near the queen, false otherwise
     */
    public boolean closeToQueen()
    {
        //get the tiles within Simulation.stashRadius of this worker
        ArrayList<Tile> nearTiles = new ArrayList<>();
        Tile.addTiles(tile, Simulation.stashRadius, nearTiles);

        //loop through near tiles
        for (Tile aTile : nearTiles)
        {
            //if the tile has a queen
            if (aTile.getAnt() instanceof Queen)
            {
                //return true
                return true;
            }
        }

        //if no queens are found, return false
        return false;
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

    @Override
    public int compareTo(Worker worker)
    {
        if (getFitness() > worker.getFitness())
        {
            return -1;
        }
        if (getFitness() < worker.getFitness())
        {
            return 1;
        }
        else return 0;
    }

    public int getFitness()
    {
        return lifeTime + stashContribution * stashContributionWorth - gluttony;
    }

    public String toString()
    {
        return "\tFitness:\t\t\t" + getFitness() +
                "\n\tStash Contribution:\t" + stashContribution +
                "\n\tGluttony:\t\t\t" + gluttony +
                "\n\tLifetime:\t\t\t" + lifeTime;
    }

    public void printOutputs()
    {
        for (Double dub : outputs)
        {
            System.out.println(dub);
        }
    }
}
