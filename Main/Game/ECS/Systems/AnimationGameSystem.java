package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.SpecialComponents.ArmorContainer;
import Main.Game.ECS.Components.StandardComponents.Animation;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import org.jsfml.system.Vector2f;

public class AnimationGameSystem extends GameSystem
{


    private static AnimationGameSystem systemInstance = new AnimationGameSystem();

    public static AnimationGameSystem getSystemInstance() {
        return systemInstance;
    }
    private AnimationGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class, TransformComponent.class, Animation.class));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            Vector2f pos = g.getComponent(Position.class).getPosition();
            TransformComponent trans = g.getComponent(TransformComponent.class);
            Animation anim = g.getComponent(Animation.class);
            float angle = trans.getDirection();
            trans.setScale(new Vector2f(1,1));
            if (angle<45 && angle > -45)
            {
                anim.spriteSheetDirection = (byte)1*3;
                trans.setScale(new Vector2f(-1,1));
                trans.setFacingRight(true);
            }
            else if(angle >135 || angle < -135)
            {
                anim.spriteSheetDirection = (byte)1*3;
                trans.setFacingRight(false);

            }
            else if (angle > 0)
            {
                anim.spriteSheetDirection= (byte)0*3;

            }
            else
            {
                anim.spriteSheetDirection= (byte)2*3;

            }
            if (BitMasks.checkIfContains(g.getBitmask(),Speed.class))
            {
                anim.setAnimationTime(30/g.getComponent(Speed.class).getStat());
            }
            if (BitMasks.checkIfContains(g.getBitmask(), Inputs.class))
            {
                if (g.getComponent(Inputs.class).isMoving())
                {
                    if (anim.checkClock())
                    {
                        anim.resetClock();
                        anim.spriteSheetAnimation = (byte)oscillate(anim.animationCounter,0,2);
                        anim.animationCounter ++;

                    }
                    else{
                        anim.updateClock(dt);
                    }
                }
                else
                {
                    anim.animationCounter =0;
                    anim.finishClock();
                    anim.spriteSheetAnimation =1;
                }
            }
            if (BitMasks.checkIfContains(g.getBitmask(), ArmorContainer.class))
            {
                for (GameObject armor : g.getComponent(ArmorContainer.class).getArmor())
                {
                    if (armor != null)
                    {
                        Animation animArmor = armor.getComponent(Animation.class);
                        animArmor.spriteSheetDirection = anim.spriteSheetDirection;
                        animArmor.spriteSheetAnimation = anim.spriteSheetAnimation;
                    }
                }

            }


        }


    }

    public static int oscillate(int input, int min, int max)
    {
        int range = max - min ;
        return min + Math.abs(((input + range) % (range * 2)) - range);
    }
}
