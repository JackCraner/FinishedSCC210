package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.system.Clock;

public class BossAttack extends Component
{



    public Clock timer;
    public BossAttack()
    {
        timer = new Clock();


    }

    @Override
    public Component clone() {
        return new BossAttack();
    }
}
