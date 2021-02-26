package Main.Game.ECS.Components.StandardComponents;

import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.Component;

/**
 * Component: TextureComponent
 *  -Defines the texture that the GameObject should have
 *
 *  Note:
 *      The physical JSFML Texture object is NOT stored here because its too heavy
 *      - The Texture Object is stored within TextureMap as a central static location
 */
public class TextureComponent extends Component
{

    public final TextureTypes texturetype;  //Type of Texture
    public Byte tileMapLocation = -1;   // Location within a sprite Sheet
    public Byte layer =1;   //Layer the Texture is in
    public String textureString;    //String of the texture

    //layer 0 is background
    //layer 1 is fixtures
    //layer 2 is projectiles
    //layer 3 is player

    /**
     * Public Constructor to create a TextureComponent Component
     * @param texturetype Type of Texture (Reference TextureType Enum)
     * @param textureID String of the texture (String)
     * @param layer Value of the layer the texture is on (Byte)
     * @param tileMapLocation Location of the texture within a spriteSheet (Byte)
     */
    public TextureComponent(TextureTypes texturetype, String textureID, Byte layer, Byte tileMapLocation)
    {
        this.texturetype = texturetype;
        this.textureString = textureID;
        this.layer = layer;
        this.tileMapLocation = tileMapLocation;
    }


    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new TextureComponent(texturetype,textureString,layer,tileMapLocation);
    }
}
