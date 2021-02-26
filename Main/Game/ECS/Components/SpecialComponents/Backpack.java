package Main.Game.ECS.Components.SpecialComponents;


import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

/**
 * Component: Backpack
 *  -Defines that a gameobject can pick up and hold other GameObjects
 */
public class Backpack extends Component
{
    private ArrayList<GameObject> gameObjectsINBACKPACK = new ArrayList<>();    //Array List of Objects in inventory
    private GameObject currentHover;    //Current Item GameObject is standing over


    private int currentBackpackPosition = 0;    //Current Item slot selected by the GameObject
    private int maxNumObject; //Number of inventory slots
    private float emptyCooldown;    //cooldown to drop items

    private final Boolean canUseItems;  //Defines if the GameObject can use items
    private final Boolean canDropItems; //Defies if the GameObject can drop items

    /**
     * Public Constructor for defining a Backpack
     * @param size Number of slots in the backpack
     * @param canUseItems If the Backpack can use items
     * @param itemsIn Any inital items to spawn within the backpack
     */
    public Backpack(int size, Boolean canUseItems, GameObject... itemsIn)
    {
        maxNumObject = size;
        this.canUseItems = canUseItems;
        this.canDropItems = canUseItems;
    }

    /**
     * Method to add a GameObject to the Backpack
     * @param g The GameObject to add
     */
    public void addGameObject(GameObject g)
    {

        gameObjectsINBACKPACK.add(g);

    }

    /**
     * Method to return if the backpack can use items
     * @return Boolean
     */
    public Boolean getCanUseItems() {
        return canUseItems;
    }

    /**
     * Method to return if the backpack can drop items
     * @return Boolean
     */
    public Boolean getCanDropItems()
    {
        return canDropItems;
    }

    /**
     * Method to set the current Hover of the backpack (Hover being the item, which the GameObject is ontop of)
     * @param currentHover GameObject to set the hover too
     */
    public void setCurrentHover(GameObject currentHover) {
        this.currentHover = currentHover;
    }

    /**
     * Method to get the current Hover of the backpack
     * @return
     */
    public GameObject getCurrentHover() {
        return currentHover;
    }

    /**
     * Method to get the value of the timer of the EmptyCooldown
     * @return Float value of the time
     */
    public float getEmptyCooldown() {
        return emptyCooldown;
    }

    /**
     * Method to update the cooldown value
     * @param emptyCooldown the new cooldown value
     */
    public void setEmptyCooldown(float emptyCooldown) {
        this.emptyCooldown = emptyCooldown;
    }

    /**
     * Method to check if there is inventory space
     * @return Boolean
     */
    public Boolean inventoryHasSpace()
    {
        return gameObjectsINBACKPACK.size() < maxNumObject;
    }

    /**
     * Method to get all the objects in the backpack
     * @return ArrayList of GameObjects
     */
    public ArrayList<GameObject> getObjectsINBACKPACK()
    {
        return gameObjectsINBACKPACK;
    }

    /**
     * Method to get the current GameObject selected based off the currentBackPackPosition value
     * @return GameObject Highlighted
     */
    public GameObject getBackPackSelect()
    {
        try
        {
            return gameObjectsINBACKPACK.get(currentBackpackPosition);
        }
        catch (Exception e)
        {
            return null;
        }

    }

    /**
     * Method to get the value of the current BackPack Position
     * @return int value
     */
    public int getCurrentBackpackPosition() {
        return currentBackpackPosition;
    }

    /**
     * Method to update the backpack position
     * Think of backpack position as a pointer to where is currently highlighted in inventory
     * @param currentBackpackPosition int
     */
    public void setCurrentBackpackPosition(int currentBackpackPosition) {
        this.currentBackpackPosition = currentBackpackPosition;
    }

    /**
     * Method to clone the component
     * @return The copy of the component
     */
    @Override
    public Component clone()
    {
        return new Backpack(maxNumObject, canUseItems,gameObjectsINBACKPACK.toArray(new GameObject[gameObjectsINBACKPACK.size()]));
    }
}
