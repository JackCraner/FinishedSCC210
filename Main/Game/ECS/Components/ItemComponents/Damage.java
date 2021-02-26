package Main.Game.ECS.Components.ItemComponents;

import Main.Game.ECS.Components.Component;

/**
 * Component: Damage
 * - Defines the Damage a weapon or item should deal
 *
 */
public class Damage extends Component
{
    private float damage;  //Damage value

    /**
     * Public Constructor to define Damage value
     * @param damage Float Damage value
     */
    public Damage(float damage)
    {
        this.damage = damage;
    }

    /**
     * Method to return the Damage value
     * @return Damage value
     */
    public float getDamage()
    {
        return damage;
    }

    /**
     * Method to set Damage value
     * @param x new Damage value
     */
    public void setDamage(float x)
    {
        this.damage = x;
    }

    /**
     * Method to clone Component
     * @return Copy of damage component
     */
    @Override
    public Component clone() {
        return new Damage(damage);
    }
}
