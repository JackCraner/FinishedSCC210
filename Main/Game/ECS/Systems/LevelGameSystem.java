package Main.Game.ECS.Systems;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.SpecialComponents.Particles;
import Main.Game.ECS.Components.StatComponents.*;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIHealthBar;
import Main.Game.GUI.GUIComponents.GUIXPBar;
import Main.Game.Managers.GUIManager;
import org.jsfml.graphics.Color;

public class LevelGameSystem extends GameSystem
{

    private static LevelGameSystem systemInstance = new LevelGameSystem();
    public static LevelGameSystem getSystemInstance()
    {
        return systemInstance;
    }
    private LevelGameSystem()
    {
        setBitMaskRequirement(BitMasks.getBitMask(Level.class));
    }

    @Override
    public void update(float dt)
    {
        for (GameObject g: getGameObjectList())
        {
            Level objectLevel = g.getComponent(Level.class);
            float xpNeededToLevel = objectLevel.getCurrentLevel() * 5;
            if (objectLevel.getAddingXP()>0)
            {
                objectLevel.setCurrentXP(objectLevel.getCurrentXP() + objectLevel.getAddingXP());
                if (objectLevel.getCurrentXP() > xpNeededToLevel)
                {
                    objectLevel.levelUP();

                    levelUpCharacter(g);

                    g.addComponent(new Particles(0.005f,1f,0.1f, Color.YELLOW));



                }

                objectLevel.clearAddingXP();
                if(g.getName() == Entity.PLAYER.name)
                {
                    System.out.println(objectLevel.getCurrentLevel() + "  " + objectLevel.getCurrentXP());
                    GUIManager.getGUIinstance().GUIUpdate(GUIXPBar.class);
                }
            }

        }

    }


    public void levelUpCharacter(GameObject g)
    {
        if(BitMasks.checkIfContains(g.getBitmask(), Health.class))
        {
            Health h = g.getComponent(Health.class);
            h.setMaxHealth(h.getMaxHealth() + 50);
            h.setStat(h.getMaxHealth());
            GUIManager.getGUIinstance().GUIUpdate(GUIHealthBar.class);
        }
        if(BitMasks.checkIfContains(g.getBitmask(), Strength.class))
        {
            Strength s = g.getComponent(Strength.class);
            s.setStat(s.getStat() + 40);
        }
        if(BitMasks.checkIfContains(g.getBitmask(), Wisdom.class))
        {
            Wisdom s = g.getComponent(Wisdom.class);
            s.setStat(s.getStat() + 40);
        }

    }
}
