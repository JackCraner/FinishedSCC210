package Main.Game.ECS.Components.ItemComponents;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.Component;

/**
 * Component: GivenEffect
 *  - Defines any spell or stat modifier effect that should be given
 *      - On weapon hit
 *      - On item use
 */
public class GivenEffect extends Component
{

    private Effects effectToGiven;  //Effect to give on

    /**
     * Public Constructor to define the GivenEffect Component
     * @param e The Effect to give (Reference Effect DataType)
     *
     */
    public GivenEffect(Effects e)
    {
        this.effectToGiven = e;
    }

    /**
     * Method to get the Effect held in this component
     * @return The Effect (Reference Effect DataType)
     */
    public Effects getEffectToGiven() {
        return effectToGiven;
    }

    /**
     * Clones this Component
     * @return a copy of the GivenEffect Component
     */
    @Override
    public Component clone() {
        return new GivenEffect(effectToGiven.clone());
    }
}
