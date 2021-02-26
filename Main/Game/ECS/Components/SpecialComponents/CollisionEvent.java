package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

/**
 * Component: CollisionEvent
 *  - Stores Collision Events for a GameObject in an ArrayList
 *
 *  - Used as a messaging device to talk between GameSystems
 */
public class CollisionEvent extends Component
{
    private ArrayList<GameObject> g;    //ArrayList of GameObjects/ Collisions

    /**
     * Public Constructor to create a collisionEvent
     */
    public CollisionEvent()
    {
        g = new ArrayList<>();
    }

    /**
     * Method to get the ArrayList of collisions that have occured on the GameObject this frame
     * @return
     */
    public ArrayList<GameObject> getG() {
        return g;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return null;
    }
}
