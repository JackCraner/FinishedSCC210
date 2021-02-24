package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

public class Mana extends Component implements IsStat
{
    float baseMana;
    float maxMana;
    float currentMana;

    public Mana(float mana)
    {
        this.currentMana = mana;
        this.maxMana = mana;
        this.baseMana = mana;
    }
    public void adjustMana(float x)
    {
        currentMana +=x;
    }
    public float getMaxMana() {
        return maxMana;
    }


    @Override
    public Component clone() {
        return new Mana(maxMana);
    }

    @Override
    public float getStat() {
        return currentMana;
    }

    @Override
    public void setStat(float value) {
        maxMana = value;
    }

    @Override
    public float getBase() {
        return baseMana;
    }
}
