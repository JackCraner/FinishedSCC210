package Main.Game.QuadTree;

import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class QTLeaf
{
    ArrayList<GameObject> objectList = new ArrayList<>();


    public QTLeaf()
    {

    }

    public void addObjectToLeaf(GameObject g)
    {
        objectList.add(g);
    }
    public void removeObjectFromLeaf(GameObject g)
    {
        objectList.remove(g);
    }

    public ArrayList<GameObject> getObjectList() {
        return objectList;
    }
    public void wipeLeaf()
    {
        objectList.clear();
    }
}
