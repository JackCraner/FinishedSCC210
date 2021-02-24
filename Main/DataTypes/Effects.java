package Main.DataTypes;

import Main.Game.ECS.Components.Component;

public class Effects
{
    private float percentageModifier;
    private float duration;
    private boolean hasDuration;
    private Class<? extends Component> type;
    public Effects(Class<? extends Component> type,float percentageModifier)
    {
        this.type = type;
        this.percentageModifier = percentageModifier;
        this.hasDuration = false;
    }
    public Effects(Class<? extends Component> type,float percentageModifier, float duration)
    {
        this.type = type;
        this.percentageModifier = percentageModifier;
        this.duration = duration;
        this.hasDuration = true;
    }

    public void effectClock(float dt)
    {
        if (hasDuration)
        {
            duration -=dt;
        }
    }
    public boolean effectActive()
    {
        return duration >0||!hasDuration;
    }

    public float getDuration() {
        return duration;
    }

    public float getPercentageModifier() {
        return percentageModifier;
    }

    public Class<? extends Component> getType() {
        return type;
    }

    public Effects clone()
    {
        return new Effects(type,percentageModifier,duration);
    }
}
