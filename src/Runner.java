/**
 * Gets everything setup and running
 */
public class Runner
{
    public static void main(String args[])
    {
        //makes the frame
        Frame frame = new Frame();
        Ticker ticker = new Ticker(frame);

        frame.add(ticker);
        frame.addKeyListener(ticker.getMenu());
        frame.setVisible(true);                     //setvisible calls paintcomponent() in panel, make sure this is always last to prevent bugs

        ticker.run();
    }
}
