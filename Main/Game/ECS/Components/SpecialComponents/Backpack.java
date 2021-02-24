package Main.Game.ECS.Components.SpecialComponents;


import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class Backpack extends Component
{
    private ArrayList<GameObject> gameObjectsINBACKPACK = new ArrayList<>();
    private GameObject currentHover;


    private int currentBackpackPosition = 0;
    private int maxNumObject = 1;
    private float emptyCooldown;

    private final Boolean canUseItems;
    private final Boolean canDropItems;

    public Backpack(int size, Boolean canUseItems, GameObject... itemsIn)
    {
        maxNumObject = size;
        this.canUseItems = canUseItems;
        this.canDropItems = canUseItems;
    }

    public void addGameObject(GameObject g)
    {

        gameObjectsINBACKPACK.add(g);

    }

    public Boolean getCanUseItems() {
        return canUseItems;
    }
    public Boolean getCanDropItems()
    {
        return canDropItems;
    }

    public void setCurrentHover(GameObject currentHover) {
        this.currentHover = currentHover;
    }

    public GameObject getCurrentHover() {
        return currentHover;
    }

    public float getEmptyCooldown() {
        return emptyCooldown;
    }

    public void setEmptyCooldown(float emptyCooldown) {
        this.emptyCooldown = emptyCooldown;
    }

    public Boolean inventoryHasSpace()
    {
        return gameObjectsINBACKPACK.size() < maxNumObject;
    }
    public ArrayList<GameObject> getObjectsINBACKPACK()
    {
        return gameObjectsINBACKPACK;
    }

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

    public int getCurrentBackpackPosition() {
        return currentBackpackPosition;
    }

    public void setCurrentBackpackPosition(int currentBackpackPosition) {
        this.currentBackpackPosition = currentBackpackPosition;
    }

    @Override
    public Component clone()
    {
        return new Backpack(maxNumObject, canUseItems,gameObjectsINBACKPACK.toArray(new GameObject[gameObjectsINBACKPACK.size()]));
    }
}
