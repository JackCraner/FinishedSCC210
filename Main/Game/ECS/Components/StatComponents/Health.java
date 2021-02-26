package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

/**
 * Component: Health
 *  - Defines the Health value of the GameObject
 */
public class Health extends Component implements IsStat {
    float baseHealth;
    float maxHealth;
    float currentHealth;

    /**
     * Public Constructor to create a new Health Component
     * @param health the total health the GameObject should have
     */
    public Health(float health)
    {
        this.maxHealth = health;
        this.baseHealth = health;
        this.currentHealth = health;
    }

    /**
     * Method to change current health, Used when taking damage or healing health
     * @param x the new current Health
     */
    public void adjustHealth(float x)
    {
        currentHealth +=x;
    }


    /**
     * Method to get maxHealth
     * @return maxHealth (float)
     */
    public float getMaxHealth()
    {
        return maxHealth;
    }

    /**
     * Method to change the maxHealth
     * @param maxHealth the new MaxHealth
     */
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Health(maxHealth);
    }

    /**
     * Method to get the current Health value
     * @return current Health (float)
     */
    @Override
    public float getStat() {
        return currentHealth;
    }

    /**
     * Method to set the value of currentHealth
     * @param value new value of current Health (float)
     */
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

    /**
     * Method to get the current Health
     * @return current Health base (float)
     */
    @Override
    public float getBase() {
        return currentHealth;
    }

    /**
     * Method to get max Health
     * @return max health (float)
     */
    public float getMax()
    {
        return maxHealth;
    }
}
