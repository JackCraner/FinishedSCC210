package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.StatComponents.*;

public enum PotionEnum implements FactorID, ItemTypes<PotionEnum>
{
    SPEEDPOTION("Speed Potion", "Speed_Potion.png", Speed.class),
    HEALTHPOTION("Health Potion", "Health_Potion.png", Health.class),
    WISDOMPOTION("Wisdom Potion", "Wisdom_Potion.png", Wisdom.class),
    STRENGTHPOTION("Strength Potion", "Strength_Potion.png", Strength.class);


    private final String name;
    private final String texture;
    private final Class<? extends Component> statType;

    PotionEnum(String name, String texture, Class<? extends Component> stat)
    {
        this.name = name;
        this.texture = texture;
        this.statType = stat;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTexture() {
        return texture;
    }

    public Class<? extends Component> getStatType() {
        return statType;
    }

    @Override
    public PotionEnum getType() {
        return this;
    }
}
