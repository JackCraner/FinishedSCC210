package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;

public class Spawner extends Component {

    final float range;
    final float spawnRate;


    public Spawner(float range, float spawnRate)
    {
        this.range = range;
        this.spawnRate = spawnRate;
    }

    public float getRange() {
        return range;
    }

    public float getSpawnRate() {
        return spawnRate;
    }

    @Override
    public Component clone() {
        return new Spawner(range, spawnRate);
    }
}
