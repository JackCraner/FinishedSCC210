package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.Component;

/**
 * Component: Speed
 *  - Defines the movement speed of a entity
 */
public class Speed extends Component implements IsStat{

    float speed;
    float baseSpeed;
    private MovementTypes type;

    /**
     *  Public Constructor to make a new Speed Component
     * @param type Movement Type (Reference MovementTypes ENUM)
     * @param speed Movement Speed value as a float
     */
    public Speed(MovementTypes type, float speed)
    {
        this.speed = speed;
        this.type = type;
        this.baseSpeed = speed;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Speed(type,speed);
    }

    /**
     * Method to get the value of the stat
     * @return speed value
     */
    @Override
    public float getStat() {
        return speed;
    }

    /**
     * Method to set the value of the stat
     * @param value the new speed value
     */
    @Override
    public void setStat(float value) {
        speed = value;
    }

    /**
     * Method to get the base value of speed
     * @return base value of speed
     */
    @Override
    public float getBase() {
        return baseSpeed;
    }

    /**
     * Method to get the Movement Type
     * @return Movement Type
     */
    public MovementTypes getType() {
        return type;
    }
}
