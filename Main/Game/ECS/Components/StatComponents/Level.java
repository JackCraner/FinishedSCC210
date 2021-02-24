package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

public class Level extends Component implements IsStat
{
    private float currentLevel;
    private float currentXP;
    private float addingXP;
    public Level(float level)
    {
        this.currentLevel = level;
        this.currentXP = 0;
    }

    public float getCurrentLevel() {
        return currentLevel;
    }
    public void levelUP()
    {
        currentLevel++;
        currentXP =0;
    }

    public float getCurrentXP() {
        return currentXP;
    }
    public void addXP(float xp)
    {
        addingXP += xp;
    }

    public void setCurrentXP(float currentXP) {
        this.currentXP = currentXP;
    }

    public float getAddingXP() {
        return addingXP;
    }
    public void clearAddingXP()
    {
        addingXP =0;
    }

    @Override
    public Component clone() {
        return new Level(currentLevel);
    }

    @Override
    public float getStat() {
        return currentLevel;
    }

    @Override
    public void setStat(float value) {
        this.currentLevel = value;
    }

    @Override
    public float getBase() {
        return 0;
    }
}
