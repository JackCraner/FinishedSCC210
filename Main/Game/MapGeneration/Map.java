package Main.Game.MapGeneration;

import Main.Game.ECS.Factory.BossEnum;
import Main.Game.ECS.Factory.FactorID;

/**
 * Different Level Values
 *      Nothing to do with seed here
 * Defines different parameters between levels
 * For example, the final level will have less torches, thus making it more dark and scary so the TorchSpawnRate should be low
 */
public enum Map
{

    MAP1(5f,200,5f,100, Block.SAND, BossEnum.SNAKE),
    MAP2(5f,100,5f,100, Block.WATER, BossEnum.WHALE),
    MAP3(5f,100,5f,100, Block.LAVA, BossEnum.NAGOR),
    MAP4(5f,100,5f,100, Block.MOSS, BossEnum.DUCK);

    public final float torchSpawnRate;
    public final float torchMinDistance;
    public final float chestSpawnRate;
    public final float chestMinDistance;
    public final Block block;
    public final BossEnum boss;
    Map(float torchSpawnRate, float torchMinDistance, float chestSpawnRate, float chestMinDistance, Block b, BossEnum boss)
    {
        this.torchSpawnRate = torchSpawnRate;
        this.torchMinDistance = torchMinDistance;
        this.chestSpawnRate = chestSpawnRate;
        this.chestMinDistance = chestMinDistance;
        this.block = b;
        this.boss = boss;

    }




    public enum Block implements FactorID
    {
        SAND("Sand Block", "SandTiles.png"), WATER ("Water Block", "WaterTiles.png"),
        LAVA("Lava Block", "LavaTiles.png"), MOSS("Moss Block", "MossTiles.png");

        private final String texture;
        private final String name;

        Block(String name, String texture)
        {
            this.texture = texture;
            this.name = name;
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

}
