package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.ECS.Factory.Fonts;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

public class GUIPlayerLevels extends GUIComponent<Level>
{
    Text namePlate;
    public GUIPlayerLevels(Level l)
    {
        super(l);
        namePlate = new Text("HI", Fonts.MAIN.f, 50);
        namePlate.setPosition(150,130);
    }

    @Override
    public void update()
    {
        namePlate.setString("Adventurer, Level: " + (int)getT().getStat());
    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        renderTarget.draw(namePlate);
    }
}
