import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Gets everything setup and running
 */
public class Runner
{
    public static void main(String args[])
    {
        int max = 32;
        Set<Double> degs = new HashSet<>();
        for (int top = 0; top < max; top++)
        {
            for (int bot = 1; bot < max; bot++)
            {
                degs.add((int)(100 * Math.toDegrees(Math.atan(top/bot))) / 100.0);
            }
        }

        ArrayList<Double> sorted = new ArrayList<>();
        sorted.addAll(degs);
        Collections.sort(sorted);

        for (Double deg : sorted)
        {
            System.out.println(deg);
        }
        //makes the frame
        Frame frame = new Frame();
        Ticker ticker = new Ticker(frame);

        frame.add(ticker);
        frame.addKeyListener(ticker.getMenu());
        frame.setVisible(true);                     //setvisible calls paintcomponent() in panel, make sure this is always last to prevent bugs

        ticker.run();
    }
}
