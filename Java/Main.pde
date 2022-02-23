/**
ants

Main File
Sets everything up and runs everything

Written by Jasper Rutherford
*/

Ticker ticker;

void setup()
{
    size(980, 670);
    surface.setTitle("ants");
    
    ticker = new Ticker();
}

void draw() 
{
    ticker.tick();
    ticker.render();
}   

// key controls
void keyPressed() 
{
    ticker.keyPressed();

    //reset
    if (key == 'r' || key == 'R') 
    {
        setup();
    }
}

void keyReleased()
{
    ticker.keyReleased();
}
