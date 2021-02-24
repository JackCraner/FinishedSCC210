package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.SpecialComponents.Light;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.Game;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

public class LightingGameSystem extends GameSystem
{
    private static LightingGameSystem lightingGameSystem = new LightingGameSystem();
    public Shader mapShader = new Shader();
    public Shader wallShader = new Shader();
    private Texture t = new Texture();
    public static LightingGameSystem getLightingGameSystem() {
        return lightingGameSystem;
    }

    private LightingGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class, Light.class));
        try
        {
            mapShader.loadFromFile(Paths.get("Assets" + File.separator + "Shaders" +File.separator+"Vert2.vert"), Paths.get("Assets" + File.separator  + "Shaders" + File.separator+ "Frag2.frag"));
            wallShader.loadFromFile(Paths.get("Assets" + File.separator + "Shaders" +File.separator+"Vert2.vert"), Paths.get("Assets" + File.separator  + "Shaders" + File.separator+ "Frag2.frag"));
        }
        catch(Exception E){
            System.out.println(E);
        }

    }

    @Override
    public void update(float dt)
    {

        int maxNumLights = 28;
        mapShader.setParameter("texture", RendererGameSystem.getSystemInstance().screenTexture.getTexture());   //gives main texture to shader
        mapShader.setParameter("resolution", 1000, 1000);
        mapShader.setParameter("ambientData", 0.3f, 0.3f, 1f, 0f);

        wallShader.setParameter("texture", RendererGameSystem.getSystemInstance().backgroundTexture.getTexture());   //gives main texture to shader
        wallShader.setParameter("resolution", 1000, 1000);
        wallShader.setParameter("ambientData", 0.3f, 0.3f, 1f, 0f);

        wallShader.setParameter("lights[0].position",0.5f,0.5f);
        wallShader.setParameter("lights[0].size",0.3f);
        wallShader.setParameter("lights[0].intensity",0.3f);
        wallShader.setParameter("lights[0].rgbData", 2,2,2);
        int counter =0;
        for(GameObject g: getGameObjectList())
        {

            mapShader.setParameter("lights[" + counter + "].position",convertGlobalPositionToScreenPosition(g.getComponent(Position.class).getPosition()));
            mapShader.setParameter("lights[" + counter + "].size",g.getComponent(Light.class).size);
            mapShader.setParameter("lights[" + counter + "].intensity", g.getComponent(Light.class).intensity);
            mapShader.setParameter("lights[" + counter + "].rgbData", g.getComponent(Light.class).rgbData);
            counter ++;
            if (counter > maxNumLights)
            {
                break;
            }
        }
        for (int i = counter; i < maxNumLights; i++)
        {
            mapShader.setParameter("lights[" + counter + "].position", 0,0);
            mapShader.setParameter("lights[" + counter + "].size", 0);
            mapShader.setParameter("lights[" + counter + "].intensity",0);
            mapShader.setParameter("lights[" + counter + "].rgbData", 0,0,0);
        }
    }


    public Vector2f convertGlobalPositionToScreenPosition(Vector2f position)
    {
        float transform = Camera.cameraInstance().camerView.getSize().x/2;
        float extra = 250f;
        Vector2f pos = Game.PLAYER.getComponent(Position.class).getPosition();
        Vector2f topLeft = new Vector2f(pos.x - transform, pos.y - transform);
        Vector2f bottomRight = new Vector2f(pos.x + transform, pos.y + transform);
        float lightPosY = 0 , lightPosX = 0;
        lightPosY = ((bottomRight.y - position.y) / (bottomRight.y- topLeft.y));
        lightPosX = ((topLeft.x - position.x) / (topLeft.x - bottomRight.x));

        return new Vector2f(lightPosX,lightPosY);

    }

}
