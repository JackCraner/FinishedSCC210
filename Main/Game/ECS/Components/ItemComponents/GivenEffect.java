package Main.Game.ECS.Components.ItemComponents;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.Component;

public class GivenEffect extends Component
{

    private Effects effectToGiven;
    public GivenEffect(Effects e)
    {
        this.effectToGiven = e;
    }

    public Effects getEffectToGiven() {
        return effectToGiven;
    }

    @Override
    public Component clone() {
        return new GivenEffect(effectToGiven.clone());
    }
}
