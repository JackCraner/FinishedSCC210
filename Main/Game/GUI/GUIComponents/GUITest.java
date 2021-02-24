package Main.Game.GUI.GUIComponents;

import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.GUI.GUIComponent;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;


public class GUITest extends GUIComponent<Backpack>
{
    public GUITest(Backpack b)
    {
        super(b);
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates) {

    }
}
