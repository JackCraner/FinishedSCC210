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
public class StartHelp {

    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    ArrayList<text> textArray =  new ArrayList<>();
    Font textFont = new Font();
    boolean onHelp;

    public StartHelp(){
        window = new RenderWindow(new VideoMode(1000,1000), "Help");
        generateBackground();
        generateButtons();
        window.draw(backgroundSprite);
        window.display();
        onHelp = true;
        runHelp();
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
        //backgroundSprite.setPosition(0,0);

        textArray.add(new text(1,new Vector2f(180, 630), textFont, Color.YELLOW));
        textArray.get(0).setString(
                "W,S,A,D for up down left and right\n\n" +
                        "Left click to pick up\n\n" +
                        "right click to shoot\n\n" +
                        "press 1-5 to change to item in inventory \n\n" +
                        "E drops\n\n" +
                        "T opens pause menu\n\n" +
                        "ESC to exit");
        textArray.get(0).setCharacterSize(20);
        textArray.add(new text(2,new Vector2f(375, 50), textFont, Color.YELLOW));
        textArray.get(1).setString("Help");
        textArray.get(1).setCharacterSize(80);
        textArray.add(new text(3,new Vector2f(375, 580), textFont, Color.YELLOW));
        textArray.get(2).setString("Instructions:");
        textArray.get(2).setCharacterSize(30);

        textArray.add(new text(4,new Vector2f(180, 200), textFont, Color.YELLOW));
        textArray.get(3).setString(
                "The aim of the game is to defeat all 4 bosses.\n\n" +
                        "When you start the game you will be in a room \n\n " +
                        "of chests, you must destroy the chest to collect\n\n" +
                        "items to help you defeat the bosses easier \n\n" +
                        "these chest levels will be between each boss level\n\n" +
                        "during each level of the game including the \n\n" +
                        "chests small mini bosses will pop up to try and kill you \n\n" +
                        "once you are ready to defeat the boss \n\n " +
                        "you must find the ladder on the map to progress \n\n" +
                        "Good Luck"
        );
        textArray.get(3).setCharacterSize(20);


    }
    /**
     * This sets up the button/s we want
     * - Generates Buttons
     *
     */
    public void generateButtons() {
        Texture playTexture = new Texture();
        try {
            playTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "Start.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        buttonArray.add(new Button(1, new Vector2f(300, 800), 400, 200));
        buttonArray.get(0).setTexture(playTexture);

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
    public void runHelp(){
        while(onHelp)
        {
            for (Event event : window.pollEvents())
            {

            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 1)
                {
                    //System.out.println("test");
                    onHelp = false;
                    window.close();
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