package Main.Menu;

import Main.Menu.Button;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
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
public class Credits {

    RenderWindow window;
    Sprite backgroundSprite = new Sprite();

    ArrayList<text> textArray =  new ArrayList<>();
    ArrayList<Button> buttonArray =  new ArrayList<>();
    boolean onCreds;
    Font textFont = new Font();
    public Credits(){
        window = new RenderWindow(new VideoMode(1000,1000), "Credits");
        generateBackground();
        generateButtons();
        window.draw(backgroundSprite);
        window.display();
        onCreds = true;
        runcreds();
    }
    /**
     * This sets up the background of our main menu
     * - Generates Background
     * - adds in text on said background
     */
    public void generateBackground()
    {
        Texture backgroundTexture = new Texture();
        try
        {
            textFont.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "HANGTHEDJ.ttf"));
            backgroundTexture.loadFromFile(Paths.get("Assets"+ File.separator + "Menu"+ File.separator + "MenuBackground.jpg"));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        backgroundSprite.setTexture(backgroundTexture);
        backgroundSprite.setScale(1,1f);
        textArray.add(new text(1,new Vector2f(375, 350), textFont, Color.YELLOW));
        textArray.get(0).setString(
                    "Group 3 Fri \n\n" +
                    "Jack Craner \n\n" +
                    "Ethan Chong \n\n" +
                    "Jess Forknall-Lane\n\n" +
                    "James Garnett\n\n" +
                    "Benjamin Cowley\n\n" +
                    "\n" +
                    "Released: February 2021");
        textArray.get(0).setCharacterSize(25);
        textArray.add(new text(2,new Vector2f(325, 100), textFont, Color.YELLOW));
        textArray.get(1).setString("Credits");
        textArray.get(1).setCharacterSize(80);
        textArray.add(new text(3,new Vector2f(375, 300), textFont, Color.YELLOW));
        textArray.get(2).setString("Developed By: ");
        textArray.get(2).setCharacterSize(30);

    }
    /**
     * This sets up the button/s we want
     * - Generates Buttons
     *
     */
    public void generateButtons() {
        Texture quitTexture = new Texture();
        try {
            quitTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "back.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
        buttonArray.add(new Button(1, new Vector2f(300, 800), 400, 200));
        buttonArray.get(0).setTexture(quitTexture);

    }
    /**
     * This checks if the button is being hovered over
     * - button check
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
     * This is our run help loop
     * - checks if the button is
     * pressed and from there runs the
     * page we want or the game
     *
     * then finally displays everything
     */
    public void runcreds(){
        while(onCreds)
        {
            for (Event event : window.pollEvents())
            {
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 1)
                {
                    onCreds = false;
                    window.close();
                    RenderWindow window = new RenderWindow(new VideoMode(1000,1000),"CoreControl");
                    StartMenu newStartMenu = new StartMenu(window);
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
            window.display();
        }
    }


}

