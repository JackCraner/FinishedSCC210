package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.Component;

public class TextureComponent extends Component
{

    public final TextureTypes texturetype;
    public Byte tileMapLocation = -1;
    public Byte layer =1;
    public String textureString;

    //layer 0 is background
    //layer 1 is fixtures
    //layer 2 is projectiles
    //layer 3 is player

    public TextureComponent(TextureTypes texturetype, String textureID, Byte layer, Byte tileMapLocation)
    {
        this.texturetype = texturetype;
        this.textureString = textureID;
        this.layer = layer;
        this.tileMapLocation = tileMapLocation;
    }



    @Override
    public Component clone() {
        return new TextureComponent(texturetype,textureString,layer,tileMapLocation);
    }
}
