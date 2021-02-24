package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.ItemComponents.Projectile;
import Main.Game.ECS.Components.SpecialComponents.BossAttack;
import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.WeaponEnum;
import Main.Game.Managers.EntityManager;

import java.util.Random;

public class BossAttacksGameSystem extends GameSystem
{
    private static BossAttacksGameSystem gameSystem = new BossAttacksGameSystem();

    public static BossAttacksGameSystem getGameSystem() {
        return gameSystem;
    }

    private BossAttacksGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(BossAttack.class));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {

            if (g.getComponent(BossAttack.class).timer.getElapsedTime().asSeconds() > 0.2)
            {
                GameObject spawn = Blueprint.enemyprojectile(WeaponEnum.Projectile.FIREBALL,g);

                spawn.getComponent(TransformComponent.class).setDirection(new Random().nextInt(300));
                spawn.getComponent(Collider.class).setAvoidTime(g);
                spawn.addComponent(new Position(g.getComponent(Position.class).getPosition(),spawn));
                spawn.addComponent(new Damage(10));

                EntityManager.getEntityManagerInstance().addGameObject(spawn);
                g.getComponent(BossAttack.class).timer.restart();
            }

            /*


            if (BitMasks.checkIfContains(mainHand.getBitmask(), Damage.class))
            {
                spawn.addComponent(mainHand.getComponent(Damage.class).clone());
            }
            if (BitMasks.checkIfContains(mainHand.getBitmask(), GivenEffect.class))
            {
                spawn.addComponent(mainHand.getComponent(GivenEffect.class).clone());
            }
            EntityManager.getEntityManagerInstance().addGameObject(spawn);

             */
        }


    }
}
