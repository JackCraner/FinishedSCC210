package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

/**
 * BossHealthBar GUI Component in the boss room
 */
public class GUIBossHealthBar extends GUIComponent<Health>
{

    private Color HPBack = new Color(1, 1, 1);         //The colour of the current Health
    private Color HPFront = new Color(254, 22, 22);    //The colour of the Health Lost
    private RectangleShape front;                               //The front element of the Health Orb
    private RectangleShape back;                                //The back element of the Health Orb
    private Vector2f healthOrbSize;                             //The vector2f of the Health Orb
    private float totalHealth = 100;                            //The value of the total Health
    private float currentHealth = 100;                          //The value of the current Health
    private float topLeftY;

    /**
     * The BossHealthBar GUI Component
     * @param s The health of the boss
     */
    public GUIBossHealthBar(Health s)
    {
        super(s);
        totalHealth = s.getMaxHealth();
        currentHealth = s.getMaxHealth();
        healthOrbSize = new Vector2f(800,100); //length, width of bar
        front = new RectangleShape(healthOrbSize);
        back = new RectangleShape(healthOrbSize);
        front.setPosition(100, 50);
        back.setPosition(100, 50);
        topLeftY = front.getPosition().y;
        front.setFillColor(HPFront);
        back.setFillColor(HPBack);
        currentHealth = getT().getStat();
        if (s.getStat() > 0)
        {
            front.setSize(new Vector2f(healthOrbSize.x* (currentHealth/totalHealth),healthOrbSize.y));
        }
        else
        {
            front.setSize(new Vector2f(0,0));
        }
    }

    /**
     * Updates the current health value of the player
     */
    @Override
    public void update()
    {
        currentHealth = getT().getStat();
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
    }
}
