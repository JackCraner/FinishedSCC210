package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

/**
 * Component: Link
 *  - Defines any links between the GameObject and another
 *
 *  Uses:
 *      Items in backpacks are linked to their Holder
 *      Projectiles are linked to their Shooter
 */
public class Link extends Component
{
    GameObject g; //GameObject to link to

    /**
     * Public Constructor to create a Link Component
     * @param g GameObject to attach to
     */
    public Link(GameObject g)
    {
        this.g = g;
    }

    /**
     * Method to get the GameObject linked to
     * @return GameObject
     */
    public GameObject getG() {
        return g;
    }

    /**
     * method to clone the component
     * @return the copy of the component
     */
    @Override
    public Component clone() {
        return new Link(g);
    }
}
