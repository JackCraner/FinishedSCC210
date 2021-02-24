package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;

public class SoundEffect extends Component
{


    public SoundEffect()
    {

    }

    @Override
    public Component clone() {
        return new SoundEffect();
    }
}
