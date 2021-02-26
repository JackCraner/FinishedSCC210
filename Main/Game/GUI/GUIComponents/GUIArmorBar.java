package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

/**
 * The ArmorBar GUI Component showing the current value of the player's armor
 */
public class GUIArmorBar extends GUIComponent<Armor>
{
    private Color armorBackColor = new Color(10, 153, 249);    //The colour of the current Armor status
    private Color armorHaveColor = new Color(3, 3, 117);       //The colour of the damage to Armor
    private RectangleShape front;                                       //The front element of the ArmorBar
    private RectangleShape back;                                        //The back element of the ArmorBar
    private Vector2f armorOrbSize;                                      //The vector2f of the ArmorBar
    private float topLeftY;
    private float currentArmor;
    private Text t = new Text();
    private Font f = new Font();

    /**
     * The ArmorBar GUI component
     * @param s The current player Armor value
     */
    public GUIArmorBar(Armor s)
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
        currentArmor = s.getArmorValue();
        armorOrbSize = new Vector2f((float)(Game.WINDOWSIZE-870), 130); //length, width of bar
        front = new RectangleShape(armorOrbSize);
        back = new RectangleShape(armorOrbSize);
        front.setPosition(820, Game.WINDOWSIZE - armorOrbSize.y - 30);
        back.setPosition(820, Game.WINDOWSIZE- armorOrbSize.y - 30);
        t.setPosition(870,  Game.WINDOWSIZE - 110);
        topLeftY = front.getPosition().y;
        front.setFillColor(armorHaveColor);
        back.setFillColor(armorBackColor);
    }

    /**
     * Updates the current Armor value of the player
     */
    @Override
    public void update()
    {
        currentArmor = getT().getArmorValue();
        t.setString(String.valueOf((int) currentArmor));
        System.out.println(armorOrbSize.y* ( currentArmor/100));
        front.setSize(new Vector2f(armorOrbSize.x, armorOrbSize.y* ( currentArmor/100)));
        front.setPosition(new Vector2f(front.getPosition().x,topLeftY + (armorOrbSize.y *(1-( currentArmor/100)))));
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
