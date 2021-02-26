package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;

/**
 * Component: Position
 *  - Defines the world position of the GameObject
 */
public class Position extends Component
{

    private Vector2f position;  //Vector Position
    private GameObject me;      //The Gameobject the position is defined for

    /**
     * Public Constructor to create a Position Component
     * @param position The Vector Position
     * @param me    The GameObject the component is attached to
     */
    public Position(Vector2f position, GameObject me)
    {
        this.position = position;
        this.me = me;
    }

    /**
     * Method to get the position
     * @return Vector2f Position
     */
    public Vector2f getPosition()
    {
        return position;
    }

    /**
     * Method to update the position
     *  - Needs to check and update the gameObject in the quadtree
     * @param position New Vector Position
     */
    public void updatePosition(Vector2f position)
    {
        EntityManager.getEntityManagerInstance().updateLeaf(me,position);
        this.position = position;
    }

    /**
     * Method to update the position via another Vector by adding them
     * @param position1 Vector to add to the current Position Vector
     */
    public void addPosition(Vector2f position1){
        Vector2f position2 = Vector2f.add(this.position, position1);
        updatePosition(position2);
    }

    /**
     * Sets the GameObject target
     * @param me GameObject the component is attached to
     */
    public void setMe(GameObject me) {
        this.me = me;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Position(position,me);       //infinite loop?
    }
}
