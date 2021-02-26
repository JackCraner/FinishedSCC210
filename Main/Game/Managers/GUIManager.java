package Main.Game.Managers;

import Main.Game.ECS.Components.SpecialComponents.ArmorContainer;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StatComponents.*;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.GUI.GUIComponent;
import Main.Game.GUI.GUIComponents.*;
import Main.Game.Game;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

import java.util.HashMap;

/**
 * Controls all the GUI (At the moment, that being the onscreen bars)
 * Acts like all others managers, controls an ArrayList of components defined for the manager
 * -- IS a singleton
 */
public class GUIManager implements Drawable
{

    public static GameObject GUITarget = Game.PLAYER;       //The GameObject the GUI is locked on, so will display information about this GAMEOBJECT;


    private HashMap<Class<? extends GUIComponent>,GUIComponent> guiACTIVEComponentList = new HashMap<>();



    boolean inBossRoom = false;
    Health bosshp;

    private static HashMap<Class<? extends GUIComponent>,GUIComponent> guiGAMEComponentList = new HashMap<>();       //Hashmap (Speedy List) of all the GUI Components
    static{
        //If you want to add a new GUI Component, first add it to the ENUM, then make a new class for the new element which extends the abstract class
        //finally add a new instance of the new class with its paired enum to the hashmap
        guiGAMEComponentList.put(GUIInventory.class, new GUIInventory(GUITarget.getComponent(Backpack.class)));
        guiGAMEComponentList.put(GUIHealthBar.class,new GUIHealthBar(GUITarget.getComponent(Health.class)));
        guiGAMEComponentList.put(GUIArmorBar.class,new GUIArmorBar(GUITarget.getComponent(Armor.class)));
        guiGAMEComponentList.put(GUIXPBar.class,new GUIXPBar(GUITarget.getComponent(Level.class)));
        guiGAMEComponentList.put(GUIItemHover.class, new GUIItemHover(GUITarget.getComponent(Backpack.class)));
    }

    private static HashMap<Class<? extends GUIComponent>, GUIComponent> guiMENUComponentList = new HashMap<>();
    static{
        guiMENUComponentList.put(GUIHealthMenu.class,new GUIHealthMenu(GUITarget.getComponent(Health.class)));
        guiMENUComponentList.put(GUIPauseMenu.class,new GUIPauseMenu(GUITarget.getComponent(Backpack.class)));
        guiMENUComponentList.put(GUIManaMenu.class,new GUIManaMenu(GUITarget.getComponent(Mana.class)));
        guiMENUComponentList.put(GUISpeedMenu.class,new GUISpeedMenu(GUITarget.getComponent(Speed.class)));
        guiMENUComponentList.put(GUIStrengthMenu.class,new GUIStrengthMenu(GUITarget.getComponent(Strength.class)));
        guiMENUComponentList.put(GUIWisdomMenu.class,new GUIWisdomMenu(GUITarget.getComponent(Wisdom.class)));
        guiMENUComponentList.put(GUIArmorMenu.class,new GUIArmorMenu(GUITarget.getComponent(ArmorContainer.class)));
        guiMENUComponentList.put(GUIPlayerLevels.class, new GUIPlayerLevels(GUITarget.getComponent(Level.class)));
    }
    
    private static HashMap<Class<? extends GUIComponent>, GUIComponent> guiDSCREENComponentList = new HashMap<>();
    static{
        guiDSCREENComponentList.put(GUIDeadScreen.class,new GUIDeadScreen(GUITarget.getComponent(Health.class)));
    }

    private static HashMap<Class<? extends GUIComponent>, GUIComponent> guiFSCREENComponentList = new HashMap<>();
    static{
        guiFSCREENComponentList.put(GUIFinScreen.class,new GUIFinScreen(GUITarget.getComponent(Level.class)));
    }

    private static HashMap<GUIModeEnum,HashMap<Class<? extends GUIComponent>,GUIComponent>> guiModesHashMapHashMap = new HashMap<>();
    static{
        guiModesHashMapHashMap.put(GUIModeEnum.GAME,guiGAMEComponentList);
        guiModesHashMapHashMap.put(GUIModeEnum.MENU,guiMENUComponentList);
        guiModesHashMapHashMap.put(GUIModeEnum.DSCREEN,guiDSCREENComponentList);
        guiModesHashMapHashMap.put(GUIModeEnum.FSCREEN,guiFSCREENComponentList);
    }


    private static GUIManager GUIinstance = new GUIManager();       //Singleton pattern (use google for better understanding)
    public static GUIManager getGUIinstance() {
        return GUIinstance;
    }

    public void setInBossRoom(boolean inBossRoom) {
        this.inBossRoom = inBossRoom;
    }

    public void leaveBossRoom()
    {
        this.inBossRoom = false;
        bosshp = null;
    }

    public void enterBossRoom(GameObject boss)
    {
        this.inBossRoom = true;
        bosshp = boss.getComponent(Health.class);
    }

    private GUIManager()
    {

        guiACTIVEComponentList = guiGAMEComponentList;
        //swapModes(GUIModes.GAME);
        //any code that needs to be run at the start is put here
        //this should stay empty unless absolutely necessary, Message me
    }
    public void swapModes(GUIModeEnum mode)
    {
        guiACTIVEComponentList = guiModesHashMapHashMap.get(mode);
    }

    /**
     * Updates the Single GUI Component given by e
     * @param e the GUI Enum of the component we want to update
     */
    public void GUIUpdate(Class<? extends GUIComponent> e)
    {
        //This should be called in GameSystems if a GUI Component needs to be updated
        //for example in BackPackGameSystem, when the player picks up or drops an item, we call GUIUpdate(GUIComponentENUM.Inventory)
        //to update the inventory
        if (guiACTIVEComponentList.get(e) != null)
        {
            guiACTIVEComponentList.get(e).update();
        }

    }
    public void GUIUpdateALL()
    {
        for (GUIComponent g: guiACTIVEComponentList.values())
        {
            g.update();
        }
    }



    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        //draws all the GUI elements

        for (GUIComponent g: guiACTIVEComponentList.values())
        {
            renderTarget.draw(g,renderStates);
        }

        if (inBossRoom)
        {
            renderTarget.draw(new GUIBossHealthBar(bosshp));
        }
    }
}

/**
 * Future Plans
 * --- We will eventually need menus
 * My Thinking is that menus will still be an ArrayList of GUI Components same as the "In Game GUI"
 * But we will give the GUIManager different modes and multiple ArrayLists
 *      Mode: In-Game
 *      ArrayList: guiComponentList
 *
 *      Mode: Settings Menu
 *      ArrayList: guiComponentListSettings
 *
 *      Mode: Talent Menu
 *      ArrayList: guiComponentListTalents
 *
 * Make an ENUM for the different Modes and a Hashmap to attach the mode to the arrayList
 * Then a function which can swap modes
 *
 * Example
 * so a input game system can record the "ESC" button being pressed, call the GUIManager.switchModesTo(GUIModes.SettingsMenu)
 * Now the GUIManager knows what mode we are on and what to display
 * Now the GUIManager should only draw and update active components (components on the arrayList on the current Mode)
 */
