package Main.Game.ECS.Components.StatComponents;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.Component;

import java.util.ArrayList;

/**
 * Component: Effects Component
 * - Defines Effects acting on the GameObject
 */
public class EffectComponent extends Component
{
    //stores array of effects to add
    private ArrayList<Effects> newEffects = new ArrayList<>();  //Array Of effects added this frame
    private ArrayList<Effects> effectsArrayList = new ArrayList<>();   // Array of effects already acting on the GameObject

    /**
     * Public Constructor to create a new EffectComponent Component
     */
    public EffectComponent()
    {

    }

    /**
     * Method to add a new Effect to the component
     * @param effect The effect to add (Reference Effects in Datatypes)
     */
    public void addEffect(Effects effect)
    {
        newEffects.add(effect);
    }

    /**
     * Method to get all new Effects in the component
     * @return ArrayList of Effects
     */
    public ArrayList<Effects> getNewEffects() {
        return newEffects;
    }

    /**
     * Method to get all the existing effects
     * @return ArrayList of Effects
     */
    public ArrayList<Effects> getEffectsArrayList() {
        return effectsArrayList;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone()
    {
        return new EffectComponent();
    }
    //flag


}
