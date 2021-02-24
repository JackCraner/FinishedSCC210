package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;

public class LifeSpanGameSystem extends GameSystem
{
    private static LifeSpanGameSystem systemInstance = new LifeSpanGameSystem();

    public static LifeSpanGameSystem getSystemInstance() {
        return systemInstance;
    }
    private LifeSpanGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(LifeSpan.class));
    }
    @Override
    public void update(float dt)
    {
        for(GameObject g: getGameObjectList())
        {
            LifeSpan l = g.getComponent(LifeSpan.class);

            if (l.checkIfFinished())
            {
                EntityManager.getEntityManagerInstance().removeGameObject(g);
            }
            else
            {
                l.countLifeSpan(dt);
            }
        }
    }
}
