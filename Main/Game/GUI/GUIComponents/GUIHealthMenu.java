package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Health;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

import java.io.File;
import java.nio.file.Paths;

/**
 * The Health value in the pause Menu
 */
public class GUIHealthMenu extends GUIComponent<Health>
{
    Text t = new Text();
    Font f = new Font();

    /**
     * GUIHealthMenu component
     * @param h The current health of the player
     */
    public GUIHealthMenu(Health h)
    {
        super(h);
        try
        {
            f.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        t = new Text("HI", f, 20);
        t.setPosition(445,260);

    }

    /**
     * Updates to the current health value of the player
     */
    @Override
    public void update()
    {
       t.setString(String.valueOf((int)getT().getStat()));
    }

    /**
     * Draws the GUI Component
     * @param renderTarget
     * @param renderStates
     */
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(t);
    }
}
