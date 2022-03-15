package Util;

/**
 * used to represent a matrix
 */
public class Matrix
{
    private double mutationChance = 0.01;
    private int width;
    private int height;
    private double min;
    private double max;

    private double[][] vals;

    /**
     * creates a matrix with the supplied dimensions with randomized values between [min, max)
     *
     * @param width  - the width of the matrix
     * @param height - the height of the matrix
     * @param min    - the minimum value in the matrix (inclusive)
     * @param max    - the maximum value in the matrix (exclusive)
     */
    public Matrix(int width, int height, double min, double max)
    {
        //set the width/height
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;

        //initialize the values array to the right size
        vals = new double[width][height];

        //initialize all the values in the array to random values
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                double val = (Math.random() * (max - min)) + min;
                vals[x][y] = val;
            }
        }
    }

    /**
     * creates a matrix with the supplied values
     *
     * @param vals the values for the matrix to have
     */
    public Matrix(double[][] vals)
    {
        this.width = vals.length;
        this.height = vals[0].length;
        this.min = -1.0;
        this.max = 1.0;
        this.vals = vals;
    }

    /**
     * creates a new matrix with values randomly chosen from one of the two parent matrices
     *
     * @param m1 the first parent matrix
     * @param m2 the second parent matrix
     */
    public Matrix(Matrix m1, Matrix m2)
    {
        //set the width/height
        this.width = m1.width;
        this.height = m2.height;
        this.min = m1.min;
        this.max = m1.max;

        //check that sizes match
        if (m1.width == m2.width && m1.height == m2.height)
        {
            //initializes the matrix's values array to the right size
            vals = new double[m1.width][m1.height];

            //fill all values in the array with values from the parents
            for (int x = 0; x < m1.width; x++)
            {
                for (int y = 0; y < m1.height; y++)
                {
                    //chance of mutation (value is re-randomized)
                    if (Math.random() < mutationChance)
                    {
                        vals[x][y] = (Math.random() * (max - min)) + min;
                    }
                    else
                    {
                        //randomly chooses a parent to copy a value from (50% chance to come from one parent, 50% chance to come from the other)
                        vals[x][y] = Math.random() < .5 ? m1.vals[x][y] : m2.vals[x][y];
                    }
                }
            }
        }
        else
        {
            System.out.println("tried to generate a matrix from two matrices which had different sizes");
        }
    }

    /**
     * adds the two supplied matrices together and returns the new matrix
     *
     * @param m1 the first matrix to be added
     * @param m2 the second matrix to be added
     * @return a new matrix which is the sum of the two supplied matrices
     */
    public static Matrix add(Matrix m1, Matrix m2)
    {
        //check that sizes match
        if (m1.width == m2.width && m1.height == m2.height)
        {
            //sum the values into a new array
            double[][] sumVals = new double[m1.width][m1.height];

            for (int x = 0; x < m1.width; x++)
            {
                for (int y = 0; y < m1.height; y++)
                {
                    sumVals[x][y] = m1.vals[x][y] + m2.vals[x][y];
                }
            }

            //create and return a new matrix with the summed values
            return new Matrix(sumVals);
        }
        else
        {
            System.out.println("tried to add two matrices which had different sizes");
            return null;
        }
    }

    /**
     * performs relu on the supplied matrix and returns a new matrix with relu'd values
     *
     * @param matrix the matrix to perform relu on
     * @return a new matrix with relu'd values
     */
    public static Matrix relu(Matrix matrix)
    {
        //relu the values into a new array
        double[][] reluVals = new double[matrix.width][matrix.height];

        for (int x = 0; x < matrix.width; x++)
        {
            for (int y = 0; y < matrix.height; y++)
            {
                reluVals[x][y] = Math.max(0, matrix.vals[x][y]);
            }
        }

        //create and return a new matrix with the relu'd values
        return new Matrix(reluVals);
    }

    /**
     * returns a transposed version of the supplied matrix
     *
     * @param matrix the matrix to transpose
     * @return a new matrix which is the transpose of the supplied matrix
     */
    public static Matrix transpose(Matrix matrix)
    {
        //transpose the values into a new array
        double[][] transposedVals = new double[matrix.width][matrix.height];

        for (int x = 0; x < matrix.width; x++)
        {
            for (int y = 0; y < matrix.height; y++)
            {
                transposedVals[x][y] = matrix.vals[y][x];
            }
        }

        //create and return a new matrix with the transposed values
        return new Matrix(transposedVals);
    }

    /**
     * dots the supplied matrices and returns the result
     *
     * @param m1 the first matrix being dotted (order matters!)
     * @param m2 the second matrix being dotted (order matters!)
     * @return a new matrix which is the result of dotting the two supplied matrices
     */
    public static Matrix dot(Matrix m1, Matrix m2)
    {
        //check that sizes match
        if (m1.width == m2.height)
        {
            //dot the values into a new array
            double[][] dotVals = new double[m2.width][m1.height];

            for (int y = 0; y < m1.height; y++)
            {
                for (int x = 0; x < m2.width; x++)
                {
                    double sum = 0;

                    for (int i = 0; i < m1.width; i++)
                    {
//                        System.out.println(x + " " + y + " " +  i);
                        sum += m1.vals[i][y] * m2.vals[x][i];
                    }

                    dotVals[x][y] = sum;
                }
            }

            //create and return a new matrix with the transposed values
            return new Matrix(dotVals);
        }
        else
        {
            System.out.println("Tried to dot two matrices which had invalid dimensions, returning null");
            return null;
        }
    }

    /**
     * gets this matrix's values
     *
     * @return the double array representing the values in this matrix
     */
    public double[][] getVals()
    {
        return vals;
    }
}
