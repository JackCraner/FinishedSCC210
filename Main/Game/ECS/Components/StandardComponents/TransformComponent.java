package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.system.Vector2f;

public class TransformComponent extends Component
{
    private Vector2f size;
    private float direction = 0;
    private Vector2f scale = new Vector2f(1,1);
    private boolean isFacingRight = false;

    public TransformComponent(Vector2f size)
    {
        this.size = size;
    }

    public Vector2f getSize() {
        return size;
    }

    public float getDirection() {
        return direction;
    }
    public void setDirection(float direction)
    {
        this.direction = direction;
    }
    public Vector2f getScale()
    {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }


    @Override
    public Component clone() {
        TransformComponent t = new TransformComponent(size);
        t.setDirection(direction);
        t.setDirection(direction);
        t.setScale(scale);
        return t;
    }
}
