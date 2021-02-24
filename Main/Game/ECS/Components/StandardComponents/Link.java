package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

public class Link extends Component
{
    GameObject g;

    public Link(GameObject g)
    {
        this.g = g;
    }

    public GameObject getG() {
        return g;
    }

    @Override
    public Component clone() {
        return new Link(g);
    }
}
