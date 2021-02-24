package Main.Game.ECS.Factory;

import Main.DataTypes.Effects;
import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.ComponentENUMs.MovementTypes;
import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.ItemComponents.*;
import Main.Game.ECS.Components.SpecialComponents.*;
import Main.Game.ECS.Components.StandardComponents.*;
import Main.Game.ECS.Components.StatComponents.*;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.Game;
import Main.Game.MapGeneration.Map;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.system.Vector3f;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import Main.Game.ECS.Components.ComponentENUMs.CollisionLayer;
import static java.util.Arrays.asList;


public class Blueprint
{
    public static Vector2f BLOCKSIZE = new Vector2f(32,32);
    public static Vector2f TEXTURESIZE = new Vector2f(32,32);
    public static Vector2f OBJECTSIZE = new Vector2f(32,32);
    public static Vector2f ITEMSIZE = new Vector2f(16,16);

    public static void addGameObjectToSystems()
    {

    }

    public static Vector2f convertToGlobal(Vector2i binary)
    {
        return new Vector2f(binary.x * BLOCKSIZE.x, binary.y * BLOCKSIZE.y);
    }
    public static Vector2i convertToLocal(Vector2f global)
    {
        return new Vector2i((int)(global.x / BLOCKSIZE.x), (int)(global.y /BLOCKSIZE.y));
    }



    public static GameObject genRandomItem(GameObject me)
    {
        Random r = new Random();
        int rValue = r.nextInt(3);
        if (rValue == 0)
        {
            PotionEnum potionArray[] = Arrays.stream(PotionEnum.values()).toArray(PotionEnum[]::new);

            return potion(potionArray[r.nextInt(potionArray.length )]);
        }
        if (rValue ==2)
        {
            ArmorEnum.ArmorPiece pieceArray[] =  Arrays.stream(ArmorEnum.ArmorPiece.values()).toArray(ArmorEnum.ArmorPiece[]::new);
            ArmorEnum.ArmorType typeArray[] = Arrays.stream(ArmorEnum.ArmorType.values()).toArray(ArmorEnum.ArmorType[]::new);
            return armor(new ArmorEnum(typeArray[r.nextInt(typeArray.length)],pieceArray[r.nextInt(pieceArray.length)]));
        }
        else
        {
            WeaponEnum weaponArray[] = Arrays.stream(WeaponEnum.values()).toArray(WeaponEnum[]::new);
            return weapon(weaponArray[r.nextInt(weaponArray.length)],me);
        }


    }

