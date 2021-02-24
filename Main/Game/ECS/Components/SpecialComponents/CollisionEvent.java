package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class CollisionEvent extends Component
{
    private ArrayList<GameObject> g;
    public CollisionEvent()
    {
        g = new ArrayList<>();
    }

    public ArrayList<GameObject> getG() {
        return g;
    }

    @Override
    public Component clone() {
        return null;
    }
}
