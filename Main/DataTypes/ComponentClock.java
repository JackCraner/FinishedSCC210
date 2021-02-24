package Main.DataTypes;

public class ComponentClock
{
    float time;
    public ComponentClock()
    {

    }
    public void addTime(float dt)
    {
        time += dt;
    }

    public float getTime() {
        return time;
    }

    public void reset()
    {
        time =0;
    }
}
