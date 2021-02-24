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

public class PhysicsGameSystem extends GameSystem {

    public static TreeMap<CollisionLayer, Integer> collisionLayers = new TreeMap<CollisionLayer, Integer>();
    public static TreeMap<CollisionLayer, ArrayList<Collider>> collidables = new TreeMap<CollisionLayer, ArrayList<Collider>>();

    public PhysicsGameSystem() {
        setBitMaskRequirement(BitMasks.produceBitMask(Position.class, TransformComponent.class, Collider.class));
        // collisionLayers.put(Collider.CollisionLayer.DEFAULT, ColliderBitMasks.getBitMask(CollisionLayer.DEFAULT) );

    }

    private static PhysicsGameSystem systemInstance = new PhysicsGameSystem();

    public static PhysicsGameSystem getSystemInstance() {
        return systemInstance;
    }

    @Override
    public void update(float dt) {


        for (GameObject g : getGameObjectList()) {
            Collider gameObjectCollider = g.getComponent(Collider.class);
            if (g.getSID() instanceof  Map.Block ) {
                continue;
            }
            gameObjectCollider.reduceAvoidTime(dt);
            /*
            if (gameObjectCollider.bitmask == 0) {
                continue;
            }

             */
            for (GameObject h : getGameObjectList()) {
                Collider gameObjectCollider2 = h.getComponent(Collider.class);
                if (g.equals(h)) {
                    continue;
                }

                if ((gameObjectCollider.bitmask & gameObjectCollider2.layer) != 0) { //change private OVER HERE
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
                                float xDiff = (rect1.left + (rect1.width * 0.5f)) - (rect2.left + (rect2.width * 0.5f));
                                float yDiff = (rect1.top + (rect1.height * 0.5f)) - (rect2.top + (rect2.height * 0.5f));
                                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                                    if (xDiff > 0) {
                                        resolve = (rect2.left + rect2.width) - rect1.left;
                                    } else {
                                        resolve = -((rect1.left + rect1.width) - rect2.left);
                                    }
                                    g.getComponent(Position.class).addPosition(new Vector2f(resolve, 0));
                                } else {
                                    if (yDiff > 0) {
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
                            /*
                            TransformComponent t = g.getComponent(TransformComponent.class);
                            Vector2f pos1 = g.getComponent(Position.class).getPosition();
                            FloatRect body = new FloatRect(pos1.x, pos1.y, t.getSize().x, t.getSize().y);
                            TransformComponent t2 = g.getComponent(TransformComponent.class);
                            Vector2f pos2 = h.getComponent(Position.class).getPosition();
                            FloatRect body2 = new FloatRect(pos2.x, pos2.y, t2.getSize().x, t2.getSize().y);
                            FloatRect collision = body.intersection(body2);


                            if (collision != null) {
                               // System.out.println("HELLOO ");
                               // i boddy2
                                    Vector2f delta = Vector2f.sub(getCenter(body), getCenter(body2));
                                    float intersectX = Math.abs(delta.x) - (body.width/2) + (body2.width/2);
                                    float intersectY = Math.abs(delta.y) - (body.height/2) + (body2.height/2);

                                    Vector2f collisionVector;
                                    if (intersectX > intersectY)
                                    {
                                        if (intersectX >0 )
                                        {
                                            collisionVector = new Vector2f(0.5f*intersectX, 0 );
                                        }
                                        else
                                        {
                                            collisionVector = new Vector2f(-0.5f*intersectX, 0 );
                                        }
                                    }
                                    else
                                    {
                                        if (intersectX>0)
                                        {
                                            collisionVector = new Vector2f(0,0.5f*intersectY );
                                        }
                                        else
                                        {
                                            collisionVector = new Vector2f(0,-0.5f*intersectY );
                                        }
                                    }

                                    Vector2f pos = g.getComponent(Position.class).getPosition();
                                    pos = new Vector2f(pos.x + collisionVector.x, pos.y + collisionVector.y);
                                    g.getComponent(Position.class).updatePosition(pos);

                             */



















/*
            if (((g.getBitmask() & s.getBitMaskRequirement()) == s.getBitMaskRequirement())&& (s.getBitMaskRequirement() != 0))

        }


                if (g.getComponent(Collider.class).getClass().get


            /*
            TransformComponent t = g.getComponent(TransformComponent.class);
            Vector2f pos = g.getComponent(Position.class).getPosition();
            Collider col = g.getComponent(Collider.class);
            FloatRect body = new FloatRect(pos.x, pos.y, t.getSize().x, t.getSize().y);
        */
//TEST HITBOX
            /*
            RectangleShape hitbox = new RectangleShape();
            hitbox.setPosition(pos);
            hitbox.setSize(t.getSize());
            hitbox.setOutlineThickness(2);
            hitbox.setOutlineColor(Color.RED);
            hitbox.setFillColor(new Color(255,255,255,0));
            Game.getGame().hitboxs.draw(hitbox);


             */

/*


            rigidBodies.add(body);
            if (col.dynamic == true)
            {
                movingRigidBodies.add(index);
            }

            index ++;

           col.reduceAvoidTime(dt);
        }

        for(Integer i: movingRigidBodies)
        {
            for (int a = 0; a < rigidBodies.size();a++)
            {
                if (!(a == i))
                {
                    FloatRect collision = rigidBodies.get(i).intersection(rigidBodies.get(a));
                    if (collision != null)
                    {

                        Collider mainCollider = getGameObjectList().get(i).getComponent(Collider.class);
                        Collider collidingWith = getGameObjectList().get(a).getComponent(Collider.class);

                        if (!(mainCollider.checkGameObject(getGameObjectList().get(a))||collidingWith.checkGameObject(getGameObjectList().get(i))))
                        {

                            if (mainCollider.events == true && collidingWith.events == true)
                            {
                               if ((getGameObjectList().get(i).getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) == 0)
                               {
                                    getGameObjectList().get(i).addComponent(new CollisionEvent(getGameObjectList().get(a))); //only 1 collisionEvent per frame

                               }
                               if ((getGameObjectList().get(a).getBitmask() & BitMasks.produceBitMask(CollisionEvent.class)) == 0)
                               {
                                   getGameObjectList().get(a).addComponent(new CollisionEvent(getGameObjectList().get(i)));
                               }

                            }
                            if (mainCollider.dieOnPhysics == true)
                            {
                                EntityManager.getEntityManagerInstance().removeGameObject(getGameObjectList().get(i));
                            }
                            if(mainCollider.physics == true && collidingWith.physics == true)
                            {

                                Vector2f delta = Vector2f.sub(getCenter(rigidBodies.get(a)), getCenter(rigidBodies.get(i)));
                                float intersectX = Math.abs(delta.x) - ((rigidBodies.get(a).width/2) + (rigidBodies.get(i).width/2));
                                float intersectY = Math.abs(delta.y) - ((rigidBodies.get(a).height/2) + (rigidBodies.get(i).height/2));

                                Vector2f collisionVector;
                                if (intersectX > intersectY)
                                {
                                    if (delta.x >0 )
                                    {
                                        collisionVector = new Vector2f(0.5f*intersectX, 0 );
                                    }
                                    else
                                    {
                                        collisionVector = new Vector2f(-0.5f*intersectX, 0 );
                                    }
                                }
                                else
                                {
                                    if (delta.y>0)
                                    {
                                        collisionVector = new Vector2f(0,0.5f*intersectY );
                                    }
                                    else
                                    {
                                        collisionVector = new Vector2f(0,-0.5f*intersectY );
                                    }
                                }

                                Vector2f pos = getGameObjectList().get(i).getComponent(Position.class).getPosition();
                                pos = new Vector2f(pos.x + collisionVector.x, pos.y + collisionVector.y);
                                getGameObjectList().get(i).getComponent(Position.class).updatePosition(pos);

                            }
                        }



                    }
                }
            }


        }

    }

 */
