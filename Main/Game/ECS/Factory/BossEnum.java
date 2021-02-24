package Main.Game.ECS.Factory;

public enum BossEnum implements FactorID
{
    SNAKE("Snake Boss", "SnakeBoss.png",100),
    DUCK("Duck Boss", "DuckBossSpriteSheet.png",500),
    WHALE("Whale Boss", "WhaleBossSpriteSheet.png",1000),
    NAGOR("Nagor Boss", "NagorSpriteSheet.png",2000);

    private final String name;
    private final String texture;
    private final float health;
    BossEnum(String name, String texture, float health)
    {
        this.name = name;
        this.texture = texture;
        this.health = health;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTexture() {
        return texture;
    }

    public float getHealth() {
        return health;
    }
}
