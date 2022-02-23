public class Simulation
{
  private Map map;    

  private int numColonies = 8;
  private ArrayList<Colony> colonies;

  private int numAnts;
  private ArrayList<Ant> ants;

  private int generation;
  private int ticksPerGeneration;

  public Simulation(int ticksPerGeneration, int numAnts)
  {
    this.ticksPerGeneration = ticksPerGeneration;
    this.numAnts = numAnts;
    this.generation = 0;

    map = new Map();
    colony = new ArrayList<Colony>();
    ants = new ArrayList<Ant>();

    generateColonies();
    nextGeneration();
  }

  /**
   *   generates all the colonies. Does not generate any ants. 
   *   Used purely to setup where the colonies will go/what tiles go with what colony. 
   */
  private void generateColonies()
  {
    colonies = new ArrayList<Colony>();
    //corners of all the stashes in a 13x13 grid
    Vec2 corners[] = {
      new Vec2(8, 11), 
      new Vec2(11, 8), 
      new Vec2(11, 4), 
      new Vec2(8, 1), 
      new Vec2(4, 1), 
      new Vec2(1, 4), 
      new Vec2(1, 8), 
      new Vec2(4, 11)};

    int stashWidth = map.getMapWidth() / 13;
    for (int lcv = 0; lcv < numColonies; lcv++)
    {
      colonies.add(new Colony(lcv, corners[lcv].times(stashWidth), stashWidth, map.getTiles()));
    }
  }

  /**
   *   generates the initial batch of ants. 
   *   Expected to be used one time by nextGeneration()
   */
  private void generateAnts()
  {
    for (int lcv = 0; lcv < numAnts; lcv++)
    {
      ants.add(new Ant());
    }
  }

  //advances to the next generation of ants
  public void nextGeneration()
  {
    generation++;

    //if no ants exist, generate the initial batch.
    if (ants.size() == 0)
    {
      generateAnts();
    }
    //if ants exist, cull and repopulate according to fitness.
    else
    {
      //cull colonies until half remain
      ArrayList<Colony> culled = new ArrayList<Colony>();
      while (colonies.size() > numColonies / 2)
      {
        //find the worst colony (lowest fitness)
        Colony worstColony = colonies.get(0);
        int lowestFitness = worstColony.getFitness();
        for (int lcv = 0; lcv < numColonies; lcv++)
        {
          Colony colony = colonies.get(lcv);
          if (colony.getFitness() < lowestFitness)
          {
            lowestFitness = colony.getFitness();
            worstColony = colony;
          }
        }

        //cull the worst colony
        culled.add(worstColony);
        colonies.remove(worstColony);
        worstColony.cull(ants);
      }

      //add the culled colonies back into the full list after the ants have been culled
      colonies.addAll(culled);

      //repopulate global ant population
      ArrayList<Ant> newAnts = new ArrayList<Ant>();
      for (Ant ant : ants)
      {
        //generate a random index that isn't the index for the current ant
        int randomIndex = (int)(Math.random() * (ants.size() - 1));
        if (randomIndex >= ants.indexOf(ant))
        {
          randomIndex++;
        }

        //generate a new ant
        newAnts.add(new Ant(ant, ants.get(randomIndex)));

        ant.setAge(ant.getAge() + 1);
      }

      //add all new ants to the global population
      ants.addAll(newAnts);
    }

    //redistribute ants among colonies

    //clear all colony affiliations
    for (Colony colony : colonies)
    {
      colony.clearAnts();
    }

    //shuffle ants and assign to new colonies
    Collections.shuffle(ants);
    for (int lcv = 0; lcv < ants.size(); lcv++)
    {
      colonies.get(lcv % numColonies).addAnt(ants.get(lcv));
    }        

    //every colony resets all of their ants relative to their new colony
    for (Colony colony : colonies)
    {
      colony.resetAnts();
    }
  }

  //updates all ants
  public void tick()
  {
    for (Ant ant : ants)
    {
      ant.update(); 
    }
  }
}
