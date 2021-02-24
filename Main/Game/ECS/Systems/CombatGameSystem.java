package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.ItemComponents.Projectile;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Components.StandardComponents.Link;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.ECS.Components.StatComponents.IsStat;
import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.ECS.Factory.*;
import Main.Game.GUI.GUIComponents.GUIBossHealthBar;
import Main.Game.GUI.GUIComponents.GUIModeEnum;
import Main.Game.Game;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.GUI.GUIComponents.GUIHealthBar;
import Main.Game.Managers.GUIManager;
import org.jsfml.system.Vector2f;
import Main.Game.ECS.Factory.WeaponEnum;

import java.util.Random;

public class CombatGameSystem extends GameSystem
{
    private static CombatGameSystem systemInstance = new CombatGameSystem();
    public static CombatGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private CombatGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Health.class));
    }
    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList()) {
            Health objectHealth = g.getComponent(Health.class);
            if ((g.getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) != 0) {
                for (GameObject hitBY:g.getComponent(CollisionEvent.class).getG())
                {
                    if (!(hitBY.getComponent(Collider.class).checkGameObject(g))) {
                        if (BitMasks.checkIfContains(hitBY.getBitmask(),Damage.class) && !BitMasks.checkIfContains(hitBY.getBitmask(),Pickup.class))
                        {
                            float damageDealt =hitBY.getComponent(Damage.class).getDamage();
                            if (hitBY.getSID() instanceof WeaponEnum.Projectile)
                            {

                                if (BitMasks.checkIfContains(hitBY.getBitmask(), Link.class) && BitMasks.checkIfContains(hitBY.getComponent(Link.class).getG().getBitmask(), Link.class) && hitBY.getComponent(Link.class).getG() != null && hitBY.getComponent(Link.class).getG().getComponent(Link.class).getG() != null && BitMasks.checkIfContains(hitBY.getComponent(Link.class).getG().getComponent(Link.class).getG().getBitmask(),((WeaponEnum.Projectile) hitBY.getSID()).getMod().getType()))
                                {
                                    damageDealt += damageDealt * (((IsStat)hitBY.getComponent(Link.class).getG().getComponent(Link.class).getG().getComponent(((WeaponEnum.Projectile) hitBY.getSID()).getMod().getType())).getStat()/100);
                                }

                            }
                            if (BitMasks.checkIfContains(g.getBitmask(), Armor.class))
                            {
                                damageDealt *= g.getComponent(Armor.class).getStat();
                            }


                            //hit numbers
                            objectHealth.adjustHealth(-damageDealt);
                            Vector2f textPos = g.getComponent(Position.class).getPosition();
                            textPos = new Vector2f(textPos.x + new Random().nextInt(30) - 15, textPos.y + 20);
                            EntityManager.getEntityManagerInstance().addGameObject(Blueprint.damageNumber(textPos, damageDealt));
                            hitBY.getComponent(Collider.class).setAvoidTime(g);
                            if (g.getName() == Entity.PLAYER.name)
                            {
                                GUIManager.getGUIinstance().GUIUpdate(GUIHealthBar.class);
                            }
                            if (g.getSID() instanceof BossEnum)
                            {

                            }
                        }
                    }
                }


            }
            if (objectHealth.getStat() <= 0) {
                EntityManager.getEntityManagerInstance().removeGameObject(g);
                if ((g.getBitmask() & BitMasks.getBitMask(Backpack.class)) != 0) {
                    Backpack b = g.getComponent(Backpack.class);
                    Vector2f pos = g.getComponent(Position.class).getPosition();
                    Vector2f size = g.getComponent(TransformComponent.class).getSize();
                    pos = new Vector2f(pos.x + size.x / 2, pos.y + size.y / 2);
                    for (GameObject g1 : b.getObjectsINBACKPACK()) {
                        g1.addComponent(new Position(pos, g1));
                        if (BitMasks.checkIfContains(g1.getBitmask(),Pickup.class))
                        {
                            g1.getComponent(Pickup.class).getFloorTimer().restart();
                        }

                        if (g1.getName() == "Wand"){
                            g1.removeComponent(Projectile.class);
                            g1.addComponent(new Projectile(Blueprint.playerprojectile(WeaponEnum.STAFF.getProjectile(),g1),WeaponEnum.STAFF.getProjectile().getCooldown()));
                        } else if (g1.getName() == "Katana") {
                            g1.removeComponent(Projectile.class);
                            g1.addComponent(new Projectile(Blueprint.playerprojectile(WeaponEnum.SWORD.getProjectile(),g1),WeaponEnum.SWORD.getProjectile().getCooldown()));
                        }
                        g.addComponent(new LifeSpan(1f));
                        g1.removeComponent(Link.class);
                        EntityManager.getEntityManagerInstance().addGameObject(g1);
                    }
                    if (BitMasks.checkIfContains(g.getBitmask(), Level.class)) {
                        Level playerLevel = Game.PLAYER.getComponent(Level.class);
                        playerLevel.addXP(g.getComponent(Level.class).getCurrentLevel()*4);
                    /*
                    Level objectLevel = g.getComponent(Level.class);
                    Position objectPosition = g.getComponent(Position.class);
                    for (int i = 0; i < objectLevel.getCurrentLevel(); i++)
                    {
                        EntityManager.getEntityManagerInstance().addGameObject(Blueprint.xpOrb(objectPosition.getPosition()));
                    }

                     */
                    }
                    if (g.getName() == Entity.PLAYER.name)
                    {
                        GUIManager.getGUIinstance().swapModes(GUIModeEnum.DSCREEN);
                        Game.getGame().setIsRunning(false);
                    }


                }

            }
        }
    }
}
