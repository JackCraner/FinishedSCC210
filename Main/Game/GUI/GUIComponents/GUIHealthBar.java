package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

/**
 * The HealthBar GUI Component
 */
public class GUIHealthBar extends GUIComponent<Health>
{
    private Color HPBack = new Color(155, 26, 10);    //The colour of the current Health
    private Color HPFront = new Color(254, 22, 22);     //The colour of the Health Lost
    private RectangleShape front;                                //The front element of the Health bar
    private RectangleShape back;                                 //The back element of the Health bar
    private Vector2f healthOrbSize;                              //The vector2f of the Health bar
    private float totalHealth = 100;                             //The value of the total Health
    private float currentHealth = 100;                           //The value of the current Health
    private float topLeftY;
    private Text t = new Text();
    private Font f = new Font();

    /**
     * The HealthBar GUI component
     * @param s The current health of the player
     */
    public GUIHealthBar(Health s)
    {
        super(s);
        try
        {
            f.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        t = new Text("HI", f, 20);
        totalHealth = s.getMaxHealth();
        currentHealth = s.getMaxHealth();
        healthOrbSize = new Vector2f((float)(Game.WINDOWSIZE-870), 130); //length, width of bar
        front = new RectangleShape(healthOrbSize);
        back = new RectangleShape(healthOrbSize);
        front.setPosition(50, Game.WINDOWSIZE - healthOrbSize.y - 30);
        back.setPosition(50, Game.WINDOWSIZE- healthOrbSize.y - 30);
        t.setPosition(100,  Game.WINDOWSIZE - 110);
        topLeftY = front.getPosition().y;
        front.setFillColor(HPFront);
        back.setFillColor(HPBack);
    }

    /**
     * Updates the current health value of the player
     */
    @Override
    public void update()
    {
        totalHealth = getT().getMaxHealth();
        currentHealth = getT().getStat();
        t.setString(String.valueOf((int)currentHealth));
        front.setSize(new Vector2f(healthOrbSize.x,healthOrbSize.y* (currentHealth/totalHealth)));
        front.setPosition(new Vector2f(front.getPosition().x,topLeftY + (healthOrbSize.y *(1-(currentHealth/totalHealth)))));
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
        renderTarget.draw(front,renderStates);
        renderTarget.draw(t);
    }
}
