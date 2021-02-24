package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Factory.Entity;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import Main.Menu.Button;
import Main.Menu.Settings;
import Main.Menu.StartMenu;
import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The pause Menu
 */
public class GUIPauseMenu extends GUIComponent<Backpack>
{
    RectangleShape backgroundMenu = new RectangleShape();       //The background element
    RectangleShape playerFrame = new RectangleShape();          //The player frame element
    RectangleShape mainHand = new RectangleShape();             //The item player currently uses


    private ArrayList<Button> buttonArray =  new ArrayList<>();
    /**
     * The pause Menu GUI Component
     * @param b The current backpack
     */
    public GUIPauseMenu(Backpack b)
    {
        super(b);
        backgroundMenu.setPosition(100,100);
        backgroundMenu.setFillColor(new Color(150,150,150,200));
        backgroundMenu.setSize(new Vector2f(800,800));
        Texture t = new Texture();
        try
        {
            t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "StatMenu2.png"));
        }
        catch(Exception e)
        {

        }
        backgroundMenu.setTexture(t);
        playerFrame.setPosition(590,300);
        playerFrame.setSize(new Vector2f(300,300));
        playerFrame.setTexture(TextureMap.TEXTUREMAP.get(Entity.PLAYER.textureString));
        playerFrame.setTextureRect(new IntRect(32, 0,32,32));
        mainHand.setPosition(540,530);
        mainHand.setSize(new Vector2f(200,200));



    }

    /**
     * Updates the pause menu
     */
    @Override
    public void update()
    {

        if (getT().getObjectsINBACKPACK().size() > 0 && getT().getBackPackSelect() != null)
        {
            mainHand.setTexture(TextureMap.TEXTUREMAP.get(getT().getBackPackSelect().getComponent(TextureComponent.class).textureString));
            mainHand.setRotation(-100);
            //mainHand.setTextureRect(new IntRect(32, 0,32,32));
        }
        else
        {
            mainHand.setTexture(null);
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
        renderTarget.draw(backgroundMenu);
        renderTarget.draw(playerFrame);
        if (mainHand.getTexture() != null)
        {
            renderTarget.draw(mainHand);
        }

        for(Button b: buttonArray)
        {
            renderTarget.draw(b);
        }

    }
}
