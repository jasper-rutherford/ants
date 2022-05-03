package MenuStuff;

import Simulation.Ants.Ant;

import java.util.ArrayList;

public class AntTab extends Tab
{
    private ArrayList<Ant> ants;

    private Ant oldest;         //the oldest ant
    private Ant bootlicker;     //the ant that has contributed the most to the stash
    private Ant lazy;           //the ant that has contributed the least to the stash
    private int numDead;        //the amount of ants that are dead


    /**
     * creates an MenuStuff.AntTab that draws statistics from a list of ants
     * @param ants the ants to draw statistics from.
     */
    public AntTab(ArrayList<Ant> ants)
    {
        //calls the MenuStuff.Tab superclass' constructor
        super();

        //set this MenuStuff.AntTab's ants to the supplied ants
        this.ants = ants;
    }

    //recalculate the ant statistics
    public void tick()
    {
//        //only recalculates if there are ants in the list
//        if (ants.size() > 0)
//        {
//            //initializes all values to zero
//            oldest = ants.get(0);
//            bootlicker = ants.get(0);
//            lazy = ants.get(0);
//            numDead = 0;
//
//            //loops through the list of ants and calculates each statistic
//            for (Ant ant : ants)
//            {
//                //find oldest ant
//                if (ant.getAge() > oldest.getAge())
//                {
//                    oldest = ant;
//                }
//
//                //find bootlicker ant (the ant with the greatest net stash contribution)
//                if (ant.getNetStashContribution() > bootlicker.getNetStashContribution())
//                {
//                    bootlicker = ant;
//                }
//
//                //find lazy ant (the ant with the lowest net stash contribution)
//                else if (ant.getNetStashContribution() < lazy.getNetStashContribution())
//                {
//                    lazy = ant;
//                }
//
//                //calculate how many ants are dead
//                if (ant.getHealth() <= 0)
//                {
//                    numDead++;
//                }
//            }
//        }
//
//        //if there are no ants, set all statistics accordingly
//        else
//        {
//            oldest = null;
//            bootlicker = null;
//            lazy = null;
//            numDead = 0;
//        }
    }

    public void render()
    {
    }
}