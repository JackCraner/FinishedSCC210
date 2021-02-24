package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import org.jsfml.system.Vector2f;

/**
 * MOVEMENTGAMESYSTEM
 *
 * - handles movement of GameObjects
 *
 */
public class MovementGameSystem extends GameSystem
{
    private static MovementGameSystem systemInstance = new MovementGameSystem();

    public static MovementGameSystem getSystemInstance()
    {
        return systemInstance;
    }

    /**
     * SETS BITMASK
     */
    private MovementGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class, Speed.class));
    }

    @Override
    public void update(float dt)
    {
        Vector2f curPos;
        Speed movement;
        Vector2f newPos;
        float speed;
        for(GameObject g: getGameObjectList())
        {
            curPos =  g.getComponent(Position.class).getPosition();
            movement = g.getComponent(Speed.class);
            newPos = curPos;
            speed = movement.getStat() * dt;
            //System.out.println(movement.getStat() + "   " + g.getName());
            if(movement.getType() == MovementTypes.CONTROLLED)
            {

                if ((g.getBitmask() & BitMasks.getBitMask(Inputs.class)) !=0)
                {
                    Inputs oInputs = g.getComponent(Inputs.class);
                    if (oInputs.forward)
                    {
                        newPos = new Vector2f(newPos.x,newPos.y -  speed);
                    }
                    if (oInputs.left)
                    {
                        newPos = new Vector2f(newPos.x - speed ,newPos.y);

                    }
                    if (oInputs.backwards)
                    {
                        newPos = new Vector2f(newPos.x,newPos.y +  speed );
                    }
                    if (oInputs.right)
                    {
                        newPos = new Vector2f(newPos.x +  speed ,newPos.y);
                    }
                }
                else
                {
                    System.out.println("CONTROLLED Movement Object without an INPUT component");
                }



            }
            if (movement.getType() == MovementTypes.LINEAR)
            {

                if ((g.getBitmask() & BitMasks.produceBitMask(TransformComponent.class)) !=0)
                {
                    TransformComponent t = g.getComponent(TransformComponent.class);
                    newPos = new Vector2f(curPos.x + (float)Math.cos(Math.toRadians(t.getDirection())) * speed, curPos.y + (float)Math.sin(Math.toRadians(t.getDirection())) * speed);
                }

            }

            if (newPos != curPos)
            {
                g.getComponent(Position.class).updatePosition(newPos);

            }



        }

    }



}
