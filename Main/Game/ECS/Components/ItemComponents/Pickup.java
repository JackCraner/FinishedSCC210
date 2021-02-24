package Main.Game.ECS.Components.ItemComponents;


import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.ItemTypes;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

public class Pickup extends Component
{
    /*
    private ArrayList<ItemEffect> itemEffects = new ArrayList<>();
    public Pickup(ItemEffect... effects)
    {
        for (ItemEffect e: effects)
        {
            itemEffects.add(e);
        }
    }

    public ArrayList<ItemEffect> getItemEffects() {
        return itemEffects;
    }

     */
    //give it a Use case whether it is a sword swing or eat of an apple

    private Clock floorTimer;
    private ItemRarity rarity;
    private ItemTypes itemType;

    public Pickup(ItemRarity rarity, ItemTypes itemType)
    {
        this.itemType = itemType;
        this.rarity = rarity;
        this.floorTimer = new Clock();
    }

    public Clock getFloorTimer() {
        return floorTimer;
    }

    public ItemTypes getItemType() {
        return itemType;
    }

    public ItemRarity getRarity() {
        return rarity;
    }



    @Override
    public Component clone() {
        return new Pickup(rarity,itemType);
    }
}
