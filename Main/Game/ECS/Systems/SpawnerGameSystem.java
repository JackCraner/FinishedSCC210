package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.Spawner;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.EnemyEnum;
import Main.Game.Managers.EntityManager;
import Main.Game.Managers.MapManager;
import org.jsfml.system.Vector2f;

import java.util.Random;

public class SpawnerGameSystem extends GameSystem
{


    private static SpawnerGameSystem systemInstance = new SpawnerGameSystem();
    Random generator = new Random();
    public static SpawnerGameSystem getInstance() {
        return systemInstance;
    }

    private SpawnerGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(Spawner.class));
    }


    @Override
    public void update(float dt)
    {

        for (GameObject g: getGameObjectList())
        {
            float range = g.getComponent(Spawner.class).getRange();
            float num = generator.nextFloat();
            if (num< g.getComponent(Spawner.class).getSpawnRate() /2)
            {
                Vector2f gPos = g.getComponent(Position.class).getPosition();
                EntityManager.getEntityManagerInstance().addGameObject(Blueprint.enemySWORD(spawnEnemy(range,gPos), EnemyEnum.randomEnemy()));
            } else if (num >= g.getComponent(Spawner.class).getSpawnRate() /2 && num < g.getComponent(Spawner.class).getSpawnRate()){
                Vector2f gPos = g.getComponent(Position.class).getPosition();
                EntityManager.getEntityManagerInstance().addGameObject(Blueprint.enemySTAFF(spawnEnemy(range,gPos), EnemyEnum.randomEnemy()));
            }

        }
    }

    public Vector2f spawnEnemy(float range, Vector2f gPos)
    {
        Vector2f spawnPos = new Vector2f(gPos.x + generator.nextInt((int)(range*2))-range,gPos.y + generator.nextInt((int)(range*2))-range);
        if (MapManager.getManagerInstance().checkTile(spawnPos))
        {
            return spawnPos;
        }
        else
        {
            return spawnEnemy(range,gPos);
        }


    }
}
