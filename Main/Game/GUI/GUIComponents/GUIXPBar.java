package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;

/**
 * The XPBar GUI Component
 */
public class GUIXPBar extends GUIComponent<Level>
{
    private Color XPColour = new Color(12, 254, 12);   //The colour of the XP gained
    private Color XPEmpty1 = new Color(57, 87, 28); //The colour of the empty XP Slot 1
    private Color XPEmpty2 = new Color(42, 63, 22);  //The colour of the empty XP Slot 2
    private RectangleShape[] XPSlot;                            //The front element of the XP bar

    /**
     * The XPBar GUI component
     * @param s The current XP of the player
     */
    public GUIXPBar(Level s)
    {
        super(s);
        generateXPSlots((int)getT().getCurrentLevel() * 5); //Generate XP slots depending on the current level

    }

    /**
     * Updates the current XP value of the player
     */
    @Override
    public void update()
    {
        float currentXP = getT().getCurrentXP();
        if (currentXP == 0)
        {
           generateXPSlots((int)getT().getCurrentLevel() * 5);
        }
        else
        {
            for (int i = 0; i< currentXP;i++)
            {
                XPSlot[i].setFillColor(XPColour);
            }
        }

    }

    /**
     * GenerateXPSlots()
     * This method generates slots for XPbar GUI Component
     * @param size
     */
    public void generateXPSlots(int size)
    {
        XPSlot = new RectangleShape[size];
        float length = 600/size;
        float height = 25;
        Vector2f slotSize = new Vector2f(length,height);
        for (int i = 0; i < size; i++){
            XPSlot[i] = new RectangleShape(slotSize);
            if (i % 2 == 0){
                XPSlot[i].setFillColor(XPEmpty1);
            }
            else {
                XPSlot[i].setFillColor(XPEmpty2);
            }
        }
        for (int i = 0; i < size; i++){
            XPSlot[i].setPosition(200+((int)length*i), Game.WINDOWSIZE -height - 135);
        }
    }

    /**
     * Draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for (int i = 0; i < XPSlot.length; i++){
            renderTarget.draw(XPSlot[i],renderStates);
        }
    }
}