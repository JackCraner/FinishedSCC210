package Main.Game.ECS.Systems;


import Main.DataTypes.Effects;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StatComponents.EffectComponent;
import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.SpecialComponents.Particles;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StatComponents.IsStat;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIHealthBar;
import Main.Game.Managers.GUIManager;
import org.jsfml.graphics.Color;

import java.util.Iterator;

public class EffectModifierGameSystem extends GameSystem
{
    private static EffectModifierGameSystem systemInstance = new EffectModifierGameSystem();

    public static EffectModifierGameSystem getSystemInstance() {
        return systemInstance;
    }

    private EffectModifierGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(EffectComponent.class));
    }





    @Override
    public void update(float dt)
    {

        for (GameObject g:getGameObjectList())
        {
            EffectComponent objectEffects = g.getComponent(EffectComponent.class);
            if (g.getComponent(CollisionEvent.class) != null)
            {
                CollisionEvent c = g.getComponent(CollisionEvent.class);
                for (GameObject collision: c.getG())
                {
                    if (BitMasks.checkIfContains(collision.getBitmask(), GivenEffect.class) && !BitMasks.checkIfContains(collision.getBitmask(), Pickup.class))
                    {
                        Effects e =collision.getComponent(GivenEffect.class).getEffectToGiven();
                        if(BitMasks.checkIfContains(g.getBitmask(), e.getType()))
                        {
                            objectEffects.addEffect(e);
                        }

                    }
                }

            }


            if (objectEffects.getNewEffects().size() > 0)
            {
                if (!BitMasks.checkIfContains(g.getBitmask(),Particles.class))
                {
                    if (objectEffects.getNewEffects().get(0).getDuration() > 0)
                    {
                        g.addComponent(new Particles(0.05f,0.15f, objectEffects.getNewEffects().get(0).getDuration(), Color.RED));
                    }


                }
                Iterator<Effects> newEffectItr = objectEffects.getNewEffects().iterator();
                while (newEffectItr.hasNext())
                {
                    Effects e = newEffectItr.next();
                    IsStat c = (IsStat)g.getComponent(e.getType());
                    c.setStat(c.getStat()+ ((c.getBase() * e.getPercentageModifier())));
                    if (c instanceof Health)
                    {
                        if (g.getName() == Entity.PLAYER.name)
                        {
                            GUIManager.getGUIinstance().GUIUpdate(GUIHealthBar.class);
                        }
                    }
                    objectEffects.getEffectsArrayList().add(e);
                    newEffectItr.remove();
                }

            }


            if (objectEffects.getEffectsArrayList().size()>0)
            {
                Iterator<Effects>  currentEffectItr = objectEffects.getEffectsArrayList().iterator();
                while ( currentEffectItr.hasNext())
                {
                    Effects e =  currentEffectItr.next();
                    e.effectClock(dt);
                    if (!e.effectActive())
                    {
                        if (e.getType() != Health.class)
                        {
                            IsStat c = (IsStat)g.getComponent(e.getType());
                            c.setStat(c.getStat()- ((c.getBase() * e.getPercentageModifier())));
                            currentEffectItr.remove();
                        }


                    }

                }
                if (objectEffects.getEffectsArrayList().size() == 0)
                {
                    if (BitMasks.checkIfContains(g.getBitmask(),Particles.class))
                    {
                        g.removeComponent(Particles.class);
                    }
                }



            }

        }


    }
}
