package Main.Game.ECS.Systems;


import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public abstract class GameSystem
{
    //DESIGN PATTERN
    /*
        - GameSystems should always extend this abstract class
        - GameSystems should always be SINGLETONS
                -Constructor is private
        - GameSystems should always SetBitMaskRequirements in its Private Constructor

        -- Whenever a new GameSystem is made, add it to the GameSystemList in SYSTEMMANAGER

        ---- Never make a new GameSystem with the same requirements as another GameSystem
                   - For Example if you wanna make a System which only requires a Movement Components, well a system like that already
                     exists (MovementGameSystem), so just combine the logic in there

        ---- Avoid Using Inheritance when making GameSystems

        ---- SYSTEMS SHOULD ONLY MANIPULATE COMPONENTS WITHIN REQUIREMENTS




     */


    private int bitMaskRequirement =0;

    private ArrayList<GameObject> gameObjectList = new ArrayList<>();

    public ArrayList<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    /**
     * Adds a GameObject to the system
     * @param c the GameObject being added
     */
    public void addGameObject(GameObject c)
    {
        gameObjectList.add(c);
    }
    public void refreshGameObjects()
    {
        gameObjectList.clear();
    }

    @Override
    public String toString()
    {
        String s;
        s = this.getClass().toString() + "\n";
        for (int i=0; i< gameObjectList.size();i++)
        {
            s += ("Object " + i + " = " + gameObjectList.get(i).toString() + "\n");

        }
        s += "Total Number of Objects: " +  gameObjectList.size() + "\n";
        return s;

    }

    public int getBitMaskRequirement() {
        return bitMaskRequirement;
    }

    public void setBitMaskRequirement(int bitMaskRequirement) {
        this.bitMaskRequirement = bitMaskRequirement;
    }

    /**
     * Called every Frame
     * @param dt
     */
    public abstract void update(float dt);


}

//Notes on the game system
/*
-- make this abstract interface ensure all the gamesystems are singletons

-- Change the ArrayList<Class<? extends Component> to a more rigid structure of the classes the systems want so reduce downcasting

-- ensure all GameSystems are added to the systems array in level
 */
