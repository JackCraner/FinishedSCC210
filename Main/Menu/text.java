package Main.Menu;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
/**
 * This is the class that will
 * allow us to add text to our pages
 *
 */
public class text extends Text{
    int id;
    public text(int id, Vector2f position, Font font, Color col) {

        this.id = id;
        this.setPosition(position);
        this.setFont(font);
        this.setColor(col);

    }
    public int getId() {
        return id;
    }
}