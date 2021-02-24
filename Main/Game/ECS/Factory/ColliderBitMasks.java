package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.ComponentENUMs.CollisionLayer;
import java.util.List;
import Main.Game.ECS.Components.ItemComponents.Damage;
import Main.Game.ECS.Components.ItemComponents.GivenEffect;
import Main.Game.ECS.Components.ItemComponents.LifeSpan;
import Main.Game.ECS.Components.ItemComponents.Pickup;
import Main.Game.ECS.Components.SpecialComponents.*;
import Main.Game.ECS.Components.StandardComponents.*;
import Main.Game.ECS.Components.StatComponents.*;
import Main.Game.ECS.Components.Component;

import java.util.HashMap;

public class ColliderBitMasks {
    private static ColliderBitMasks bitMasksInstance = new ColliderBitMasks();

    public static ColliderBitMasks getBitMasksInstance() {
        return bitMasksInstance;
    }

    private static HashMap<CollisionLayer, Integer> COLLISIONBITLAYERS = new HashMap<>();

    private static HashMap<CollisionLayer, Integer> COLLISIONBITMASKS = new HashMap<>();

    static {
        //max 31 components
        COLLISIONBITLAYERS.put(CollisionLayer.DEFAULT, Integer.parseInt("0", 2));     //1
        COLLISIONBITLAYERS.put(CollisionLayer.PLAYER, Integer.parseInt("10", 2)); //2
        COLLISIONBITLAYERS.put(CollisionLayer.BLOCK, Integer.parseInt("100", 2));  //3
        COLLISIONBITLAYERS.put(CollisionLayer.ENEMY, Integer.parseInt("1000", 2));  //3
        COLLISIONBITLAYERS.put(CollisionLayer.ITEM, Integer.parseInt("10000", 2));  //3
        COLLISIONBITLAYERS.put(CollisionLayer.ENEMYPROJECTILE, Integer.parseInt("100000", 2));  //3
        COLLISIONBITLAYERS.put(CollisionLayer.PLAYERPROJECTILE, Integer.parseInt("1000000", 2));  //3
        // COLLISIONBITMASKS.put(Collider.class, Integer.parseInt("1000", 2));  //4


    }

    /**
     * Creates a BitMask for a GameObject or GameSystem given one or many Ints
     * The BitMask works by using BitWise OR on all the numbers
     *
     * @param bitMasks The One or Many Ints to combine into a bitmask
     * @return the combined bitmask
     */
    public static int produceBitMask(int... bitMasks) {
        int newMask = 0;
        for (int i : bitMasks) {
            newMask |= i;
        }
        return newMask;
    }

    /**
     * Also Creates a BitMask for a GameObject or GameSystem but given one or many Components
     * Uses the unique integer values found in the HashMap for each given component
     *
     * @param bitMasks The Components used to make the BitMask
     * @return The BitMask
     */
    public static int produceBitMask(List<CollisionLayer> bitMasks) {
        int newMask = 0;
        for (CollisionLayer c : bitMasks) {
            newMask |= COLLISIONBITLAYERS.get(c);
        }
        return newMask;
    }
    public static int produceBitMask(CollisionLayer bitMask) {
        int newMask = 0;
        newMask |= COLLISIONBITLAYERS.get(bitMask);
        return newMask;
    }



    public static int getBitMask(CollisionLayer c) {
        return COLLISIONBITLAYERS.get(c);
    }




    public static boolean checkIfContains(int bitMask, CollisionLayer c) {
        return ((bitMask & COLLISIONBITMASKS.get(c)) != 0);
    }



}