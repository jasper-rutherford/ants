import java.util.ArrayList;

public class ColonyTab extends Tab
{
    private ArrayList<Colony> trueColonies;
    private ArrayList<Colony> sortedColonies;
    private int sortMode = 0;

    public ColonyTab(ArrayList<Colony> colonies)
    {
        trueColonies = colonies;
        tick();
    }

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
        }
    }

    public void render()
    {

    }

    public void keyPressed()
    {

    }
}