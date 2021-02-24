package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.Component;

public class Speed extends Component implements IsStat{

    float speed;
    float baseSpeed;
    private MovementTypes type;


    public Speed(MovementTypes type, float speed)
    {
        this.speed = speed;
        this.type = type;
        this.baseSpeed = speed;
    }


    @Override
    public Component clone() {
        return new Speed(type,speed);
    }

    @Override
    public float getStat() {
        return speed;
    }

    @Override
    public void setStat(float value) {
        speed = value;
    }

    @Override
    public float getBase() {
        return baseSpeed;
    }

    public MovementTypes getType() {
        return type;
    }
}
