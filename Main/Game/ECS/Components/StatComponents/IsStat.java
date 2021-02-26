package Main.Game.ECS.Components.StatComponents;

/**
 * Interface to define components that are stats
 *  - Stats are values that define key attributes statistically about an Entity
 *  - Examples: Speed, Health, Armor
 *
 */
public interface IsStat
{
    /**
     * Method to get the key value of the Stat
     * @return the stat as a float
     */
    public float getStat();

    /**
     * Method to set the value of the stat
     * @param value new value of the stat as a float
     */
    public void setStat(float value);

    /**
     * Method to get the base value of the stat
     *  - Used for effects (Base = unmodified stat)
     * @return base value of stat as a float
     */
    public float getBase();
}
