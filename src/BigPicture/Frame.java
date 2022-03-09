package BigPicture;

import javax.swing.*;

/**
 * Listens for key inputs and passes them on to the ticker.
 */
public class Frame extends JFrame
{
    public Frame()
    {
        //initializes the frame
        setTitle("Simulation/Ants");
        setSize(980, 670 + 39); //TODO: the width isn't quite right.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
