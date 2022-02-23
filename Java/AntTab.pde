public class AntTab extends Tab
{
    private ArrayList<Ant> ants;

    private Ant oldest;         //the oldest ant
    private Ant bootlicker;     //the ant that has contributed the most to the stash
    private Ant lazy;           //the ant that has contributed the least to the stash
    private int numDead;        //the amount of ants that are dead

    public AntTab(ArrayList<Ant> ants)
    {
        this.ants = ants;
    }

    //recalculate the ant statistics
    public void tick()
    {
        oldest = ants.get(0);
        bootlicker = oldest;
        lazy = oldest;
        numDead = 0;

        for (Ant ant : ants)
        {
            //find oldest ant
            if (ant.getAge() > oldest.getAge())
            {
                oldest = ant;
            }

            //find bootlicker ant
            if (ant.getNetStashContribution() > bootlicker.getNetStashContribution())
            {
                bootlicker = ant;
            }

            //find lazy ant
            else if (ant.getNetStashContribution() < lazy.getNetStashContribution())
            {
                lazy = ant;
            }

            //calculate how many ants are dead
            if (ant.health <= 0)
            {
                numDead++;
            }
        }
    }

    public void render()
    {

    }
}