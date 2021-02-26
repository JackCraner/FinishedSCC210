package Main.DataTypes;

/**
 * A data structure that defines that two objects should not interact with each other
 *
 * --- Use cases
 *  - Player drops an item so they shouldnt be able to pick it up for a brief time
 *  - Projectiles should hit a GameObject only once so after inital hit, the projectile will avoid that GameObject
 *
 */
public class Avoids
{
    private int gameObjectUIDTOAVOID;
    private float avoidTime;
    private boolean avoidTimeRequired = true;

    /**
     * Public Constructor for Avoids
     * @param gameObjectUIDTOAVOID  Unique ID of the gameObject to avoid
     * @param totalAvoidTime  Duration of the avoid in Seconds
     */
    public Avoids(int gameObjectUIDTOAVOID, float totalAvoidTime)
    {
        this.gameObjectUIDTOAVOID = gameObjectUIDTOAVOID;
        this.avoidTime = totalAvoidTime;
    }

    /**
     * Constructor for a permanent avoid
     * @param gameObjectUIDTOAVOID Unique ID of the gameObject to avoid
     */
    public Avoids(int gameObjectUIDTOAVOID)
    {
        this.gameObjectUIDTOAVOID = gameObjectUIDTOAVOID;
        this.avoidTimeRequired = false;
    }

    /**
     * Method to update the time left on the avoid
     * @param dt  The time interval from the last frame
     */
    public void avoidTimeClock(float dt)
    {
        if (avoidTimeRequired)
        {
            avoidTime -= dt;
        }

    }

    /**
     * Method to check if avoid is finished
     * @return Will return true is time is below 0 : Will return false if its a permanent avoid and thus never finishes
     */
    public boolean avoidFinished()
    {
        return (avoidTimeRequired && avoidTime < 0 );

    }

    /**
     * Method to get the ID of the gameObject they are avoiding
     * @return Int of the ID of the gameObject
     */
    public int getGameObjectUIDTOAVOID()
    {
        return gameObjectUIDTOAVOID;
    }


}
