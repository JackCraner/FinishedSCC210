package Main.Game.ECS.Components.ItemComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;

public class Projectile extends Component
{
    private final GameObject projectile;
    private float cooldownTime;
    private float cooldownValue = 0;

    public Projectile(GameObject projectile, float cooldownTime)
    {
        this.projectile= projectile;
        this.cooldownTime = cooldownTime;
    }


    public GameObject getProjectile(Vector2f position)
    {
        cooldownValue = cooldownTime;
        GameObject newprojectile = projectile.clone();
        newprojectile.addComponent(new Position(position,newprojectile));
        return newprojectile;
    }
    public void reduceCoolDown(float dt)
    {
        if (cooldownValue >0)
        {
            cooldownValue -= dt;
        }
    }
    public Boolean isReady()
    {
        return (cooldownValue <= 0);
    }

    @Override
    public Component clone() {
        return new Projectile(projectile,cooldownTime);
    }
}