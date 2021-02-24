package Main.Game.ECS.Components.ComponentENUMs;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Components.StatComponents.Strength;
import Main.Game.ECS.Components.StatComponents.Wisdom;

enum StatsEnum
{
    WISDOM(Wisdom.class),STRENGTH(Strength.class),HEALTH(Health.class),SPEED(Speed.class);

    public final Class<? extends Component> type;
    StatsEnum(Class<? extends Component> type)
    {
        this.type = type;
    }

    /*
    public static StatsEnum generateEffect()
    {

    }

     */


}
