package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

/**
 * Component: ArmorContainer
 *  -Defines that a GameObject can hold and wear armor pieces
 */
public class ArmorContainer extends Component {

    private GameObject helmet;
    private GameObject chest;
    private GameObject legs;
    private ArrayList<GameObject> gameObjectsINARMORCONTAINER = new ArrayList<>();

    /**
     * Constructor for the Armor Container
     */
    public ArmorContainer()
    {

    }

    /**
     * Method to add a piece of armor to the Armor Container
     * @param g The GameObject to add
     */
    public void addGameObject(GameObject g)
    {
        gameObjectsINARMORCONTAINER.add(g);

    }

    /**
     * Method to get the helmet
     * @return GameObject of the helmet
     */
    public GameObject getHelmet() {
        return helmet;
    }

    /**
     * Method to get the Chest Piece
     * @return GameObject of the chest
     */
    public GameObject getChest() {
        return chest;
    }

    /**
     * Method to get the legs armor piece
     * @return GameObject of the legs
     */
    public GameObject getLegs() {
        return legs;
    }

    /**
     * Method to set the helmet
     * @param helmet New helmet to wear
     */
    public void setHelmet(GameObject helmet) {
        this.helmet = helmet;
    }

    /**
     * Method to set the chest
     * @param chest new chest to wear
     */
    public void setChest(GameObject chest) {
        this.chest = chest;
    }

    /**
     * Method to set the legs
     * @param legs new legs to wear
     */
    public void setLegs(GameObject legs) {
        this.legs = legs;
    }

    /**
     * Method to get all the armor pieces as an array of GameObjects
     * @return Array of all Armor pieces currently worn
     */
    public GameObject[] getArmor()
    {
        return new GameObject[]{helmet,chest,legs};
    }

    /**
     * Method to clone the component
     * @return The copy of the component
     */
    @Override
    public Component clone() {
        return new ArmorContainer();
    }
}
