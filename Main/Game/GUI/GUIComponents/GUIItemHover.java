package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Fonts;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

/**
 * The item hover GUI component over dropped items
 */
public class GUIItemHover extends GUIComponent<Backpack>
{
    private Color backColor = new Color(203, 203, 203);     //The colour of the item hover
    private RectangleShape back;                                     //The back element
    private RectangleShape picture;                                  //The front of the hover
    private Text name;
    private Text subtext;
    private boolean active;
    private Texture background = new Texture();

    /**
     * ItemHover GUI component
     * @param backpack The current items stored
     */
    public GUIItemHover(Backpack backpack) {
        super(backpack);
        try
        {
            background.loadFromFile(Paths.get("Assets" + File.separator + "UI" + File.separator+ "ItemHover.png"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        name =  new Text("HI", Fonts.MAIN.f, 20);
        name.setPosition(new Vector2f(Game.WINDOWSIZE-170, Game.WINDOWSIZE/2-72));
        subtext =  new Text("HI", Fonts.MAIN.f, 15);
        subtext.setPosition(new Vector2f(Game.WINDOWSIZE-170, Game.WINDOWSIZE/2-20));
        picture = new RectangleShape(new Vector2f(128,128));
        back = new RectangleShape(new Vector2f(200,300));
        back.setPosition(Game.WINDOWSIZE-200, Game.WINDOWSIZE/2-100);
        picture.setPosition(Game.WINDOWSIZE - 164, Game.WINDOWSIZE/2 + 45);
        back.setTexture(background);
    }

    /**
     * Updates the Item Hover component over dropped items
     */
    @Override
    public void update()
    {
        if (getT().getCurrentHover() != null)
        {
            active = true;
            String substring = "";
            GameObject hover = getT().getCurrentHover();
            name.setColor(hover.getComponent(Pickup.class).getRarity().rarityColor);
            String temp = hover.getName();
            if (temp.contains(" ")){
                String[] nameParts = temp.split(" ");
                name.setString(nameParts[0] + "\n" + nameParts[1]);
            }
            else {
                name.setString(temp);
            }

            if (BitMasks.checkIfContains(hover.getBitmask(), Damage.class))
            {
                substring +=("Damage: " + (hover.getComponent(Damage.class).getDamage())+ "\n");
            }
            if (BitMasks.checkIfContains(hover.getBitmask(), Armor.class))
            {
                substring +=("Armor: " + (hover.getComponent(Armor.class).getArmor() + "\n"));
            }
            if (BitMasks.checkIfContains(hover.getBitmask(), GivenEffect.class))
            {
                substring += ("Effect: " + 100*hover.getComponent(GivenEffect.class).getEffectToGiven().getPercentageModifier() + "%  \n" + (hover.getComponent(GivenEffect.class).getEffectToGiven().getType().getSimpleName()));
            }

            subtext.setString(substring);
            picture.setTexture(TextureMap.TEXTUREMAP.get(hover.getComponent(TextureComponent.class).textureString));
            picture.setTextureRect(new IntRect(0, 0,32,32));

        }
        else
        {
           active = false;
        }

    }

    /**
     * ConstructMessage() method
     * @param hover Current item hover
     * @return String s
     */
    public String constructMessage(GameObject hover)
    {
        String s = "";


        return s;
    }

    /**
     * Draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        if (active)
        {
            renderTarget.draw(back);
            renderTarget.draw(picture);
            renderTarget.draw(name);
            renderTarget.draw(subtext);
        }

    }
}
