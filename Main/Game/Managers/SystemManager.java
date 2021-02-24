package Main.Game.Managers;


import Main.Game.ECS.Components.StatComponents.Level;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Systems.*;

import java.util.ArrayList;

/**
 * SYSTEMMANAGER
 *
 * -Handles all the Systems in the game
 *  - Updating and Generating Events
 * -Handles SystemEvents (TO DO)
 *
 * Whenever a new System is created
 * It must be added to this class to work
 *
 */
public class SystemManager
{
    private static SystemManager managerInstance = new SystemManager();
    private ArrayList<GameSystem> systemList = new ArrayList<>();
    public static SystemManager getSystemManagerInstance()
    {
        return managerInstance;
    }

    /**
     * Add All systems here
     */
    private SystemManager()
    {
        systemList.add(InputGameSystem.getSystemInstance());
        systemList.add(MovementGameSystem.getSystemInstance());
        systemList.add(SpawnerGameSystem.getInstance());
        systemList.add(PhysicsGameSystem.getSystemInstance());
        systemList.add(ArmorGameSystem.getSystemInstance());
        systemList.add(BackpackGameSystem.getSystemInstance());
        systemList.add(CombatGameSystem.getSystemInstance());
        systemList.add(EffectModifierGameSystem.getSystemInstance());
        systemList.add(LifeSpanGameSystem.getSystemInstance());
        systemList.add(LevelGameSystem.getSystemInstance());
        systemList.add(EventClearGameSystem.getSystemInstance());
        systemList.add(AnimationGameSystem.getSystemInstance());
        systemList.add(RendererGameSystem.getSystemInstance());
        systemList.add(LightingGameSystem.getLightingGameSystem());
        systemList.add(ParticleGameSystem.getSystemInstance());
        systemList.add(SoundGameSystem.getSystemInstance());
        systemList.add(BossAttacksGameSystem.getGameSystem());
        systemList.add(CleanUpGameSystem.getGameSystem());

    }

    /**
     * Adds a given GameObject to all the Systems in which it meets the requirements
     * @param g The GameObject to be added to the systems
     */
    public void addGameObjectTOSYSTEMS(GameObject g)
    {
        for (GameSystem s: systemList)
        {
            if (((g.getBitmask() & s.getBitMaskRequirement()) == s.getBitMaskRequirement())&& (s.getBitMaskRequirement() != 0))
            {
                s.addGameObject(g);
            }
        }
    }

    public void addGOtoSYSTEM(GameObject g, GameSystem s)
    {
        if (((g.getBitmask() & s.getBitMaskRequirement()) == s.getBitMaskRequirement())&& (s.getBitMaskRequirement() != 0))
        {
            s.addGameObject(g);
        }

    }
    public void updateSystem(GameSystem s, float dt)
    {
        s.update(dt);
    }
    /**
     * Updates all Systems
     * -- Called every frame
     */
    public void updateSystems(float dt)
    {
        for(GameSystem s: systemList)
        {

            s.update(dt);

        }
        flushSystems();
    }

    /**
     * Empties all the ArrayLists in all the systems ready for next Frames data
     */
    public void flushSystems()
    {
        for(GameSystem s: systemList)
        {
            s.refreshGameObjects();
        }
    }

    public ArrayList<GameSystem> getSystemList()
    {
        return systemList;
    }
}
