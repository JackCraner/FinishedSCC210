package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.Managers.EntityManager;

public class CleanUpGameSystem extends GameSystem
{
    private static CleanUpGameSystem gameSystem = new CleanUpGameSystem();

    public static CleanUpGameSystem getGameSystem()
    {
        return gameSystem;
    }

    private CleanUpGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(Pickup.class));
    }
    @Override
    public void update(float dt)
    {

        for(GameObject g: getGameObjectList())
        {
            if (g.getName() != "Torch")
            {

                if (g.getComponent(Pickup.class).getFloorTimer().getElapsedTime().asSeconds() > 5)
                {
                    EntityManager.getEntityManagerInstance().removeGameObject(g);
                }

            }

        }

    }
}
