package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.Component;

/**
 * Component: Inputs
 *  - Defines that the GameObject should have some sort of brain
 *  - Defines button presses each frame
 *      - AI or player controlled
 */
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

    /**
     * Public Constructor to create a Inputs Component
     * @param inputType The type of Input Brain (AI or Player, etc) (reference InputTypes ENUM)
     */
    public Inputs(InputTypes inputType)
    {
        this.inputType = inputType;
    }

    /**
     * Method to see if the GameObject is moving this frame
     * @return Boolean
     */
    public Boolean isMoving()
    {
        return (forward||backwards||left||right);
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Inputs(inputType);
    }
}

