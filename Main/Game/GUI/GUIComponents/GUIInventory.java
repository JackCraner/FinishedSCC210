package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.TextureMap;
import Main.Game.GUI.GUIComponent;
import Main.Game.Game;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

/**
 * The Inventory GUI component
 */
public class GUIInventory extends GUIComponent<Backpack>
{
    private Color slotHighlight = new Color(195,0,0,90);                //Colour of the item selected
    private int numSlots = 6;                                                       //The number of slots in the inventory
    private GameObject[] inventoryGameObjects = new GameObject[numSlots];           //The item objects
    private RectangleShape[] inventoryRectangles = new RectangleShape[numSlots];    //The inventory slots
    private RectangleShape[] back = new RectangleShape[numSlots];                   //The back element
    private RectangleShape highlightSlot = new RectangleShape();                    //The highlighted slot
    private Vector2f inventorySize;                                                 //The vector2f of the Inventory
    private Vector2f slotSize;                                                      //The vector2f of the Inventory
    private float slotLength;                                                       //The length of each slot
    private Texture slot = new Texture();

    /**
     * The Inventory GUI component
     * @param b The current items stored
     */
    public GUIInventory(Backpack b)
    {
        super(b);
        slotLength = (float)(Game.WINDOWSIZE - 900);
        slotSize = new Vector2f(slotLength, 90);
        inventorySize = new Vector2f((float)(Game.WINDOWSIZE - 400), 90);

        try
        {
            slot.loadFromFile(Paths.get("Assets" + File.separator + "UI" + File.separator+ "InventorySlot.png"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        for (int i = 0; i < inventoryRectangles.length; i++){
            inventoryRectangles[i] = new RectangleShape(slotSize);
            inventoryRectangles[i].setTexture(slot);
            inventoryRectangles[i].setPosition(200 + (slotLength * i), Game.WINDOWSIZE - inventorySize.y - 30);
        }
        for (int i = 0; i < back.length; i++){
            back[i] = new RectangleShape(slotSize);
            back[i].setTexture(slot);
            back[i].setPosition(200 + (slotLength * i), Game.WINDOWSIZE - inventorySize.y - 30);
        }

        highlightSlot.setFillColor(slotHighlight);
        highlightSlot.setSize(slotSize);
        highlightSlot.setPosition(inventoryRectangles[0].getPosition());

    }

    /**
     * Updates the inventory with items the player currently has
     */
    @Override
    public void update()
    {
        for(RectangleShape rec: inventoryRectangles)
        {
            rec.setTexture(slot);
        }
        for (int a = 0; a < getT().getObjectsINBACKPACK().size(); a++ )
        {
            inventoryGameObjects[a] = getT().getObjectsINBACKPACK().get(a);
            inventoryRectangles[a].setTexture(TextureMap.TEXTUREMAP.get(inventoryGameObjects[a].getComponent(TextureComponent.class).textureString));
            inventoryRectangles[a].setTextureRect(new IntRect(inventoryGameObjects[a].getComponent(TextureComponent.class).tileMapLocation * 32, 0,32,32));
        }
        highlightSlot.setPosition( inventoryRectangles[getT().getCurrentBackpackPosition()].getPosition());
    }

    /**
     * Draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for (int i = 0; i < back.length; i++){
            renderTarget.draw(back[i],renderStates);
        }

        for (RectangleShape r: inventoryRectangles)
        {
            if (r.getTexture()!=null)
            {
                if (r.getTexture() == slot){
                    renderTarget.draw(r,renderStates);
                }
                else {
                    Transform t = new Transform();
                    t = Transform.rotate(t, -45, r.getPosition().x + r.getSize().x / 2, r.getPosition().y + r.getSize().y / 2);
                    RenderStates rs = new RenderStates(renderStates, t);
                    renderTarget.draw(r, rs);
                }
            }
            else
            {
                renderTarget.draw(r,renderStates);
            }
        }
        renderTarget.draw(highlightSlot);
    }
}
