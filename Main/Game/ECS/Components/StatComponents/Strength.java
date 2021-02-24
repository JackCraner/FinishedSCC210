package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

public class Strength extends Component implements IsStat{
    float strength;
    float baseStrength;
   public Strength(float strength)
   {
       this.baseStrength = strength;
      this.strength = strength;
   }

    @Override
    public Component clone() {
        return new Strength(strength);
    }

    @Override
    public float getStat() {
        return strength;
    }

    @Override
    public void setStat(float value) {
        strength = value;
    }

    @Override
    public float getBase() {
        return baseStrength;
    }
}
