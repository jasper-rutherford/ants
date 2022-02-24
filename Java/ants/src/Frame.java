import javax.swing.*;

/**
 * Listens for key inputs and passes them on to the ticker.
 */
public class Frame extends JFrame
{
    public Frame()
    {
        //initializes the frame
        setTitle("Ants");
        setSize(960, 960);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
    }
}
