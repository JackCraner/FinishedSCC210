package Main.Game.ECS.Entity;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.FactorID;

import java.util.ArrayList;

public class GameObject implements Cloneable
{
    //DESIGN PATTERN
    /*
    - GameObject is like the chassis of a car
    - Simply an ArrayList of Components
    - The Components Inside a GameObject defines its scope and ability

    Preferably only create GameObjects through the "Factory Pattern" aka Blueprint Class

    If you wanna make a new Base GameObject, for example a Torch
        - First go to Blueprint and make a new class called Torch with parameters
        - Define the components the Torch should have
        - Next Add it to EntityID
        - Then add the new GameObject to the ENTITYMANAGER via this method
     */



    private FactorID SID;
    private int UID=0;
    private int bitmask = 0;


    private ArrayList<Component> componentList = new ArrayList<>();


    public GameObject(FactorID SID)
    {
        this.SID = SID;

    }


    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for (Component c: componentList)
        {
            if ((componentClass.isAssignableFrom(c.getClass())))
            {
                return componentClass.cast(c);
            }
        }
        return null;
    }
    public ArrayList<Component> getComponentList() {
        return componentList;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for (int i= 0; i<componentList.size();i++)
        {

            if (componentList.get(i).getClass().isAssignableFrom(componentClass))
            {
                componentList.remove(i);
                bitmask = bitmask & ~BitMasks.getBitMask(componentClass);
                return;
            }
        }
    }

    public void addComponent(Component c)
    {
        if ((bitmask & BitMasks.getBitMask(c.getClass())) == 0)
        {
            bitmask = bitmask | BitMasks.getBitMask(c.getClass());
            this.componentList.add(c);
        }
        else
        {
            System.out.println("ERROR with GameObject " + SID.getName() + " :: Two components of same type");
        }

    }
    public void setComponentList(ArrayList<Component> c)
    {
        componentList = c;
    }

    public String getName() {
        return SID.getName();
    }

    public String getTexture()
    {
        return SID.getTexture();
    }

    public FactorID getSID() {
        return SID;
    }

    public int getBitmask() {
        return bitmask;
    }

    public GameObject clone()
    {
        GameObject go = new GameObject(SID);
        for (Component c: componentList)
        {
            go.addComponent(c.clone());
        }
        if ((go.getBitmask() & BitMasks.produceBitMask(Position.class)) != 0)
        {
            go.getComponent(Position.class).setMe(go);
        }
       return go;

    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    @Override
    public String toString() {
        return SID.getName();
    }
}
