package Main.Game.ECS.Components.StatComponents;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.Component;

import java.util.ArrayList;

public class EffectComponent extends Component
{
    //stores array of effects to add
    private ArrayList<Effects> newEffects = new ArrayList<>();
    private ArrayList<Effects> effectsArrayList = new ArrayList<>();
    public EffectComponent()
    {

    }

    public void addEffect(Effects effect)
    {
        newEffects.add(effect);
    }

    public ArrayList<Effects> getNewEffects() {
        return newEffects;
    }

    public ArrayList<Effects> getEffectsArrayList() {
        return effectsArrayList;
    }

    @Override
    public Component clone()
    {
        return new EffectComponent();
    }
    //flag


}
