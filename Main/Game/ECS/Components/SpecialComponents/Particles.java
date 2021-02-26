package Main.Game.ECS.Components.SpecialComponents;

import Main.DataTypes.ComponentClock;
import Main.Game.ECS.Components.Component;
import org.jsfml.graphics.Color;

/**
 * Component: Particle
 *  -Defines if the GameObject should emit particles
 */
public class Particles extends Component
{

    private final float spawnCooldown;
    private final float rangeOfParticles;
    private float spawnTime;
    private float duration;
    private final Color c;
    private ComponentClock clock = new ComponentClock();

    /**
     * Public Constructor to create a particles Component
     * @param spawnCooldown Spawn rate of the particles (v = 0.05 means a particle spawns every 0.05 seconds) (float)
     * @param rangeOfParticles  Distance the particles will travel (float)
     * @param duration Duration the particles will stay alive (float)
     * @param c Color of the particles (Color)
     */
    public Particles(float spawnCooldown, float rangeOfParticles, float duration, Color c)
    {
        this.spawnCooldown = spawnCooldown;
        this.rangeOfParticles = rangeOfParticles;
        this.duration = duration;
        this.c = c;
    }

    /**
     * Method to get the spawnCooldown
     * @return the curren value of the cooldown
     */
    public float getSpawnCooldown() {
        return spawnCooldown;
    }

    /**
     * Method to get the spawntime
     * @return float
     */
    public float getSpawnTime() {
        return spawnTime;
    }

    /**
     * Method to get the range value of the particles
     * @return the range as a float
     */
    public float getRangeOfParticles() {
        return rangeOfParticles;
    }

    /**
     * Method to get the duration of the particles
     * @return the duration as a float
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Method to get the color of the particles
     * @return the color as a JSFML Color
     */
    public Color getC() {
        return c;
    }

    /**
     * Method to get the Clock component of the particle component
     *  - Used to keep track of the cooldown timers
     * @return The clock
     */
    public ComponentClock getClock() {
        return clock;
    }

    /**
     * Method to update the spawn time of the particles
     * @param spawnTime new float spawn time
     */
    public void setSpawnTime(float spawnTime) {
        this.spawnTime = spawnTime;
    }

    /**
     * Method to clone the component
     * @return the copy of the component
     */
    @Override
    public Component clone() {
        return null;
    }
}
