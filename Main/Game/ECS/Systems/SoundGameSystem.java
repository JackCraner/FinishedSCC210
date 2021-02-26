package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.SpecialComponents.SoundEffect;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Speed;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.Game;
import org.jsfml.audio.Listener;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;

public class SoundGameSystem extends GameSystem
{

    private static SoundGameSystem systemInstance = new SoundGameSystem();
    private SoundBuffer sb = new SoundBuffer();
    public static SoundGameSystem getSystemInstance() {
        return systemInstance;
    }

    private SoundGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(SoundEffect.class));

        try{
            sb.loadFromFile(Paths.get("Assets" + File.separator + "Sound" + File.separator+ "Fireball.wav"));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void update(float dt)
    {

        Vector2f pos = Game.PLAYER.getComponent(Position.class).getPosition();
        Listener.setPosition(pos.x, 0, pos.y);

        /*
        if (Game.PLAYER.getComponent(TransformComponent.class).isFacingRight())
        {
            Listener.setDirection(1,0,0);
        }
        else
        {
            Listener.setDirection(-1,0,0);
        }

        for (GameObject g: getGameObjectList())
        {

            Sound s = new Sound(sb);
            s.setVolume(99f);
            s.setPitch(1.5f);
            if(BitMasks.checkIfContains(g.getBitmask(),Position.class))
            {
                Vector2f gPos = g.getComponent(Position.class).getPosition();
               // s.setPosition(gPos.x+200,0,gPos.y);
                //s.setMinDistance(70f);
                //s.setAttenuation(100f);
                //s.setRelativeToListener(true);
            }
            s.play();
            g.removeComponent(SoundEffect.class);
        }

         */
    }
}
