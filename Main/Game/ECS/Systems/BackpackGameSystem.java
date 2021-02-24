package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.ItemComponents.Projectile;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.*;
import Main.Game.ECS.Components.StatComponents.EffectComponent;
import Main.Game.ECS.Factory.*;
import Main.Game.GUI.GUIComponents.GUIItemHover;
import Main.Game.Game;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.GUI.GUIComponents.GUIInventory;
import Main.Game.Managers.GUIManager;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;

public class BackpackGameSystem extends GameSystem
{

    Clock hovertime = new Clock();


    private static BackpackGameSystem systemInstance = new BackpackGameSystem();
    public static BackpackGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private BackpackGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Backpack.class, Inputs.class));
    }
    @Override
    public void update(float dt)
    {
            if (hovertime.getElapsedTime().asSeconds() > 1)
            {
                Game.PLAYER.getComponent(Backpack.class).setCurrentHover(null);
            }
           for (GameObject g: getGameObjectList())
           {
                Backpack backpack = g.getComponent(Backpack.class);
                Inputs oInputs = g.getComponent(Inputs.class);
                if ((g.getBitmask()  & BitMasks.getBitMask(CollisionEvent.class))!=0)
                {

                    for (GameObject pickupObject : g.getComponent(CollisionEvent.class).getG())
                    {

                        if ((BitMasks.checkIfContains(pickupObject.getBitmask(),Pickup.class)))
                        {
                            backpack.setCurrentHover(pickupObject);
                            hovertime.restart();
                            if (!(pickupObject.getComponent(Pickup.class).getItemType() instanceof ArmorEnum.ArmorPiece))
                            {
                                if (backpack.inventoryHasSpace() &&oInputs.pickUP)   //Either remove pickup button or make item hitbox bigger
                                {
                                    EntityManager.getEntityManagerInstance().removeGameObject(pickupObject);
                                    pickupObject.removeComponent(Position.class);
                                    pickupObject.addComponent(new Link(g));
                                    backpack.addGameObject(pickupObject);
                                    if (g.getName() == Entity.PLAYER.name)
                                    {
                                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                                    }
                                }
                            }



                        }
                    }

                }
                else
                {
                    backpack.setCurrentHover(null);
                }
                GUIManager.getGUIinstance().GUIUpdate(GUIItemHover.class);

                if (backpack.getObjectsINBACKPACK().size() > 0)
                {
                    GameObject mainHand = backpack.getBackPackSelect();
                    if (mainHand != null)
                    {
                        if (backpack.getCanUseItems())
                        {
                            if (BitMasks.checkIfContains(mainHand.getBitmask(),Projectile.class))
                            {
                                Projectile p = mainHand.getComponent(Projectile.class);
                                Vector2f pos = g.getComponent(Position.class).getPosition();
                                float angle =   g.getComponent(TransformComponent.class).getDirection();
                                mainHand.getComponent(TransformComponent.class).setDirection(angle);
                                if(p.isReady() && oInputs.use)
                                {

                                    pos = new Vector2f(pos.x + (float)(Math.cos(Math.toRadians(angle)) * Blueprint.OBJECTSIZE.x), pos.y + (float)(Math.sin(Math.toRadians(angle)) * Blueprint.OBJECTSIZE.y));
                                    GameObject spawn = p.getProjectile(pos);
                                    spawn.getComponent(TransformComponent.class).setDirection(angle);
                                    spawn.getComponent(Collider.class).setAvoidTime(g);

                                    if (BitMasks.checkIfContains(mainHand.getBitmask(), Damage.class))
                                    {
                                        spawn.addComponent(mainHand.getComponent(Damage.class).clone());
                                    }
                                    if (BitMasks.checkIfContains(mainHand.getBitmask(), GivenEffect.class))
                                    {
                                        spawn.addComponent(mainHand.getComponent(GivenEffect.class).clone());
                                    }
                                    EntityManager.getEntityManagerInstance().addGameObject(spawn);
                                }
                                p.reduceCoolDown(dt);
                            }


                            if (mainHand.getComponent(Pickup.class).getItemType() instanceof PotionEnum && oInputs.use)
                            {
                                if (BitMasks.checkIfContains(g.getBitmask(), EffectComponent.class))
                                {
                                    g.getComponent(EffectComponent.class).addEffect(mainHand.getComponent(GivenEffect.class).getEffectToGiven());
                                }
                                backpack.getObjectsINBACKPACK().remove(backpack.getCurrentBackpackPosition());
                                if (g.getName() == Entity.PLAYER.name)
                                {
                                    GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                                }
                            }

                        }



                        if(oInputs.drop && backpack.getEmptyCooldown() <= 0 && backpack.getCanDropItems())
                        {
                            backpack.getObjectsINBACKPACK().remove(backpack.getCurrentBackpackPosition());
                            backpack.setEmptyCooldown(0.4f);
                            mainHand.addComponent(new Position(g.getComponent(Position.class).getPosition(),mainHand));
                            mainHand.removeComponent(Link.class);
                            mainHand.getComponent(Collider.class).setAvoidTime(g,1.5f);
                            mainHand.getComponent(Pickup.class).getFloorTimer().restart();
                            EntityManager.getEntityManagerInstance().addGameObject(mainHand);
                            if (g.getName() == Entity.PLAYER.name)
                            {
                                GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                            }
                        }

                    }
                    if (backpack.getEmptyCooldown() >0)
                    {
                        backpack.setEmptyCooldown(backpack.getEmptyCooldown() - dt);
                    }

                    }



           }
    }
}
