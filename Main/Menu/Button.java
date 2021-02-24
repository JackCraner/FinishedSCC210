package Main.Menu;


import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * This is the class that will
 * allow us to make buttons
 *
 * also sets up a check if said button is being hovered over
 * so that we can check if its being pressed
 */
public class Button extends RectangleShape{
    int id;
    public Button(int id, Vector2f position, float width, float height)
    {

        super(new Vector2f(width,height));
        this.id = id;
        this.setPosition(position);

    }
    public int getId() {
        return id;
    }

    public boolean isHovered(Vector2i mousePos)
    {
        if (this.getGlobalBounds().contains(new Vector2f(mousePos)))
        {
            return true;
        }
        return false;
    }


}
