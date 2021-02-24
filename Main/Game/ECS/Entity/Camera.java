package Main.Game.ECS.Entity;

import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import java.awt.color.CMMException;

public class Camera
{

    public View camerView = new View();

    private static Vector2f cameraSize = new Vector2f(1000,1000);
    private static Camera cameraInstance = new Camera();

    private Camera()
    {
        camerView.setSize(cameraSize);
    }

    public static Camera cameraInstance() {
        return cameraInstance;
    }




}
