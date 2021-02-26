package Main.Game.ECS.Components.ItemComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;

/**
 * Component: Projectile
 *  - Gives the GameObject a Projectile Component which can be fired
 *
 *
 */
public class Projectile extends Component
{
    private final GameObject projectile;   //GameObject which is released as a projectile
    private float cooldownTime; //Cooldown time on the ability to fire the projectile
    private float cooldownValue = 0;    // Current value of the cooldown time

    /**
     * Public Constructor for creating a projectile Component
     * @param projectile The GameObject which is launched
     * @param cooldownTime  The cooldown time of the projectile
     */
    public Projectile(GameObject projectile, float cooldownTime)
    {
        this.projectile= projectile;
        this.cooldownTime = cooldownTime;
    }

    /**
     * Method to get the projectile
     * @param position Vector2f Position to spawn the projectile at
     * @return  A GameObject which is a copy of the held Projectile with the given position
     */
    public GameObject getProjectile(Vector2f position)
    {
        cooldownValue = cooldownTime;
        GameObject newprojectile = projectile.clone();
        newprojectile.addComponent(new Position(position,newprojectile));
        return newprojectile;
    }

    /**
     * Method to update the cooldown time on the projectile
     * @param dt the time between frames
     */
    public void reduceCoolDown(float dt)
    {
        if (cooldownValue >0)
        {
            cooldownValue -= dt;
        }
    }

    /**
     * Method to see if the projectile is ready to be fired
     * @return  True if cooldown time is less than or equal to 0
     */
    public Boolean isReady()
    {
        return (cooldownValue <= 0);
    }

    /**
     * Method to clone the component
     * @return a copy of the component
     */
    @Override
    public Component clone() {
        return new Projectile(projectile,cooldownTime);
    }
}