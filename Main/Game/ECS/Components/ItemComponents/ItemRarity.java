package Main.Game.ECS.Components.ItemComponents;

import org.jsfml.graphics.Color;

import java.util.Random;

public enum ItemRarity
{
    COMMON(Color.WHITE,0.4f,1),
    UNCOMMON(Color.GREEN,0.7f,1.2f),
    RARE(Color.BLUE,0.9f,2),
    EPIC(Color.MAGENTA,0.97f,3),
    LEGENDARY(Color.YELLOW,1f,5);

    public final Color rarityColor;
    public final float chance;
    public final float multi;
    ItemRarity(Color c,float chance, float multi)
    {
        this.rarityColor =c;
        this.chance = chance;
        this.multi = multi;

    }

    public static ItemRarity generateRarity()
    {
        float v = new Random().nextFloat();
       if (v< COMMON.chance)
       {
           return COMMON;
       }
       else if(v>COMMON.chance && v<= UNCOMMON.chance)
       {
           return UNCOMMON;
       }
       else if(v>UNCOMMON.chance && v<= RARE.chance)
       {
           return RARE;
       }
       else if(v> RARE.chance&& v<= EPIC.chance)
       {
           return EPIC;
       }
       else
       {
           return LEGENDARY;
       }

    }

}
