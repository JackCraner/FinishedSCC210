package Main.Game.ECS.Factory;

public class ArmorEnum implements FactorID
{

    public enum ArmorType
    {
        PLATE("Plate","Plate_"),LEATHER("Leather","Leather_"),CHAIN("Chain","Chain_");

        final String textureStart;
        final String nameStart;
        ArmorType(String nameStart, String textureStart)
        {
            this.textureStart = textureStart;
            this.nameStart = nameStart;
        }

        public String getTextureStart() {
            return textureStart;
        }

        public String getNameStart() {
            return nameStart;
        }
    }
    public enum ArmorPiece implements ItemTypes<ArmorPiece>
    {
        HELMET("Helmet","1.png"),CHEST("Chest","2.png"),LEGGINGS("Leggings","3.png");

        final String textureEnd;
        final String nameEnd;
        ArmorPiece(String nameEnd,String textureEnd)
        {
            this.textureEnd = textureEnd;
            this.nameEnd = nameEnd;
        }


        public String getNameEnd() {
            return nameEnd;
        }

        public String getTextureEnd() {
            return textureEnd;
        }

        @Override
        public ArmorPiece getType() {
            return this;
        }
    }


    final String texture;
    final String name;
    final ArmorPiece aP;
    public ArmorEnum(ArmorType aT,ArmorPiece aP)
    {
        this.texture = aT.getTextureStart() + "" + aP.getTextureEnd();
        this.name = aT.getNameStart()  + " " + aP.getNameEnd();
        this.aP = aP;
    }

    public ArmorPiece getArmorPiece() {
        return aP;
    }

    public String getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }
}
