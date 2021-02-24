package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Strength;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

import java.io.File;
import java.nio.file.Paths;

/**
 * The strength value in the pause Menu
 */
public class GUIStrengthMenu extends GUIComponent<Strength>
{
    Text t = new Text();
    Font f = new Font();

    /**
     * GUIStrengthMenu component
     * @param s The current strength of the player
     */
    public GUIStrengthMenu(Strength s)
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
        t.setPosition(445,510);

    }

    /**
     * Updates to the current strength value of the player
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