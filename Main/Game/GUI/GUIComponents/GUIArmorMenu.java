package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.SpecialComponents.ArmorContainer;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Factory.Entity;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

/**
 * The Armor components in the pause menu
 */
public class GUIArmorMenu extends GUIComponent<ArmorContainer>
{
    RectangleShape helmet = new RectangleShape();       //The helmet element
    RectangleShape chest = new RectangleShape();        //The chest element
    RectangleShape legs = new RectangleShape();         //The legs element

    RectangleShape[] armorPieces = {helmet,chest,legs};

    /**
     * GUIArmorMenu component
     * @param ac The current Armor of the player
     */
    public GUIArmorMenu(ArmorContainer ac)
    {
        super(ac);
        for (RectangleShape r: armorPieces )
        {
            r.setSize(new Vector2f(150,150));
        }
        helmet.setPosition(140,750);
        chest.setPosition(330,700);
        legs.setPosition(530,690);


    }

    /**
     * Updates to the current armor the player has
     */
    @Override
    public void update()
    {
        Vector2f standardRenderSize = new Vector2f(32,32);
        if (getT().getHelmet() != null)
        {

            TextureComponent t =getT().getHelmet().getComponent(TextureComponent.class);
            helmet.setTexture(TextureMap.TEXTUREMAP.get(t.textureString));
            helmet.setTextureRect(new IntRect(1 * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
        }
        else
        {
            helmet.setTexture(null);
        }
        if (getT().getChest()!= null)
        {

            TextureComponent t =getT().getChest().getComponent(TextureComponent.class);
            chest.setTexture(TextureMap.TEXTUREMAP.get(t.textureString));
            chest.setTextureRect(new IntRect(1 * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
        }
        else
        {
            chest.setTexture(null);
        }
        if (getT().getLegs() != null)
        {

            TextureComponent t = getT().getLegs().getComponent(TextureComponent.class);
            legs.setTexture(TextureMap.TEXTUREMAP.get(t.textureString));
            legs.setTextureRect(new IntRect(1 * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
        }
        else
        {
            legs.setTexture(null);
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
        for (RectangleShape r: armorPieces)
        {
            if (r.getTexture() != null)
            {
                renderTarget.draw(r);
            }
        }
    }





}
