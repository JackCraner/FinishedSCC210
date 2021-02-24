package Main.Game.ECS.Components.StandardComponents;

import Main.DataTypes.Avoids;
import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.ComponentENUMs.CollisionLayer;
import Main.Game.ECS.Factory.ColliderBitMasks;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Entity.GameObject;
import org.jsfml.system.Vector2f;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;



public class Collider extends Component {
    // public Boolean isColliding = false;
    public final Boolean events;
    public final Boolean dynamic;
    public final Boolean physics;
    public ArrayList<Avoids> avoidGameObject = new ArrayList<>();
    public final Boolean dieOnPhysics;
    public int layer;
    public   int bitmask;



    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics, Avoids... a) {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
        this.layer= 0;
        this.bitmask = 0;
        avoidGameObject.addAll(Arrays.asList(a));
    }
    public Collider( int layer,int mask, Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics, Avoids... a) {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
        this.layer= ColliderBitMasks.produceBitMask(layer);
        this.bitmask = ColliderBitMasks.produceBitMask(mask);
        avoidGameObject.addAll(Arrays.asList(a));
    }

    public Collider(  List<CollisionLayer>  layer, List<CollisionLayer> mask, Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics, Avoids... a) {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
        this.layer= ColliderBitMasks.produceBitMask(layer);
        this.bitmask = ColliderBitMasks.produceBitMask(mask);
        avoidGameObject.addAll(Arrays.asList(a));
    }
    public Collider( CollisionLayer  layer, List<CollisionLayer> mask, Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics, Avoids... a) {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
        this.layer= ColliderBitMasks.produceBitMask(layer);
        this.bitmask = ColliderBitMasks.produceBitMask(mask);
        avoidGameObject.addAll(Arrays.asList(a));
    }
    public Collider(  CollisionLayer  layer, CollisionLayer mask, Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics, Avoids... a) {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
        this.layer= ColliderBitMasks.produceBitMask(layer);
        this.bitmask = ColliderBitMasks.produceBitMask(mask);
        avoidGameObject.addAll(Arrays.asList(a));
    }

    public void setAvoidTime(GameObject g, float x) {
        avoidGameObject.add(new Avoids(g.getUID(), x));
    }

    public void setAvoidTime(GameObject g) {
        avoidGameObject.add(new Avoids(g.getUID()));
    }

    public void reduceAvoidTime(float dt) {
        Iterator<Avoids> itr = avoidGameObject.iterator();
        while (itr.hasNext()) {
            Avoids a = itr.next();
            a.avoidTimeClock(dt);
            if (a.avoidFinished()) {
                itr.remove();
            }
        }

    }

    public boolean checkGameObject(GameObject g) {
        for (Avoids a : avoidGameObject) {
            if (a.getGameObjectUIDTOAVOID() == g.getUID()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Component clone() {
        return new Main.Game.ECS.Components.StandardComponents.Collider(layer, bitmask, events, dynamic, physics, dieOnPhysics, ((ArrayList<Avoids>) (avoidGameObject.clone())).toArray(new Avoids[avoidGameObject.size()]));
    }
}

    /*

    public enum CollisionLayer {
        DEFAULT(1), PLAYER(2), BLOCK(3), ENEMY(4);
        private final int value;
        CollisionLayer(final int val) {
            value = val;
        }

        public int getValue() { return value; }
    }

     */






        /*
    public Manifold Intersects(Collider other){
        Manifold m = new Manifold();
        m.isColliding = false;

        Collider boxCollider =  other;
        FloatRect rect1 = GetCollidable();
        FloatRect rect2 = boxCollider.GetCollidable();
        FloatRect collision = rect1.intersection(rect2);
        if (collision != null)
        {
            m.isColliding = true;
            m.other = rect2;
        }
        return  m;
    }









    /*
   // public Boolean isColliding = false;
    public final Boolean events;
    public final Boolean dynamic;
    public final Boolean physics;
    public ArrayList<Avoids> avoidGameObject = new ArrayList<>();
    public float avoidTimer = 0;
    public final Boolean dieOnPhysics;

    public Collider()
    {
      this.events = false;
      this.dynamic = false;
      this.physics = true;
      this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = false;
    }
    public Collider(Boolean events, Boolean dynamic, Boolean phyiscs, Boolean dieOnPhysics)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.dieOnPhysics = dieOnPhysics;
    }
    private Collider(Boolean events, Boolean dynamic, Boolean phyiscs, ArrayList<Avoids> a, Boolean dieOnPhysics)
    {
        this.events = events;
        this.dynamic = dynamic;
        this.physics = phyiscs;
        this.avoidGameObject = a;
        this.dieOnPhysics = dieOnPhysics;
    }
    public void setAvoidTime(GameObject g, float x)
    {
        avoidGameObject.add(new Avoids(g.getUID(),x));
    }
    public void setAvoidTime(GameObject g)
    {
        avoidGameObject.add(new Avoids(g.getUID()));
    }
    public void reduceAvoidTime(float dt)
    {
        Iterator<Avoids> itr = avoidGameObject.iterator();
        while(itr.hasNext())
        {
            Avoids a = itr.next();
            a.avoidTimeClock(dt);
            if(a.avoidFinished())
            {
                itr.remove();
            }
        }

    }



    @Override
    public Component clone() {
        return new Collider(events,dynamic,physics,(ArrayList<Avoids>)avoidGameObject.clone(),dieOnPhysics);
    }

     */
