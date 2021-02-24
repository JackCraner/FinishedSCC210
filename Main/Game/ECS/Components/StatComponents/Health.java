package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

public class Health extends Component implements IsStat {
    float baseHealth;
    float maxHealth;
    float currentHealth;

    public Health(float health)
    {
        this.maxHealth = health;
        this.baseHealth = health;
        this.currentHealth = health;
    }
    public void adjustHealth(float x)
    {
        currentHealth +=x;
    }
    public float getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public Component clone() {
        return new Health(maxHealth);
    }

    @Override
    public float getStat() {
        return currentHealth;
    }

    @Override
    public void setStat(float value) {
        if (value > maxHealth)
        {
            currentHealth = maxHealth;
        }
        else
        {
            currentHealth = value;
        }


    }

    @Override
    public float getBase() {
        return currentHealth;
    }


    public float getMax()
    {
        return maxHealth;
    }
}
