package Main.Game.ECS.Components.SpecialComponents;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Entity.GameObject;

import java.util.ArrayList;

public class ArmorContainer extends Component {

    private GameObject helmet;
    private GameObject chest;
    private GameObject legs;
    private ArrayList<GameObject> gameObjectsINARMORCONTAINER = new ArrayList<>();

    public ArmorContainer()
    {

    }

    public void addGameObject(GameObject g)
    {
        gameObjectsINARMORCONTAINER.add(g);

    }

    public GameObject getHelmet() {
        return helmet;
    }

    public GameObject getChest() {
        return chest;
    }

    public GameObject getLegs() {
        return legs;
    }

    public void setHelmet(GameObject helmet) {
        this.helmet = helmet;
    }

    public void setChest(GameObject chest) {
        this.chest = chest;
    }

    public void setLegs(GameObject legs) {
        this.legs = legs;
    }


    public GameObject[] getArmor()
    {
        return new GameObject[]{helmet,chest,legs};
    }
    @Override
    public Component clone() {
        return new ArmorContainer();
    }
}
