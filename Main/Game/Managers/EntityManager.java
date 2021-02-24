package Main.Game.Managers;

import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.QuadTree.QTRoot;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.ArrayList;


/**
 * ENTITYMANAGER
 *
 * Handles all GameObjects in the game
 *  -All Adding/Removing and Reading of GameObjects should go through this class
 * Handles these GameObjects in a QuadTree structure for fast spatial recall
 *
 *
 * ONLY GLOBAL PIXEL POSITIONS PASSED IN
 *      - stops confusion of whether a Vector referred to an ArrayIndex or Pixel Location
 */

public class EntityManager
{
    private static EntityManager entityManagerInstance = new EntityManager();
    private static QTRoot quadTree = QTRoot.getQuadTree();
    private int UIDCounter=1;

    public static EntityManager getEntityManagerInstance() {
        return entityManagerInstance;
    }


    /**
     * Converts a global pixel position into the Quadtree Leaf that holds that position
     * @param position global pixel position
     * @return A Vector2i defining the Array position within the quadtree
     */
    public static Vector2i convertGlobalPositionTOLEAF(Vector2f position)
    {
        return new Vector2i(Math.floorDiv((int)position.x,quadTree.LEAFSIZE), Math.floorDiv((int)position.y,quadTree.LEAFSIZE));
    }

    /**
     * Adds a GameObject to the quadTree
     * @param g the GameObject to be added
     */
    public void addGameObject(GameObject g, Vector2f pos)
    {
        if ((g.getBitmask() & BitMasks.produceBitMask(Position.class)) != 0)
        {
            Vector2i objectPos = convertGlobalPositionTOLEAF(pos);
            if(objectPos.x >= quadTree.NUMLEAFX || objectPos.x < 0|| objectPos.y >= quadTree.NUMLEAFY|| objectPos.y < 0)
            {

            }
            else
            {
                if (g.getUID() == 0)
                {
                    g.setUID(UIDCounter);
                    UIDCounter ++;
                }
                quadTree.addGameObject(g, objectPos);
            }

        }
        else
        {
            System.out.println("Object has no Position :: Adding to QUADTREE FAILED");
        }

    }
    public void addGameObject(GameObject g)
    {
        addGameObject(g,g.getComponent(Position.class).getPosition());
    }

    public void removeGameObject(GameObject g)
    {
        if ((g.getBitmask() & BitMasks.produceBitMask(Position.class)) != 0)
        {
            Vector2i objectPos = convertGlobalPositionTOLEAF(g.getComponent(Position.class).getPosition());
            if(objectPos.x >= quadTree.NUMLEAFX || objectPos.x < 0|| objectPos.y >= quadTree.NUMLEAFY|| objectPos.y < 0)
            {

            }
            else
            {
                quadTree.removeGameObject(g,objectPos);
            }

        }
        else{
            System.out.println("Object has no Position :: Removing from QUADTREE FAILED");
        }
    }


    /**
     * Adds an Array of GameObjects to the quadTree
     * @param gArray the Array of GameObjects to be added
     */
    public void addGameObject(ArrayList<GameObject> gArray)
    {
        for (GameObject g:gArray)
        {
            addGameObject(g);
        }

    }


    /**
     * Returns all the GameObjects within a certain Leaf of the QuadTree
     *
     *        This Method is private because the position parameter is defined as an Index Value --- NOT A GLOBAL PIXEL POSITIOn ---
     *        thus this method shouldnt be called outside of the EntityManager
     *
     *
     * @param pos Index of the Leaf wanted
     * @return ArrayList of GameObjects found within the leaf
     */
    private ArrayList<GameObject> getGameObjectsInLeaf(Vector2i pos)
    {

        if ((pos.x < quadTree.NUMLEAFX && pos.x >= 0) && (pos.y < quadTree.NUMLEAFY && pos.y >= 0))
        {
            return quadTree.getLeaf(pos).getObjectList();
        }
        return new ArrayList<>();
    }

    /**
     * Returns an ArrayList of GameObjects within a given range around a point
     * @param pos Central Position in pixels
     * @param range Range in pixels
     * @return The ArrayList of GameObjects
     */
    public ArrayList<GameObject> getGameObjectInVicinity(Vector2f pos, float range)
    {

        int leafRange = (int)Math.ceil(range/quadTree.LEAFSIZE);


        ArrayList<GameObject> newList = new ArrayList<>();
        Vector2i cPos = convertGlobalPositionTOLEAF(pos);
        for (int a = -leafRange; a <= leafRange; a++)
        {
            for (int b = -leafRange; b <= leafRange; b++)
            {
                newList.addAll(getGameObjectsInLeaf(new Vector2i(cPos.x + a, cPos.y + b)));
            }
        }

        return newList;
    }

    /**
     * If an Object moves/Updates its Position, we need to check if it has entered a new Leaf of the QuadTree and update its leafPosition
     * @param g The GameObject we testing
     * @param newPosition The new Position the GameObject is moving too
     */
    public void updateLeaf(GameObject g, Vector2f newPosition)
    {
        if ((g.getBitmask() & BitMasks.produceBitMask(Position.class)) != 0)
        {
            if (!(convertGlobalPositionTOLEAF(g.getComponent(Position.class).getPosition()).equals(convertGlobalPositionTOLEAF(newPosition))))
            {
               removeGameObject(g);
               addGameObject(g,newPosition);
            }
        }
        else
        {
            System.out.println("Object has no Position :: Updating LEAF FAILED");
        }

    }

    public void clearAllEntities()
    {
        quadTree.clearTree();
    }






}
