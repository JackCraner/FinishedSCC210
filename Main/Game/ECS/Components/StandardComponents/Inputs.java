package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.Component;

public class Inputs extends Component
{
    public boolean forward;
    public boolean backwards;
    public boolean left;
    public boolean right;
    public boolean pickUP;
    public boolean use;
    public boolean drop;
    public  boolean attacking = false;




    public final InputTypes inputType;
    public Inputs(InputTypes inputType)
    {
        this.inputType = inputType;
    }
    public Boolean isMoving()
    {
        return (forward||backwards||left||right);
    }

    @Override
    public Component clone() {
        return new Inputs(inputType);
    }
}

