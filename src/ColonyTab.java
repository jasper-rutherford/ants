import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class ColonyTab extends Tab
{
    //the list of colonies in their default order
    private ArrayList<Colony> trueColonies;

    //the same list of colonies, sorted according to the current sortMode
    private ArrayList<Colony> sortedColonies;

    //used to indicate how the sortedColonies are sorted.
    // 0 - No sorting                       (aka sort by index, smallest to biggest)
    // 1 - Sort by stored food              (largest to smallest)
    // 2 - Sort by number of living ants    (largest to smallest)
    // 3 - Sort by fitness                  (largest to smallest)
    private int sortMode = 0;

    public ColonyTab(ArrayList<Colony> colonies)
    {
        //calls the Tab superclass' constructor
        super();

        //trueColonies is initialized to the supplied list of colonies
        trueColonies = colonies;

        //sortedColonies is initialized to be empty (it is assumed that tick() will be called before render()
        sortedColonies = new ArrayList<>();

        //sortMode defaults to 0
        sortMode = 0;
    }

    /**
     * updates all the information in the tab based on the current state of the colonies.
     */
    public void tick()
    {
        sortedColonies.clear();

        //sort by colony number
        if (sortMode == 0)
        {
            sortedColonies.addAll(trueColonies);
        }
        //sort by stored food
        else if (sortMode == 1)
        {
            for (Colony colony : trueColonies)
            {
                int smaller = 0;
                for (int lcv = 0; lcv < sortedColonies.size(); lcv++)
                {
                    if (sortedColonies.get(lcv).getFood() < colony.getFood())
                    {
                        smaller = lcv;
                    }
                }
                sortedColonies.add(smaller, colony);
            }

            //reverse the list (this will produce smallest to biggest otherwise)
            Collections.reverse(sortedColonies);
        }
        //sort by number of living ants
        else if (sortMode == 2)
        {
            for (Colony colony : trueColonies)
            {
                int smaller = 0;
                for (int lcv = 0; lcv < sortedColonies.size(); lcv++)
                {
                    if (sortedColonies.get(lcv).getNumAliveAnts() < colony.getNumAliveAnts())
                    {
                        smaller = lcv;
                    }
                }
                sortedColonies.add(smaller, colony);
            }

            //reverse the list (this will produce smallest to biggest otherwise)
            Collections.reverse(sortedColonies);
        }
        //sort by fitness
        else if (sortMode == 4)
        {
            for (Colony colony : trueColonies)
            {
                int smaller = 0;
                for (int lcv = 0; lcv < sortedColonies.size(); lcv++)
                {
                    if (sortedColonies.get(lcv).getFitness() < colony.getFitness())
                    {
                        smaller = lcv;
                    }
                }
                sortedColonies.add(smaller, colony);
            }

            //reverse the list (this will produce smallest to biggest otherwise)
            Collections.reverse(sortedColonies);
        }
    }

    public void keyPressed(KeyEvent e)
    {
        //left arrow - reduce sortMode by one (looping around from 0 to 3)
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            sortMode = (sortMode - 1) % 4;
        }

        //left arrow - increase sortMode by one (looping around from 3 to 0)
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            sortMode = (sortMode + 1) % 4;
        }
    }

    public void render()
    {

    }
}