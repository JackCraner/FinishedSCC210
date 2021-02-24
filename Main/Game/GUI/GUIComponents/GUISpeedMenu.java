package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

import java.io.File;
import java.nio.file.Paths;

/**
 * The speed value in the pause Menu
 */
public class GUISpeedMenu extends GUIComponent<Speed>
{
    Text t = new Text();
    Font f = new Font();

    /**
     * GUISpeedMenu component
     * @param s The current speed of the player
     */
    public GUISpeedMenu(Speed s)
    {
        super(s);
        try
        {
            f.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "LEMONMILK-Regular.otf"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        t = new Text("HI", f, 20);
        t.setPosition(443,427);

    }

    /**
     * Updates to the current speed value of the player
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