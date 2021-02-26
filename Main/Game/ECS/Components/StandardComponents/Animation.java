package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;

/**
 * Component: Animation
 *  - Defines that a gameobject should have an animation
 *
 *  Uses:
 *      - GameObjects walking
 *      - Armor movement
 *
 */
public class Animation extends Component
{
    public byte spriteSheetDirection =0;    //Direction of the sprite (between 1 and 3)
    public byte spriteSheetAnimation =0;    //Current Slide of the animation (Between 1 and 3)

    private float animationTime; //time between animationFrames
    private float animationClock; //Timer to keep track of time between animation slides
    public int animationCounter =0;

    /**
     * Public Constructor to create a new Animation component
     * @param animationTime The time between animation slides
     */
    public Animation(float animationTime)
    {
        this.animationTime = animationTime;

    }

    /**
     * Method to update the animation clock
     * @param dt the time between game frames
     */
    public void updateClock(float dt)
    {
        animationClock += dt;
    }


    /**
     * Method to reset the value of the clock to 0
     */
    public void resetClock()
    {
        animationClock =0;
    }

    /**
     * Method to set the clock to finish instantly
     */
    public void finishClock()
    {
        animationClock = animationTime;
    }

    /**
     * Method to see if the clock is finished and the next slide of the animation can be shown
     * @return Boolean
     */
    public boolean checkClock()
    {
        return (animationClock >= animationTime);
    }

    /**
     * Set a new animation time (time between animation slides)
     *  - This is used when a GameObject speeds up, their animation is also sped up
     * @param time the new time (float)
     */
    public void setAnimationTime(float time)
    {
        animationTime = time;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Animation(animationTime);
    }
}
