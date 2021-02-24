package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.GUI.GUIComponent;
import Main.Menu.Button;
import Main.Menu.Settings;
import Main.Menu.StartMenu;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import Main.Menu.EndCredits;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * GUIFinScreen class holds the component for The End screen when the player has completed the levels
 */
public class GUIFinScreen extends GUIComponent<Level>
{
    private Color ScreenBack = new Color(51, 51, 51);      //The colour of the current Health
    private Color ScreenFront = new Color(10, 153, 249);   //The colour of the Health Lost
    private ArrayList<Button> buttonArray =  new ArrayList<>();     //Button arraylist
    private RectangleShape[] buttons = new RectangleShape[2];       //The button element of the screen
    private RectangleShape title;                                   //The title element of the screen
    private RectangleShape back;                                    //The back element of the screen
    private RectangleShape load;                                    //The load element of the screen
    private float PlayerLevel;                                      //The value of the current Health
    private Vector2f buttonSize;                                    //Vector2f variables that hold the size of the element
    private Vector2f screenSize;
    private Texture t1 = new Texture();                             //Element textures
    private Texture t2 = new Texture();
    private Texture t3 = new Texture();
     /**
     * GUIFinScreen constructor. The end title screen
     * @param s Is the current level
     */
    public GUIFinScreen(Level s)
    {
        super(s);
        //PlayerLevel = s.getCurrentLevel();
        screenSize = new Vector2f((float)(Game.WINDOWSIZE), 1000); //length, width of bar
        buttonSize = new Vector2f((float)(Game.WINDOWSIZE - 400), 200);
        back = new RectangleShape(screenSize);
        try
        {
            t1.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "Credits.png"));
            t2.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "quit.png"));
            t3.loadFromFile(Paths.get("Assets" + File.separator + "Screens" + File.separator+ "EndScreen.jpg"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        buttonArray.add(new Button(1, new Vector2f(480, 535),600, 200));
        buttonArray.get(0).setTexture(t1);
        buttonArray.add(new Button(2, new Vector2f(460, 665), 600, 200));
        buttonArray.get(1).setTexture(t2);
        back.setTexture(t3);
        back.setPosition(0, 0);
    }

    /**
     * CheckButtons()
     * This method checks whether a button has been pressed
     * @return a Id of the button pressed
     */
    public int checkButtons()
    {
        for (Button b: buttonArray)
        {
            if (b.isHovered(Mouse.getPosition(Game.getGame().getWindow())))
            {
                return b.getId();
            }
        }
        return 0;
    }

    /**
     * Updates the Fin Screen shown on the GUI
     */
    @Override
    public void update()
    {
        //title.setPosition(220, Game.WINDOWSIZE - buttonSize.y - 500);
        if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
            if (checkButtons() == 1) {
                Game.getGame().getWindow().close();
                EndCredits endcreds = new EndCredits();
            }
        }
        if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
            if (checkButtons() == 2) {
                Game.getGame().getWindow().close();
            }
        }

        back.setPosition(0, 0);
    }

     /**
     * Draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        update();
        renderTarget.draw(back,renderStates);
        for (Button b: buttonArray) {
            renderTarget.draw(b);
        }
    }
}