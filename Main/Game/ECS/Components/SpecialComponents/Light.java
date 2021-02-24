package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.system.Vector3f;

public class Light extends Component
{
    public float size;
    public float intensity;
    public Vector3f rgbData;
    //public Vector2f position;    Later on we give the Light its on Position within the Sprite for more accurate light placement on objects
    public Light(float size, float intensity, Vector3f rgbData)
    {
        this.size = size;
        this.intensity = intensity;
        this.rgbData = rgbData;
    }


    @Override
    public Component clone() {
        return new Light(size,intensity,rgbData);
    }
}
