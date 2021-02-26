package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.system.Clock;

/**
 * Component: BossAttack
 *  -Defines that the gameObject should exhibit bossAttack mechanics
 */
public class BossAttack extends Component
{



    public Clock timer; //Timer counts time between the bosses attacks

    /**
     * Public Constructor to create a BossAttack Component
     */
    public BossAttack()
    {
        timer = new Clock();


    }

    /**
     * Method to clone the component
     * @return Copy of the Component
     */
    @Override
    public Component clone() {
        return new BossAttack();
    }
}
