package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.SpecialComponents.ArmorContainer;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Components.StatComponents.EffectComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.ArmorEnum;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIArmorBar;
import Main.Game.Managers.EntityManager;
import Main.Game.Managers.GUIManager;

public class ArmorGameSystem extends GameSystem
{

    private static ArmorGameSystem systemInstance = new ArmorGameSystem();

    public static ArmorGameSystem getSystemInstance() {
        return systemInstance;
    }

    private ArmorGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(ArmorContainer.class, Inputs.class));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject g : getGameObjectList())
        {
            if (BitMasks.checkIfContains(g.getBitmask(), CollisionEvent.class))
            {
                for (GameObject collisionObject :g.getComponent(CollisionEvent.class).getG())
                {
                    if (BitMasks.checkIfContains(collisionObject.getBitmask(), Pickup.class))
                    {
                        Pickup p = collisionObject.getComponent(Pickup.class);
                        ArmorContainer a = g.getComponent(ArmorContainer.class);
                        if (p.getItemType() instanceof ArmorEnum.ArmorPiece && g.getComponent(Inputs.class).pickUP)
                        {
                            System.out.println(p.getItemType());
                            GameObject drop = new GameObject(Entity.PLACEHOLDER);
                            if (BitMasks.checkIfContains(collisionObject.getBitmask(),GivenEffect.class))
                            {
                                if (BitMasks.checkIfContains(g.getBitmask(), EffectComponent.class))
                                {
                                    g.getComponent(EffectComponent.class).addEffect(collisionObject.getComponent(GivenEffect.class).getEffectToGiven());
                                }

                            }
                            switch((ArmorEnum.ArmorPiece)p.getItemType())
                            {
                                case HELMET:
                                    if (a.getHelmet() != null)
                                    {
                                        dropArmor(a.getHelmet(),g);
                                    }
                                    a.setHelmet(collisionObject);
                                    break;
                                case CHEST:
                                    if (a.getChest() != null)
                                    {
                                        dropArmor(a.getChest(),g);
                                    }
                                    a.setChest(collisionObject);
                                    break;
                                case LEGGINGS:
                                    if (a.getLegs() != null)
                                    {
                                        dropArmor(a.getLegs(),g);
                                    }
                                    a.setLegs(collisionObject);
                                    break;
                            }
                            updateArmor(g);
                            GUIManager.getGUIinstance().GUIUpdate(GUIArmorBar.class);
                            System.out.println(g.getName() +"   " +  (a.getHelmet()!= null ? a.getHelmet().getName() : "NULL")  + "   " + (a.getChest() != null ? a.getChest().getName() : "NULL") + "   " +(a.getLegs() != null ? a.getLegs().getName() : "NULL"));
                            EntityManager.getEntityManagerInstance().removeGameObject(collisionObject);
                            collisionObject.removeComponent(Position.class);
                }



                    }

                }
            }
        }
    }

    public void dropArmor(GameObject a, GameObject g)
    {
        a.addComponent(new Position(g.getComponent(Position.class).getPosition(),a));
        a.getComponent(Collider.class).setAvoidTime(g,1f);
        a.getComponent(Pickup.class).getFloorTimer().restart();
        EntityManager.getEntityManagerInstance().addGameObject(a);

    }

    public void updateArmor(GameObject g)
    {
        if (BitMasks.checkIfContains(g.getBitmask(), Armor.class))
        {
            int value =0;
            for(GameObject armor: g.getComponent(ArmorContainer.class).getArmor())
            {
                if (armor != null)
                {
                    value += armor.getComponent(Armor.class).getArmorValue();
                }
            }

            g.getComponent(Armor.class).setStat(value);
        }
    }
}
