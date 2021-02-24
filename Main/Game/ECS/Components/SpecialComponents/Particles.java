package Main.Game.ECS.Components.SpecialComponents;

import Main.DataTypes.ComponentClock;
import Main.Game.ECS.Components.Component;
import org.jsfml.graphics.Color;

public class Particles extends Component
{
    Color color;
    private final float spawnCooldown;
    private final float rangeOfParticles;
    private float spawnTime;
    private float duration;
    private final Color c;
    private ComponentClock clock = new ComponentClock();
    public Particles(float spawnCooldown, float rangeOfParticles, float duration, Color c)
    {
        this.spawnCooldown = spawnCooldown;
        this.rangeOfParticles = rangeOfParticles;
        this.duration = duration;
        this.c = c;
    }

    public float getSpawnCooldown() {
        return spawnCooldown;
    }

    public float getSpawnTime() {
        return spawnTime;
    }

    public float getRangeOfParticles() {
        return rangeOfParticles;
    }

    public float getDuration() {
        return duration;
    }

    public Color getC() {
        return c;
    }

    public ComponentClock getClock() {
        return clock;
    }

    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    @Override
    public Component clone() {
        return null;
    }
}
