package Main.Game.ECS.Factory;

import Main.Game.MapGeneration.Map;
import org.jsfml.graphics.Texture;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

public class TextureMap
{
    public static HashMap<String, Texture> TEXTUREMAP = new HashMap<>();
    static{
        for(Entity entity: Entity.values()) {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + entity.textureString));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(entity.textureString, t);
        }

        for(ArmorEnum.ArmorType t : ArmorEnum.ArmorType.values())
        {
            for(ArmorEnum.ArmorPiece p : ArmorEnum.ArmorPiece.values())
            {
                ArmorEnum ae = new ArmorEnum(t,p);
                Texture tex = new Texture();
                try
                {
                    tex.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Armor" + File.separator + ae.getTexture()));
                } catch (Exception e) {
                    System.out.println("TextureMap Load: " + e);
                }
                TEXTUREMAP.put(ae.getTexture(), tex);
            }
        }

        for(BossEnum b: BossEnum.values())
        {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Entities" + File.separator + "Bosses" + File.separator+  b.getTexture()));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(b.getTexture(), t);
        }
        for(PotionEnum b: PotionEnum.values())
        {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Potions" + File.separator +  b.getTexture()));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(b.getTexture(), t);
        }
        for(Map.Block b: Map.Block.values())
        {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Blocks" + File.separator +  b.getTexture()));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(b.getTexture(), t);
        }
        for(WeaponEnum.WeaponPiece b : WeaponEnum.WeaponPiece.values())
        {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Weapons" + File.separator +  b.getTexture()));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(b.getTexture(), t);
        }
        for(WeaponEnum.Projectile b : WeaponEnum.Projectile.values())
        {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Projectiles" + File.separator +  b.getTexture()));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(b.getTexture(), t);
        }
        for(EnemyEnum ene: EnemyEnum.values())
        {
            Texture t = new Texture();
            try
            {
                t.loadFromFile(Paths.get("Assets" + File.separator + "EntityTextures" + File.separator + "Entities" + File.separator +  ene.getTexture()));
            } catch (Exception e) {
                System.out.println("TextureMap Load: " + e);
            }
            TEXTUREMAP.put(ene.getTexture(), t);
        }

    }

}
