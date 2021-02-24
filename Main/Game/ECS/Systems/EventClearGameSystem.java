package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.Game;

public class EventClearGameSystem extends GameSystem
{
    private static EventClearGameSystem systemInstance = new EventClearGameSystem();

    public static EventClearGameSystem getSystemInstance() {
        return systemInstance;
    }
    private EventClearGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(CollisionEvent.class));
    }


    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            CollisionEvent collisions = g.getComponent(CollisionEvent.class);
            if(g.getName() == Entity.PLAYER.name)
            {
                for (GameObject collide : collisions.getG())
                {
                    if (g.getComponent(Inputs.class).pickUP && collide.getName() == Entity.TRAPDOOR.name)
                    {
                        Game.getGame().newMap();
                    }
                }

            }
            collisions.getG().clear();
        }

    }
}
