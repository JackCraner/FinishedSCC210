package Main.Game.ECS.Components.StatComponents;

import Main.Game.ECS.Components.Component;

/**
 * Component: Level
 *  - Defines the current Level of Entities
 */
public class Level extends Component implements IsStat
{
    private int currentLevel;
    private float currentXP;
    private float addingXP;

    /**
     * Public constructor to create a new Level Component
     * @param level Inital Level of the gameObject as int
     */
    public Level(int level)
    {
        this.currentLevel = level;
        this.currentXP = 0;
    }

    /**
     * Method to get the current Level
     * @return Current Level
     */
    public float getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Method to Level up the GameObject
     * - Increases current Level and sets XP back to 0
     */
    public void levelUP()
    {
        currentLevel++;
        currentXP =0;
    }

    /**
     * Method to get the current XP value
     * @return current experience value
     */
    public float getCurrentXP() {
        return currentXP;
    }

    /**
     * Method used to update and increase the XP value
     *  - Used when gaining experience via killing another GameObject with a Level Component
     * @param xp The XP to add to the XP total
     */
    public void addXP(float xp)
    {
        addingXP += xp;
    }

    /**
     * Method to set the XP value to new value
     * @param currentXP new XP vaue
     */
    public void setCurrentXP(float currentXP) {
        this.currentXP = currentXP;
    }

    /**
     * Method which gets the current XP total
     * @return XP total value
     */
    public float getAddingXP() {
        return addingXP;
    }

    /**
     * Method which sets XP total to 0
     *  - Used when leveling up
     */
    public void clearAddingXP()
    {
        addingXP =0;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Level(currentLevel);
    }

    /**
     * Method to get the stat value
     * @return Stat value
     */
    @Override
    public float getStat() {
        return currentLevel;
    }

    /**
     * Method to set the stat value
     * @param value new value of the stat as a float
     */
    @Override
    public void setStat(float value) {
        this.currentLevel = (int)value;
    }

    /**
     * Method to get the base value of the stat
     * @return 0
     */
    @Override
    public float getBase() {
        return 0;
    }
}
