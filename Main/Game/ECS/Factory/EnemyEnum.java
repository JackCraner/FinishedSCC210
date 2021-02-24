package Main.Game.ECS.Factory;

import java.util.Random;

public enum EnemyEnum implements FactorID
{
    SKELETON("Enemy", "SkeletonSpriteSheet1.png" ),GOBLIN("Enemy", "GoblinSpriteSheet1.png"),
    ELF("Enemy", "GoblinSpriteSheet2.png"), RAGER("Enemy", "SkeletonSpriteSheet2.png"), DARKIN("Enemy", "SkeletonSpriteSheet3.png");

    private String name;
    private String texture;
    EnemyEnum(String name, String texture)
    {
        this.name = name;
        this.texture = texture;
    }

    public static EnemyEnum randomEnemy()
    {
        int randomBinaryEnemy = new Random().nextInt(EnemyEnum.values().length);
        int counter =1;
        for (EnemyEnum e:EnemyEnum.values())
        {
            if (counter == randomBinaryEnemy)
            {
                return e;
            }
            counter ++;
        }
        return ELF;
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
