import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu implements KeyListener
{
    private Tab[] tabs;
    private int currentTab;

    //ticker passed so that the settings menu can set tps and ticker.sim.setup()
    public Menu(Ticker ticker)
    {
        tabs = new Tab[3];
        tabs[0] = new AntTab();
        tabs[1] = new ColonyTab();
        tabs[2] = new SettingsTab(ticker);

        //current tab defaults to settings tab.
        currentTab = 2;
    }

    public void tick()
    {
    }

    public void render(Graphics g)
    {
        renderTabLabels();

        tabs[currentTab].render(g);
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
