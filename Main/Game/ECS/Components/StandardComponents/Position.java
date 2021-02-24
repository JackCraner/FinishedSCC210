package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;

public class Position extends Component
{

    private Vector2f position;
    private GameObject me;
    public Position(Vector2f position, GameObject me)
    {
        this.position = position;
        this.me = me;
    }
    public Vector2f getPosition()
    {
        return position;
    }

    public void updatePosition(Vector2f position)
    {
        EntityManager.getEntityManagerInstance().updateLeaf(me,position);
        this.position = position;
    }

    public void addPosition(Vector2f position1){
        Vector2f position2 = Vector2f.add(this.position, position1);
        updatePosition(position2);
    }

    public void setMe(GameObject me) {
        this.me = me;
    }

    @Override
    public Component clone() {
        return new Position(position,me);       //infinite loop?
    }
}
