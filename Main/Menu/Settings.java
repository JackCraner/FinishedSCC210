package Main.Menu;


import Main.Game.ECS.Systems.SoundGameSystem;
import org.jsfml.audio.Listener;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
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
 * - slider checks/loop
 */
public class Settings {

    RenderWindow window;
    ArrayList<Button> buttonArray =  new ArrayList<>();
    ArrayList<Slider> sliderArray =  new ArrayList<>();
    ArrayList<text> textArray =  new ArrayList<>();
    Sprite backgroundSprite = new Sprite();
    Font textFont = new Font();
    boolean onSett;
    int volcount = 10;

    public Settings(){
        window = new RenderWindow(new VideoMode(1000,1000), "Settings");
        //this.window = window;
        generateBackground();
        generateButtons();
        generateSlider();

        window.draw(backgroundSprite);
        window.display();
        onSett = true;
        volcount = 10;
        runSett();
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

        textArray.add(new text(1,new Vector2f(350, 300), textFont, Color.YELLOW));
        textArray.get(0).setString("Background Sound");
        textArray.get(0).setCharacterSize(25);
        textArray.add(new text(2,new Vector2f(300, 100), textFont, Color.YELLOW));
        textArray.get(1).setString("Settings");
        textArray.get(1).setCharacterSize(80);
        textArray.add(new text(3,new Vector2f(300, 400), textFont, Color.YELLOW));
        textArray.get(2).setString("0 1 2 3 4 5 6 7 8 9");
        textArray.get(2).setCharacterSize(35);
        textArray.add(new text(4,new Vector2f(300, 450), textFont, Color.YELLOW));
        textArray.get(3).setString("Hover over slider and use \n\n 0-9 to change volume.");
        textArray.get(3).setCharacterSize(25);
    }
    /**
     * This sets up the button/s we want
     * - Generates Buttons
     *
     */
    public void generateButtons() {
        Texture backTexture = new Texture();
        try {
            backTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "back.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        buttonArray.add(new Button(1, new Vector2f(300, 800), 400, 200));
        buttonArray.get(0).setTexture(backTexture);
    }
    /**
     * This sets up the slider/s
     * - Generates slider
     *
     */
    public void generateSlider(){
        Texture fullTexture = new Texture();
        try {
            fullTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "10.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        sliderArray.add(new Slider(1, new Vector2f(300, 350), 400, 50));
        sliderArray.get(0).setTexture(fullTexture);
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
     * This checks if the slider is being hovered over
     * - slider check
     *
     */
    public int checkSlider()
    {

        for (Slider s: sliderArray)
        {

            if (s.isHovered(Mouse.getPosition(window)))
            {
                return s.getId();
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
     * This is our run settings loop
     * - checks which the button/slider is
     *  pressed and from there runs the
     *  page we want or the game
     *
     * then finally displays everything
     */
    public void runSett(){

        Texture zeroTexture = new Texture();
        Texture oneTexture = new Texture();
        Texture twoTexture = new Texture();
        Texture threeTexture = new Texture();
        Texture fourTexture = new Texture();
        Texture fiveTexture = new Texture();
        Texture sixTexture = new Texture();
        Texture sevenTexture = new Texture();
        Texture eightTexture = new Texture();
        Texture fullTexture = new Texture();
        try {
            zeroTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "0.png"));
            oneTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "1.png"));
            twoTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "2.png"));
            threeTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "3.png"));
            fourTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "4.png"));
            fiveTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "5.png"));
            sixTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "6.png"));
            sevenTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "7.png"));
            eightTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "8.png"));
            fullTexture.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator + "10.png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        while(onSett)
        {
            for (Event event : window.pollEvents())
            {
            }
            if (Mouse.isButtonPressed(Mouse.Button.LEFT))
            {
                if (checkButtons() == 1)
                {
                    //System.out.println("test");
                    onSett = false;
                    window.close();
                    RenderWindow window = new RenderWindow(new VideoMode(1000,1000),"CoreControl");
                    StartMenu newStartMenu = new StartMenu(window);
                }
            }

            if (Keyboard.isKeyPressed(Keyboard.Key.NUM9)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(fullTexture);
                    Listener.setGlobalVolume(29);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM8)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(eightTexture);
                    Listener.setGlobalVolume(25);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM7)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(sevenTexture);
                    Listener.setGlobalVolume(22);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM6)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(sixTexture);
                    Listener.setGlobalVolume(18);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM5)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(fiveTexture);
                    Listener.setGlobalVolume(15);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM4)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(fourTexture);
                    Listener.setGlobalVolume(12);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM3)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(threeTexture);
                    Listener.setGlobalVolume(10);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM2)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(twoTexture);
                    Listener.setGlobalVolume(6);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM1)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(oneTexture);
                    Listener.setGlobalVolume(2);
                }
            }
            if (Keyboard.isKeyPressed(Keyboard.Key.NUM0)){
                if (checkSlider() == 1) {
                    sliderArray.get(0).setTexture(zeroTexture);
                    Listener.setGlobalVolume(0);
                }
            }

            window.clear();
            window.draw(backgroundSprite);
            for (Button b: buttonArray)
            {
                window.draw(b);
            }
            for (Slider s: sliderArray)
            {
                window.draw(s);
            }
            for (text t: textArray)
            {
                window.draw(t);
            }
            window.display();
        }
    }
}

