package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

/**
 * Component: Armor
 *  - Defines the Armor values for GameObjects
 *  - Both for Armor pieces and Entities Wearing Armor
 */
public class Armor extends Component implements IsStat {
    float baseArmor;
    float armorValue;


    /**
     * Public Constructor for making an Armor Component
     * @param armorValue Strength of the Armor (Values between 0 - 100)
     */
    public Armor(float armorValue) {

        //max armor is 100
        if (armorValue < 100) {
            this.armorValue = armorValue;
            this.baseArmor = armorValue;
        } else {
            this.armorValue = 100;
            this.baseArmor = 100;
        }

    }

    /**
     *  Method to get the Armor value
     * @return float of the armor value
     */
    public float getArmorValue() {
        return armorValue;
    }

    /**
     * Method to get the stat
     * @return Float of armor converted to a percentage (0 - 1)
     */
    @Override
    public float getStat() {
        return (1 - (armorValue / 100));
    }

    /**
     * Method to set the value of Armor
     * @param value the new Armor value (float)
     */
    @Override
    public void setStat(float value) {
        armorValue = value;
    }

    /**
     * Method to get the base Armor value (Before effects)
     * @return float of base Armor
     */
    @Override
    public float getBase() {
        return baseArmor;
    }

    /**
     * Method to clone the component
     * @return Copy of the component
     */
    @Override
    public Component clone() {
        return new Armor(armorValue);
    }

}
