package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;

public class Animation extends Component
{
    public byte spriteSheetDirection =0;
    public byte spriteSheetAnimation =0;

    private float animationTime; //time between animationFrames
    private float animationClock;
    public int animationCounter =0;
    public Animation(float animationTime)
    {
        this.animationTime = animationTime;

    }

    public void updateClock(float dt)
    {
        animationClock += dt;
    }
    public void resetClock()
    {
        animationClock =0;
    }
    public void finishClock()
    {
        animationClock = animationTime;
    }
    public boolean checkClock()
    {
        return (animationClock >= animationTime);
    }
    public void setAnimationTime(float time)
    {
        animationTime = time;
    }

    @Override
    public Component clone() {
        return new Animation(animationTime);
    }
}
