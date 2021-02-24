package Main.Game.ECS.Components.ItemComponents;

import Main.Game.ECS.Components.Component;

public class Damage extends Component
{
    private float damage = 10;
    public Damage(float damage)
    {
        this.damage = damage;
    }
    public float getDamage()
    {
        return damage;
    }
    public void setDamage(float x)
    {
        this.damage = x;
    }

    @Override
    public Component clone() {
        return new Damage(damage);
    }
}
