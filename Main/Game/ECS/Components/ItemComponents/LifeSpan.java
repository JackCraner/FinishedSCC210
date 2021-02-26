package Main.Game.ECS.Components.ItemComponents;

import Main.Game.ECS.Components.Component;

/**
 * Component: Lifespan
 *  - Defines how long a GameObject should be alive
 *
 *  Uses:
 *      - Particle Effects
 *      - Projectiles
 *      - Items on the floor
 */
public class LifeSpan extends Component
{
    private final float lifespanTotal;          //in seconds
    private float currentLifeSpan =0;       //Time left


    /**
     * Public Constructor To create a given LifeSpan
     * @param lifespanTotal The total time the GameObject should be alive
     */
    public LifeSpan(float lifespanTotal)
    {
        this.lifespanTotal = lifespanTotal;
    }

    /**
     * Method to check if Life span is finished
     * @return True if time has expired
     */
    public Boolean checkIfFinished()
    {
        return (lifespanTotal < currentLifeSpan);
    }

    /**
     * Method to update the LifeSpan clock
     * @param dt the time between frames
     */
    public void countLifeSpan(float dt)
    {
        if (lifespanTotal > currentLifeSpan)
        {
            currentLifeSpan +=dt;
        }
    }

    /**
     * Method to clone the component
     * @return A copy of the Component
     */
    @Override
    public Component clone() {
        return new LifeSpan(lifespanTotal);
    }
}
