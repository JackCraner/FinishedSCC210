package Main.Game.ECS.Systems;


import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.ComponentENUMs.CollisionLayer;
import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
//import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.SpecialComponents.CollisionEvent;
import Main.Game.ECS.Components.StandardComponents.Collider;
import Main.Game.ECS.Factory.ColliderBitMasks;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Factory.ColliderBitMasks;
import Main.Game.ECS.Factory.Entity;
import Main.Game.Managers.EntityManager;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import Main.Game.MapGeneration.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
/**
 * PHYSICSGAMESYSTEM
 *
 * - handles movement of GameObjects
 *
 */
public class PhysicsGameSystem extends GameSystem {

    public static TreeMap<CollisionLayer, Integer> collisionLayers = new TreeMap<CollisionLayer, Integer>();
    public static TreeMap<CollisionLayer, ArrayList<Collider>> collidables = new TreeMap<CollisionLayer, ArrayList<Collider>>();

    /**
     * Constructor
     */
    public PhysicsGameSystem() {
        /**
        Sets bit mask requirement
         */
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class, TransformComponent.class, Collider.class));

        // collisionLayers.put(Collider.CollisionLayer.DEFAULT, ColliderBitMasks.getBitMask(CollisionLayer.DEFAULT) );

    }

    private static PhysicsGameSystem systemInstance = new PhysicsGameSystem();

    public static PhysicsGameSystem getSystemInstance() {
        return systemInstance;
    }


    /**
     *  Loop through the collidable entities, Check whether collidable entities collide,Resolve collision
     * @param dt Change in time since last frame
     */
    @Override
    public void update(float dt) {


        for (GameObject g : getGameObjectList()) {
            Collider gameObjectCollider = g.getComponent(Collider.class);
            if (g.getSID() instanceof  Map.Block ) {
                continue;
            }
            gameObjectCollider.reduceAvoidTime(dt);

            for (GameObject h : getGameObjectList()) {
                Collider gameObjectCollider2 = h.getComponent(Collider.class);
                if (g.equals(h)) {
                    continue;
                }

                if ((gameObjectCollider.bitmask & gameObjectCollider2.layer) != 0) {
                    if (gameObjectCollider2.layer == gameObjectCollider.layer){
                        continue;
                    }

                    TransformComponent t = g.getComponent(TransformComponent.class);
                    Vector2f pos1 = g.getComponent(Position.class).getPosition();
                    FloatRect rect1 = new FloatRect(pos1.x, pos1.y, t.getSize().x, t.getSize().y);
                    TransformComponent t2 = g.getComponent(TransformComponent.class);
                    Vector2f pos2 = h.getComponent(Position.class).getPosition();
                    FloatRect rect2 = new FloatRect(pos2.x, pos2.y, t2.getSize().x, t2.getSize().y);
                    FloatRect collision = rect2.intersection(rect1);
                    if (collision != null) {
                        Collider mainCollider = g.getComponent(Collider.class);
                        Collider collidingWith = h.getComponent(Collider.class);


                        if (!(mainCollider.checkGameObject(h)||collidingWith.checkGameObject(g)))
                        {
                            if (mainCollider.events == true && collidingWith.events == true)
                            {


                                if ((g.getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) == 0)
                                {
                                    g.addComponent(new CollisionEvent());


                                }
                                g.getComponent(CollisionEvent.class).getG().add(h);
                                if ((h.getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) == 0) {
                                    h.addComponent(new CollisionEvent());

                                }
                                h.getComponent(CollisionEvent.class).getG().add(g);

                            }


                            if (mainCollider.dieOnPhysics == true) {
                                EntityManager.getEntityManagerInstance().removeGameObject(g);
                            }
                            if (mainCollider.physics == true && collidingWith.physics == true) {
                                float resolve = 0;
                                float xIntersect = (rect1.left + (rect1.width * 0.5f)) - (rect2.left + (rect2.width * 0.5f));
                                float yIntersect = (rect1.top + (rect1.height * 0.5f)) - (rect2.top + (rect2.height * 0.5f));
                                if (Math.abs(xIntersect) > Math.abs(yIntersect)) {
                                    if (xIntersect > 0) {
                                        resolve = (rect2.left + rect2.width) - rect1.left;
                                    } else {
                                        resolve = -((rect1.left + rect1.width) - rect2.left);
                                    }
                                    g.getComponent(Position.class).addPosition(new Vector2f(resolve, 0));
                                } else {
                                    if (yIntersect > 0) {
                                        resolve = (rect2.top + rect2.height) - rect1.top;

                                    } else
                                    {
                                        resolve = -((rect1.top + rect1.height) - rect2.top);
                                    }
                                    g.getComponent(Position.class).addPosition(new Vector2f(0, resolve));
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}





















