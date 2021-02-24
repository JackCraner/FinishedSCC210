package Main.Game.ECS.Factory;

import java.io.File;

public enum Entity implements FactorID
{
    PLACEHOLDER("NULL","NULL"),
    PLAYER("Player", "Entities" + File.separator + "PlayerSpriteSheet.png"),
    TORCH("Torch", "Fixtures" + File.separator +"Torch.png"),
    CHEST("Chest", "Fixtures" + File.separator + "chest2.png"),
    ENEMY("Enemy", "Entities" + File.separator + "SkeletonSpriteSheet1.png"),
    DamageText("Text", "Text"),

    TRAPDOOR("Door","Fixtures" + File.separator +"Ladder.png"),
    PARTICLE("Particle", "null");
    //FIREBALL("Fire Ball", "Projectiles" + File.separator + "FireBall.png"),
    //SWOOSH("Swing", "Projectiles" + File.separator + "SwordAttackpng.png");





    public final String name;
    public final String textureString;
    Entity(String name, String textureString)
    {
        this.name = name;
        this.textureString = textureString;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTexture() {
        return textureString;
    }
}