    public static GivenEffect generateEffect()
    {
        int randTemp = new Random().nextInt(3);
        Class<? extends Component> modType;
        if (randTemp == 1)
        {
            modType = Speed.class;
        }
        else if(randTemp == 2)
        {
            modType = Strength.class;
        }
        else
        {
            modType = Wisdom.class;
        }

        float modAmount = ((float)Math.round(new Random().nextFloat() * 100))  /200;

        return new GivenEffect(new Effects(modType,modAmount));
    }
    public static GameObject armor(ArmorEnum armorType)
    {
        GameObject g = new GameObject(armorType);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,armorType.getTexture(), (byte)3,(byte)1));

        g.addComponent(new Collider(CollisionLayer.ITEM,CollisionLayer.PLAYER,true,false,false,false));
        ItemRarity rarity = ItemRarity.generateRarity();
        g.addComponent(new Armor(10 * rarity.multi));
        g.addComponent(new Pickup(rarity,armorType.getArmorPiece()));
        g.addComponent(new Animation(0.2f));
        g.addComponent(generateEffect());
        g.addComponent(new Particles(0.03f,0.2f,-1f,rarity.rarityColor));
        return g;
    }

    public static GameObject potion(PotionEnum potionType)
    {
        GameObject g = new GameObject(potionType);
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,potionType.getTexture(), (byte)3,(byte)0));
        g.addComponent(new Collider(CollisionLayer.ITEM,CollisionLayer.PLAYER,true,false,false,false));
        g.addComponent(new Pickup(ItemRarity.generateRarity(),potionType));
        g.addComponent(new Particles(0.03f,0.2f,-1f,g.getComponent(Pickup.class).getRarity().rarityColor));
        g.addComponent(new GivenEffect(new Effects(potionType.getStatType(),0.5f,10)));
        return g;

    }


    public static GameObject weapon(WeaponEnum weaponType, GameObject holder)  //OVER HERE
    {
        GameObject g = new GameObject(weaponType);
        g.addComponent(new Link(holder));
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,weaponType.body.texture, (byte)2,(byte)0));
        ItemRarity rarity = ItemRarity.generateRarity();
        g.addComponent(new Pickup(rarity,weaponType.body));
        g.addComponent(new Damage(weaponType.projectile.getBaseDamage() * rarity.multi));

        /*
        if (new Random().nextInt(3) == 1);
        {
            g.addComponent(generateEffect());
        }

         */

        g.addComponent(new Collider(CollisionLayer.ITEM,CollisionLayer.DEFAULT,true,false,false,false));
        if (holder.getName() == "Enemy"){ g.addComponent(new Projectile(enemyprojectile(weaponType.projectile,g),weaponType.projectile.getCooldown()));
        } else { g.addComponent(new Projectile(playerprojectile(weaponType.projectile,g),weaponType.projectile.getCooldown()));}
        g.addComponent(new Particles(0.05f,0.2f,-1f,rarity.rarityColor));
        return g;
    }

    public static GameObject playerprojectile(WeaponEnum.Projectile projectileType, GameObject shooter)  //OVER HERE
    {
        GameObject g = new GameObject(projectileType);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.PROJECTILE,projectileType.getTexture(), (byte)2,(byte)0));
        g.addComponent(new LifeSpan(projectileType.lifeSpan));
        g.addComponent(new Speed(MovementTypes.LINEAR, projectileType.speed));
        g.addComponent(new Collider(CollisionLayer.PLAYERPROJECTILE,  CollisionLayer.DEFAULT,true,true,false,false));
        g.addComponent(new Link(shooter));
        return g;
    }

    public static GameObject enemyprojectile(WeaponEnum.Projectile projectileType, GameObject shooter)  //OVER HERE
    {
        GameObject g = new GameObject(projectileType);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.PROJECTILE,projectileType.getTexture(), (byte)2,(byte)0));
        g.addComponent(new LifeSpan(projectileType.lifeSpan));
        g.addComponent(new Speed(MovementTypes.LINEAR, projectileType.speed));
        g.addComponent(new Collider(CollisionLayer.ENEMYPROJECTILE,  CollisionLayer.DEFAULT,true,true,false,false));
        g.addComponent(new Link(shooter));
        return g;
    }

    public static GameObject player(Vector2f position)
    {

        GameObject g = new GameObject(Entity.PLAYER);
        g.addComponent(new Position(position,g));
        g.addComponent(new Inputs(InputTypes.HUMAN));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.PLAYER.textureString,(byte)3, (byte)1));
        g.addComponent(new Speed(MovementTypes.CONTROLLED, 300));
        g.addComponent(new Light(0.5f,10f,new Vector3f(0.6f,0.6f,0.8f)));
        g.addComponent(new Health(100));
        g.addComponent(new Mana(100));
        g.addComponent(new Armor(0));
        g.addComponent(new Collider(CollisionLayer.PLAYER,asList(CollisionLayer.BLOCK,CollisionLayer.ENEMYPROJECTILE,CollisionLayer.ENEMY,CollisionLayer.ITEM),true,true,true,false));
        g.addComponent(new Backpack(6,true));
        g.addComponent(new Level(1));
        g.addComponent(new Animation(0.2f));
        g.addComponent(new EffectComponent());
        g.addComponent(new Spawner(500,0.0007f));
        g.addComponent(new ArmorContainer());
        g.addComponent(new Strength(50));
        g.addComponent(new Wisdom(50));
        g.addComponent(new CollisionEvent());

        //g.getComponent(EffectComponent.class).addEffect(new Effects(Speed.class,2f,5));
        return g;


    }
    public static GameObject test(Vector2f position)
    {
        GameObject g = new GameObject(Entity.CHEST);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        return g;
    }
    public static GameObject chest(Vector2f position) //OVER HERE
    {
        GameObject g = new GameObject(Entity.CHEST);
        g.addComponent(new Backpack(1, false));
       // g.getComponent(Backpack.class).addGameObject(armor(new ArmorEnum(ArmorEnum.ArmorType.LEATHER, ArmorEnum.ArmorPiece.HELMET)));
        g.getComponent(Backpack.class).addGameObject(genRandomItem(g));
        g.addComponent(new Health(20));
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.CHEST.textureString,(byte)2, (byte)0));
        g.getComponent(TextureComponent.class).tileMapLocation =0;
        //g.getComponent(TransformComponent.class).setRotation(95);
        g.addComponent(new Collider(CollisionLayer.DEFAULT, CollisionLayer.PLAYERPROJECTILE,true,true,false,false));

        return g;
    }

    public static GameObject block(Vector2f position, byte blockID, float rotation, Map m)
    {
        GameObject g = new GameObject(m.block);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(BLOCKSIZE));
        g.getComponent(TransformComponent.class).setDirection(rotation);
        g.addComponent(new TextureComponent(TextureTypes.BLOCK,m.block.getTexture(), (byte)0,blockID));
        //g.addComponent(new Health(5));
        g.addComponent(new Collider(CollisionLayer.BLOCK,CollisionLayer.DEFAULT,false,false,true,false));
        return g;
    }
    public static GameObject torch(Vector2f position)
    {
        GameObject g = new GameObject(Entity.TORCH);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(16,16)));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.TORCH.textureString,(byte)2,(byte)0));
        g.getComponent(TextureComponent.class).tileMapLocation =0;
        g.addComponent(new Light(0.4f,5f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Collider(CollisionLayer.ITEM,CollisionLayer.DEFAULT,true,false,false,false));
        g.addComponent(new Pickup(ItemRarity.COMMON,WeaponEnum.WeaponPiece.WAND));
        return g;

    }
    /*
    public static GameObject swordSwoosh()
    {
        GameObject g = new GameObject(Entity.SWOOSH);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.PROJECTILE, Entity.SWOOSH.getTexture(), (byte)2,(byte)0));
        g.addComponent(new LifeSpan(0.25f));
        g.addComponent(new Speed(MovementTypes.LINEAR, 100));
        g.addComponent(new Collider(true,true,false,false));
        return g;
    }
    public static GameObject fireBall()
    {
        GameObject g = new GameObject(Entity.FIREBALL);
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.PROJECTILE,Entity.FIREBALL.getTexture(), (byte)2,(byte)0));
        g.addComponent(new Light(0.1f,10f, new Vector3f(1f,0.8f,0.2f)));
        g.addComponent(new Speed(MovementTypes.LINEAR,500));
        g.addComponent(new LifeSpan(2f));
        g.addComponent(new Collider(true,true,false,false));
        g.addComponent(new SoundEffect());
        return g;
    }

     */

    public static GameObject enemySTAFF(Vector2f position, EnemyEnum ee)
    {
        GameObject g = new GameObject(ee);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,ee.getTexture(),(byte)2,(byte)0));
        g.addComponent(new Collider(asList(CollisionLayer.ENEMY), asList(CollisionLayer.BLOCK, CollisionLayer.ENEMY, CollisionLayer.PLAYERPROJECTILE),true,true,true,false));
        g.addComponent(new Health(10));
        g.addComponent(new Armor(10));
        g.addComponent(new Backpack(6,true));
        g.addComponent(new EffectComponent());
        g.addComponent(new Inputs(InputTypes.AIRANGED));
        g.addComponent(new Speed(MovementTypes.CONTROLLED, 100));
        g.addComponent(new Level(1));
        g.getComponent(Backpack.class).addGameObject(weapon(WeaponEnum.STAFF,g));
        g.addComponent(new Animation(0.2f));
        //h = new GameObject()
        return g;
    }
    public static GameObject enemySWORD(Vector2f position, EnemyEnum ee)
    {
        GameObject g = new GameObject(ee);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(OBJECTSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,ee.getTexture(),(byte)2,(byte)0));
        g.addComponent(new Collider(asList(CollisionLayer.ENEMY), asList(CollisionLayer.BLOCK, CollisionLayer.ENEMY, CollisionLayer.PLAYERPROJECTILE),true,true,true,false));
        g.addComponent(new Health(10));
        g.addComponent(new Armor(10));
        g.addComponent(new Backpack(6,true));
        g.addComponent(new EffectComponent());
        g.addComponent(new Inputs(InputTypes.AIMELEE));
        g.addComponent(new Speed(MovementTypes.CONTROLLED, 100));
        g.addComponent(new Level(1));
        g.getComponent(Backpack.class).addGameObject(weapon(WeaponEnum.SWORD,g));
        g.addComponent(new Animation(0.2f));
        //h = new GameObject()
        return g;
    }
    public static GameObject boss(Vector2f position, BossEnum boss)
    {
        GameObject g = new GameObject(boss);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(64,64)));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,boss.getTexture(), (byte)2,(byte)1));
        g.addComponent(new Collider(CollisionLayer.DEFAULT, asList(CollisionLayer.BLOCK,CollisionLayer.PLAYER ,CollisionLayer.ENEMY, CollisionLayer.PLAYERPROJECTILE),true,true,true,false));
        g.addComponent(new Health(boss.getHealth()));
        g.addComponent(new Armor(10));
        g.addComponent(new Backpack(1,false));
        GameObject a = trapdoor(position);
        a.removeComponent(Position.class);
        g.getComponent(Backpack.class).addGameObject(a);
        g.addComponent(new EffectComponent());
        g.addComponent(new Inputs(InputTypes.AIMELEE));
        g.addComponent(new Speed(MovementTypes.CONTROLLED, 200));
        g.addComponent(new Level(1));
        g.addComponent(new Animation(0.2f));
        g.addComponent(new BossAttack());
        return g;
    }
    public static GameObject damageNumber(Vector2f position, float dmg)
    {
        GameObject g = new GameObject(Entity.DamageText);

        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(20,20)));
        g.getComponent(TransformComponent.class).setDirection(-90);
        g.addComponent(new TextureComponent(TextureTypes.TEXT,String.valueOf((int)dmg), (byte)2,(byte)0));
        g.addComponent(new LifeSpan(1.5f));
        g.addComponent(new Speed(MovementTypes.LINEAR,30));
       return  g;
    }
    public static GameObject xpOrb(Vector2f position)
    {
        GameObject g = new GameObject(Entity.DamageText);

        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(20,20)));
        //g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,(byte)2,Entity.XPORB.textureString));
        g.addComponent(new LifeSpan(10f));


        return  g;
    }
    /*
    public static GameObject helmet(Vector2f position)
    {
        GameObject g = new GameObject(Entity.HELMET.name);
        g.addComponent(new TransformComponent(ITEMSIZE));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.HELMET.textureString,(byte)3,(byte)1));
        g.addComponent(new Pickup(null,0, ItemRarity.generateRarity()));
        //g.addComponent(new Damage(10));
        g.addComponent(new Collider(true,false,false,false));
        g.addComponent(new Animation(0.2f));
        return g;
    }

     */
    public static GameObject trapdoor(Vector2f position)
    {
        GameObject g = new GameObject(Entity.TRAPDOOR);
        g.addComponent(new Position(position,g));
        g.addComponent(new TransformComponent(new Vector2f(64,64)));
        g.addComponent(new TextureComponent(TextureTypes.RECTANGLE,Entity.TRAPDOOR.textureString,(byte)2,(byte)0));
        g.addComponent(new Collider(CollisionLayer.DEFAULT, CollisionLayer.PLAYER,true,false,false,false));
        g.addComponent(new Light(0.3f,3f, new Vector3f(1f,0.8f,1f)));
        return g;
    }
    public static GameObject genObject(Entity e, Object... o)
    {
        for (Object o1:o)
        {
            //get entity parameters
            //cast the o objects to the parameter types
            //inject the parameters into the addComponents
        }
        return null;

        //new data type called entityParameters<Entity>
        /**
         * entityParameters<PLAYER>
         *     entityParameters(vectory, dmg)
         * </PLAYER>
         */
    }




}
