package Main.Menu;


import Main.Game.Game;
import Main.Menu.Button;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.Event;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
/**
 * This class handles
 * - background/window generation
 * - button generation
 * - button checks/loops
 *
 */
public class StartMenu {

    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    RectangleShape logo = new RectangleShape();
    ArrayList<text> textArray =  new ArrayList<>();
    Font textFont = new Font();
    boolean onMenu;

    public StartMenu(RenderWindow window)
    { 
        this.window = window;
        generateButtons();
        generateBackground();
        onMenu = true;
        runMenu();
    }
    /**
     * This sets up the background of our main menu
     * - Generates Background
     *
     */
    public void generateBackground()
    {
        Texture backgroundTexture = new Texture();
        try
        {
            textFont.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "HANGTHEDJ.ttf"));
            backgroundTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "MenuBackground.jpg"));
            Texture t = new Texture();
            t.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Pixel_Earth.png"));
            logo.setTexture(t);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        backgroundSprite.setTexture(backgroundTexture);
        backgroundSprite.setScale(1,1f);
        logo.setSize(new Vector2f(400,400));
        logo.setPosition(new Vector2f(310,350));
        textArray.add(new text(1,new Vector2f(150, 500), textFont, Color.YELLOW));

    }
    /**
     * This sets up the buttons needed to redirect
     * - Generates Buttons
     *
     */
    public void generateButtons()
    {
        Texture playTexture = new Texture();
        Texture quitTexture = new Texture();
        Texture settTexture = new Texture();
        Texture helpTexture = new Texture();
        Texture conTexture = new Texture();
        try
        {
            playTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "start.png"));
            quitTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "quit.png"));
            settTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Settings.png"));
            helpTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Help.png"));
            conTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "Continue.png"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        buttonArray.add(new Button(1,new Vector2f(350, 50), 300, 100));
        buttonArray.get(0).setTexture(playTexture);

        buttonArray.add(new Button(2,new Vector2f(350,875),300,100));
        buttonArray.get(1).setTexture(quitTexture);

        buttonArray.add(new Button(3,new Vector2f(525,800),300,100));
        buttonArray.get(2).setTexture(helpTexture);

        buttonArray.add(new Button(4,new Vector2f(350, 115), 300, 100));
        buttonArray.get(3).setTexture(conTexture);

        buttonArray.add(new Button(5,new Vector2f(150, 800), 300, 100));
        buttonArray.get(4).setTexture(settTexture);
    }
    /**
     * This checks if the button is being hovered over
     * - Buttons check
     *
     */
    public int checkButtons()
    {
        for (Button b: buttonArray)
        {
            if (b.isHovered(Mouse.getPosition(window)))
            {
                return b.getId();
            }
        }
        return 0;
    }
    /**
     * This checks if the text is being hovered over
     * - text check
     *
     */
    public int checkText()
    {

        for (text t: textArray)
        {
            return t.getId();
        }
        return 0;
    }
    /**
     * This is our run menu loop
     * - checks if the button is
     * pressed and from there runs the
     *page we want or the game
     *
     * then finally displays everything
     */
    public void runMenu()
    {
        while(onMenu)
        {


            for (Event event : window.pollEvents())
            {
            }


            if (Keyboard.isKeyPressed(Keyboard.Key.ESCAPE))
            {
                onMenu = false;
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 1)
                {
                    onMenu = false;
                    window.close();
                    StartHelp sh = new StartHelp();

                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 2)
                {
                    window.close();
                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 3)
                {
                    window.close();
                    Help help = new Help();
                    onMenu = false;
                }
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT)){
                if (checkButtons() == 4)
                {
                    window.close();
                    //add continue here

                }
            }

            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 5)
                {
                    window.close();
                    Settings sett = new Settings();
                    onMenu = false;
                }
            }
            window.clear();
            window.draw(backgroundSprite);
            for (text t: textArray)
            {
                window.draw(t);
            }
            for (Button b: buttonArray)
            {
                window.draw(b);
            }
            window.draw(logo);
            window.display();
        }


    }
}
