package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;

/**
 * Component: Spawner
 *  - Defines that the GameObject should spawn enemies around it
 */
public class Spawner extends Component {

    final float range;  //Range that enemies spawn within
    final float spawnRate; //rate that enemies spawn

    /**
     * Public Constructor to create a new spawner component
     * @param range The range that enemies spawn within around the Spawner
     * @param spawnRate The Rate that enemies will spawn in the range (Spawn rate is a percentage checked every frame so v = 1 would be one spawn ever frame)
     */
    public Spawner(float range, float spawnRate)
    {
        this.range = range;
        this.spawnRate = spawnRate;
    }

    /**
     * Method to get the range
     * @return Range (float)
     */
    public float getRange() {
        return range;
    }

    /**
     * Method to get the spawn rate
     * @return spawn rate (float)
     */
    public float getSpawnRate() {
        return spawnRate;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Spawner(range, spawnRate);
    }
}
