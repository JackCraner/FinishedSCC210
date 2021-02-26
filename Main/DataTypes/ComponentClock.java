package Main.DataTypes;

/**
 * A simple Time keeping data type
 *
 * -- Normally use JSFML Clock
 *  -- Use this when you need a clock that you can dynamically add time values to (eg counts upwards)
 */
public class ComponentClock
{

    float time; //The time
    public ComponentClock()
    {

    }

    /**
     * Adds time to the clock
     * @param dt Adds the time to the clock (dt being time between frames)
     */
    public void addTime(float dt)
    {
        time += dt;
    }

    /**
     * Method to get the current time of the clock
     * @return the float time on the clock
     */
    public float getTime() {
        return time;
    }

    /**
     * Method to reset the clock to 0 seconds
     */
    public void reset()
    {
        time =0;
    }
}
