package Main.Game.ECS.Factory;

import Main.Game.ECS.Components.Component;
import Main.Game.ECS.Components.StatComponents.Strength;
import Main.Game.ECS.Components.StatComponents.Wisdom;
import Main.Game.ECS.Entity.GameObject;

import java.lang.reflect.Method;

public enum WeaponEnum implements FactorID
{

    STAFF(WeaponPiece.WAND, Projectile.FIREBALL),
    SWORD(WeaponPiece.SWORD,Projectile.SWOOSH);

    public enum Modifier
    {
        STRENGTH(Strength.class),WISDOM(Wisdom.class);

        Class<? extends Component> type;
        Modifier(Class<? extends Component> type)
        {
            this.type = type;
        }

        public Class<? extends Component> getType() {
            return type;
        }
    }
    public enum WeaponPiece implements ItemTypes<WeaponPiece>
    {
        WAND("Wand" , "Demon_Staff_2.png"),SWORD("Katana","Katana_1.png");

        String texture;
        String name;
        WeaponPiece(String name, String texture)
        {
            this.texture = texture;
            this.name = name;
        }

        public String getTexture() {
            return texture;
        }

        public String getName() {
            return name;
        }

        @Override
        public WeaponPiece getType() {
            return this;
        }
    }
    public enum Projectile implements FactorID
    {
        FIREBALL("Fire Ball", "FireBall.png",Modifier.WISDOM, 2f,2f,500,10),
        SWOOSH("Swing", "SwordAttackpng.png",Modifier.STRENGTH,0.5f,0.25f,100,5);

        String name;
        String texture;
        Modifier mod;
        float cooldown;
        float lifeSpan;
        float speed;
        float baseDamage;
        Projectile(String name, String texture, Modifier mod, float cooldown, float lifeSpan,float speed, float baseDamage)
        {
            this.name = name;
            this.texture = texture;
            this.mod = mod;
            this.cooldown = cooldown;
            this.lifeSpan = lifeSpan;
            this.speed = speed;
            this.baseDamage =baseDamage;
        }


        public float getBaseDamage() {
            return baseDamage;
        }

        public float getCooldown() {
            return cooldown;
        }

        public Modifier getMod() {
            return mod;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getTexture() {
            return texture;
        }
    }

    final WeaponPiece body;
    final Projectile projectile;
    WeaponEnum(WeaponPiece b, Projectile p)
    {
        this.body = b;

        this.projectile = p;
    }

    public WeaponPiece getBody() {
        return body;
    }


    public Projectile getProjectile() {
        return projectile;
    }
    @Override
    public String getName() {
        return body.getName();
    }

    @Override
    public String getTexture() {
        return body.getTexture();
    }

}
