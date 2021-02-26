package Main.DataTypes;

import Main.Game.ECS.Components.Component;

/**
 * Data type Effects:
 *  Used for applying spell effects and modification effects to GameObjects
 *
 */
public class Effects
{
    private float percentageModifier;  //Strength of Modifier as a float percentage (1 = 100%, 0.5 = 50%)
    private float duration;     //Duration of the effect in seconds
    private boolean hasDuration;    //Whether the Effect is permanenet or not
    private Class<? extends Component> type;        //Stat the Modifier is on

    /**
     * Constructor for a permanent Modifier Effect
     * @param type  Stat which the Effect is on
     * @param percentageModifier    Strength of Effect as a float percentage (0.5 = 50%)
     */
    public Effects(Class<? extends Component> type,float percentageModifier)
    {
        this.type = type;
        this.percentageModifier = percentageModifier;
        this.hasDuration = false;
    }

    /**
     * Constructor for a timed Effect
     * @param type  Stat which the Effect is on
     * @param percentageModifier    Strength of the Effect as a float percentage (0.5 = 50%)
     * @param duration  Duration of the Effect in seconds
     */
    public Effects(Class<? extends Component> type,float percentageModifier, float duration)
    {
        this.type = type;
        this.percentageModifier = percentageModifier;
        this.duration = duration;
        this.hasDuration = true;
    }

    /**
     * Method to update the clock/ duration of the effect
     * @param dt the time between frames
     */
    public void effectClock(float dt)
    {
        if (hasDuration)
        {
            duration -=dt;
        }
    }

    /**
     * Method to check if the Effect should still be active
     * @return  True is duration is still above 0 or if the effect is designed to be permanent
     */
    public boolean effectActive()
    {
        return duration >0||!hasDuration;
    }

    /**
     * Method to get the current Duration left on the effect
     * @return Float duration left
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Method to get the strength of the effect
     * @return Float strength of the effect
     */
    public float getPercentageModifier() {
        return percentageModifier;
    }

    /**
     * Method to get the type of effect
     * @return the component type of the effect (Eg: Strength, Wisdom, Speed)
     */
    public Class<? extends Component> getType() {
        return type;
    }

    /**
     * Clones the effect
     * @return A copy of the effect
     */
    public Effects clone()
    {
        return new Effects(type,percentageModifier,duration);
    }
}
