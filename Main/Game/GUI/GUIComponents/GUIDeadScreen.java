package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Systems.GameSystem;
import Main.Game.GUI.GUIComponent;
import Main.Game.Managers.SystemManager;
import Main.Menu.Button;
import Main.Menu.Credits;
import Main.Menu.Settings;
import Main.Game.Game;
import Main.Menu.StartMenu;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import Main.Game.Managers.GUIManager;

/**
 * GUIDeadScreen class holds the component for gameover screen when the player health is zero
 */
public class GUIDeadScreen extends GUIComponent<Health>
{
    private Color ScreenBack = new Color(38, 38, 38);           //The colour of the background
    private Color ScreenFront = new Color(254, 22, 22);         //The colour of the buttons
    private RectangleShape front;                                        //The title element of the screen
    private int numButtons = 1;
    private ArrayList<Button> buttonArray =  new ArrayList<>();          //Button arraylist
    private RectangleShape[] buttons = new RectangleShape[numButtons];   //The button element of the screen
    private RectangleShape back;                                         //The back element of the screen
    private RectangleShape load;                                         //The load element of the screen
    private float PlayerHealth;                                          //The value of the current Health
    private Vector2f screenSize;
    private Vector2f buttonSize;
    private Texture t1 = new Texture();
    private Texture t2 = new Texture();
<<<<<<< HEAD
    private static GUIManager GUIMANAGER = GUIManager.getGUIinstance();
=======

>>>>>>> 1a841241a6f25263273fc14a5afd61b5739f4d44

    /**
     * GUIDeadScreen constructor. The gameover screen
     * @param s The current health of the player
     */
    public GUIDeadScreen(Health s)
    {
        super(s);
        PlayerHealth = s.getStat();
        screenSize = new Vector2f((float)(Game.WINDOWSIZE), 1000);      //length, width of bar
        buttonSize = new Vector2f((float)(Game.WINDOWSIZE - 400), 200);
        back = new RectangleShape(screenSize);

        try
        {
<<<<<<< HEAD
=======

>>>>>>> 1a841241a6f25263273fc14a5afd61b5739f4d44
            t1.loadFromFile(Paths.get("Assets" + File.separator + "Menu" + File.separator+ "quit.png"));
            t2.loadFromFile(Paths.get("Assets" + File.separator + "Screens" + File.separator+ "GameOver.jpg"));

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
<<<<<<< HEAD
        buttonArray.add(new Button(1, new Vector2f(200, 400),600, 200));
=======
        buttonArray.add(new Button(1, new Vector2f(500, 500),600, 200));
>>>>>>> 1a841241a6f25263273fc14a5afd61b5739f4d44
        buttonArray.get(0).setTexture(t1);
        back.setTexture(t2);
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
     * Updates the Dead Screen (Gameover) shown on the GUI
     * Check if a button is pressed
     */
    @Override
    public void update()
    {
        if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
            if (checkButtons() == 1) {
                Game.getGame().getWindow().close();

            }
        }
<<<<<<< HEAD
=======

>>>>>>> 1a841241a6f25263273fc14a5afd61b5739f4d44
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

        renderTarget.draw(back,renderStates);

        for (Button b: buttonArray) {
            renderTarget.draw(b);
        }
        update();

    }
}

