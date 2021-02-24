package Main.DataTypes;

public class Avoids
{
    private int gameObjectUIDTOAVOID;
    private float avoidTime;
    private boolean avoidTimeRequired = true;
    public Avoids(int gameObjectUIDTOAVOID, float totalAvoidTime)
    {
        this.gameObjectUIDTOAVOID = gameObjectUIDTOAVOID;
        this.avoidTime = totalAvoidTime;
    }
    public Avoids(int gameObjectUIDTOAVOID)
    {
        this.gameObjectUIDTOAVOID = gameObjectUIDTOAVOID;
        this.avoidTimeRequired = false;
    }

    public void avoidTimeClock(float dt)
    {
        if (avoidTimeRequired)
        {
            avoidTime -= dt;
        }

    }
    public boolean avoidFinished()
    {
        return (avoidTimeRequired && avoidTime < 0 );

    }
    public int getGameObjectUIDTOAVOID()
    {
        return gameObjectUIDTOAVOID;
    }


}
