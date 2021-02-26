package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import org.jsfml.system.Vector3f;

/**
 * Component: Light
 *  -Defines if the GameObject should emit a light source
 */
public class Light extends Component
{
    public float size;  //Size of the light source as a percentage of the screen (0.5 = 50% of the screen)
    public float intensity; //Strength of the light source
    public Vector3f rgbData; //Color of the light source

    /**
     * Public Constructor to create a Light component
     * @param size Size of the light source as a percentage of the screen (0.5 = 50% of the screen)
     * @param intensity Strength of the light source
     * @param rgbData Color of the light source
     */
    public Light(float size, float intensity, Vector3f rgbData)
    {
        this.size = size;
        this.intensity = intensity;
        this.rgbData = rgbData;
    }

    /**
     * Method to clone the component
     * @return copy of the component
     */
    @Override
    public Component clone() {
        return new Light(size,intensity,rgbData);
    }
}
