package Main.Game.ECS.Components.ItemComponents;

import Main.Game.ECS.Components.Component;

public class LifeSpan extends Component
{
    private final float lifespanTotal;          //in seconds
    private float currentLifeSpan =0;
    public LifeSpan(float lifespanTotal)
    {
        this.lifespanTotal = lifespanTotal;
    }
    public Boolean checkIfFinished()
    {
        return (lifespanTotal < currentLifeSpan);
    }
    public void countLifeSpan(float dt)
    {
        if (lifespanTotal > currentLifeSpan)
        {
            currentLifeSpan +=dt;
        }
    }
    @Override
    public Component clone() {
        return new LifeSpan(lifespanTotal);
    }
}
