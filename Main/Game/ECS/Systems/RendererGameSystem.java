package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ComponentENUMs.TextureTypes;
import Main.Game.ECS.Components.ItemComponents.Pickup;

import Main.Game.ECS.Components.SpecialComponents.ArmorContainer;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.Animation;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TextureComponent;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Components.StatComponents.Armor;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.*;
import Main.Game.Game;
import Main.Game.Managers.MapManager;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class RendererGameSystem  extends GameSystem
{
    private static RendererGameSystem systemInstance = new RendererGameSystem();

    private Layer[] graphicLayers = {new Layer(),new Layer(), new Layer(), new Layer()};
    public RenderTexture screenTexture = new RenderTexture();
    public RenderTexture backgroundTexture = new RenderTexture();
    public Sprite backgroundSprite = new Sprite();
    private RenderStates rS;
    public Sprite screenSprite = new Sprite();
    public VertexArray backGround = new VertexArray(PrimitiveType.QUADS);
    private Font font= new Font();

    private static Vector2f standardRenderSize = new Vector2f(32,32);
    private RendererGameSystem()
    {
        setBitMaskRequirement(BitMasks.produceBitMask(TextureComponent.class, Position.class, TransformComponent.class));
        try
        {
            font.loadFromFile(Paths.get("Assets" + File.separator + "Fonts" + File.separator+ "LEMONMILK-Regular.otf"));
            screenTexture.create(Game.WINDOWSIZE, Game.WINDOWSIZE);
            screenTexture.setView(Camera.cameraInstance().camerView);
            backgroundTexture.create(Game.WINDOWSIZE, Game.WINDOWSIZE);
            backgroundTexture.setView(Camera.cameraInstance().camerView);
        }
        catch (Exception e)
        {
            System.out.println("Render System: " + e);
        }

    }
    @Override
    public void update(float dt)
    {

        // currently we calculate and draw every frame
        // need an event that something has moved (SpeedSystem) to order this system to calculate the frame and draw
        // otherwise we just draw (big performance boost)

        //System.out.println("Num Objects: " +getGameObjectList().transform.getSize()());


        //buildVertexArray();
        Vector2f curPos;
        TransformComponent transform;
        TextureComponent texture;
        if (Game.getGame().isRunning)
        {
            backGround.clear();
            RendererGameSystem.getSystemInstance().screenTexture.clear(new Color(43,39,39,0));
            RendererGameSystem.getSystemInstance().backgroundTexture.clear(new Color(220,200,200,255));
        }
        for(GameObject g: getGameObjectList())
        {
            curPos = g.getComponent(Position.class).getPosition();
            transform = g.getComponent(TransformComponent.class);
            texture = g.getComponent(TextureComponent.class);

            if (texture.texturetype == TextureTypes.BLOCK)
            {
                //// SO MANY VECTORS :CCCCC
                Vertex[] vertexArray = new Vertex[4];
                Vector2f[] texCordArray ={
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation, 0),
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation + Blueprint.TEXTURESIZE.x, 0),
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation + Blueprint.TEXTURESIZE.x, +Blueprint.TEXTURESIZE.x),
                        new Vector2f(Blueprint.TEXTURESIZE.x * texture.tileMapLocation, +Blueprint.TEXTURESIZE.x)
                };
                Vector2f[] texCords = new Vector2f[4];
                if (transform.getDirection() == 90)
                {

                    texCords[0] = texCordArray[1];
                    texCords[1] = texCordArray[2];
                    texCords[2] = texCordArray[3];
                    texCords[3] = texCordArray[0];

                }
                if (transform.getDirection() == 95)
                {

                    texCords[0] = texCordArray[0];
                    texCords[1] = texCordArray[3];
                    texCords[2] = texCordArray[2];
                    texCords[3] = texCordArray[1];

                }
                else if (transform.getDirection() == 180)
                {

                    texCords[0] = texCordArray[3];
                    texCords[1] = texCordArray[2];
                    texCords[2] = texCordArray[1];
                    texCords[3] = texCordArray[0];

                }
                else if (transform.getDirection() == 185)
                {

                    texCords[0] = texCordArray[2];
                    texCords[1] = texCordArray[1];
                    texCords[2] = texCordArray[0];
                    texCords[3] = texCordArray[3];

                }
                else if (transform.getDirection() == 270)
                {

                    texCords[0] = texCordArray[2];
                    texCords[1] = texCordArray[3];
                    texCords[2] = texCordArray[0];
                    texCords[3] = texCordArray[1];

                }
                else
                {
                    texCords[0] = texCordArray[0];
                    texCords[1] = texCordArray[1];
                    texCords[2] = texCordArray[2];
                    texCords[3] = texCordArray[3];
                }
                backGround.add(new Vertex(curPos,texCords[0]));
                backGround.add(new Vertex(new Vector2f(curPos.x, curPos.y + transform.getSize().y),texCords[1]));
                backGround.add(new Vertex(new Vector2f(curPos.x + transform.getSize().x, curPos.y + transform.getSize().y),texCords[2]));
                backGround.add(new Vertex(new Vector2f(curPos.x + transform.getSize().x, curPos.y),texCords[3]));






            }
            if(texture.texturetype == TextureTypes.RECTANGLE)
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(new Vector2f(curPos.x, curPos.y));
                s.setSize(transform.getSize());
                s.setScale(transform.getScale().x,transform.getScale().y);
                s.setTexture(TextureMap.TEXTUREMAP.get(texture.textureString));
                if (BitMasks.checkIfContains(g.getBitmask(), Animation.class))
                {
                    Animation anim = g.getComponent(Animation.class);
                    texture.tileMapLocation = (byte)(anim.spriteSheetAnimation + anim.spriteSheetDirection);
                }
                if (texture.tileMapLocation >= 0)
                {
                    s.setTextureRect(new IntRect(texture.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                }

                graphicLayers[texture.layer-1].addRectangle(s);

                if((g.getBitmask() & BitMasks.getBitMask(Backpack.class)) != 0 && g.getComponent(Backpack.class).getBackPackSelect() != null && g.getComponent(Backpack.class).getCanUseItems())
                {
                    RectangleShape s1 = new RectangleShape();
                    Backpack b = g.getComponent(Backpack.class);
                    if (b.getBackPackSelect().getComponent(Pickup.class).getItemType() instanceof PotionEnum)
                    {
                        s1.setPosition(new Vector2f(curPos.x-(transform.isFacingRight()? -15:5), curPos.y + 10));
                        s1.setSize(b.getBackPackSelect().getComponent(TransformComponent.class).getSize());
                    }
                    else
                    {
                        s1.setPosition(new Vector2f(curPos.x-(transform.isFacingRight()? -15:15), curPos.y));
                        s1.setSize(standardRenderSize);
                    }
                    s1.setRotation(b.getBackPackSelect().getComponent(TransformComponent.class).getDirection());
                    TextureComponent mainHandTexture =b.getObjectsINBACKPACK().get(b.getCurrentBackpackPosition()).getComponent(TextureComponent.class);
                    s1.setTexture(TextureMap.TEXTUREMAP.get(mainHandTexture.textureString));

                    if (mainHandTexture.tileMapLocation >= 0)
                    {
                        s1.setTextureRect(new IntRect(mainHandTexture.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                    }

                    graphicLayers[mainHandTexture.layer - 1].addRectangle(s1);
                }
                //Test
                if(BitMasks.checkIfContains(g.getBitmask(), ArmorContainer.class))
                {
                    for(GameObject armor: g.getComponent(ArmorContainer.class).getArmor())
                    {
                        if (armor != null)
                        {
                            RectangleShape s1 = new RectangleShape();
                            s1.setPosition(new Vector2f(curPos.x, curPos.y));
                            s1.setSize(standardRenderSize);
                            s1.setScale(transform.getScale().x,transform.getScale().y);
                            TextureComponent armorTex = armor.getComponent(TextureComponent.class);
                            s1.setTexture(TextureMap.TEXTUREMAP.get(armorTex.textureString));
                            if (BitMasks.checkIfContains(armor.getBitmask(),Animation.class))
                            {
                                Animation anim = armor.getComponent(Animation.class);
                                armor.getComponent(TextureComponent.class).tileMapLocation = (byte)(anim.spriteSheetAnimation + anim.spriteSheetDirection);
                            }
                            if (armorTex.tileMapLocation >= 0)
                            {
                                s1.setTextureRect(new IntRect(armorTex.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                            }
                            graphicLayers[armorTex.layer - 1].addRectangle(s1);
                        }

                    }

                }
            }
            if (texture.texturetype == TextureTypes.TEXT)
            {
                Text t = new Text(texture.textureString,font,(int)transform.getSize().x);
                t.setColor(Color.YELLOW);
                t.setPosition(new Vector2f(curPos.x, curPos.y));
                graphicLayers[texture.layer - 1].addText(t);
            }
            if(texture.texturetype == TextureTypes.PARTICLE)
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(curPos);
                s.setSize(transform.getSize());
                try {
                    int[] rgbData =  Arrays.asList(texture.textureString.split(",")).stream().mapToInt(Integer::parseInt).toArray();
                    s.setFillColor(new Color(rgbData[0],rgbData[1],rgbData[2]));
                }
                catch (Exception e)
                {
                    System.out.println("Color Not Found");
                }
                graphicLayers[texture.layer - 1].addRectangle(s);


            }
            if(texture.texturetype == TextureTypes.PROJECTILE)
            {
                RectangleShape s = new RectangleShape();
                s.setPosition(new Vector2f(curPos.x, curPos.y));
                s.setSize(standardRenderSize);
                s.setRotation(transform.getDirection());
                s.setScale(transform.getScale().x,transform.getScale().y);
                s.setTexture(TextureMap.TEXTUREMAP.get(texture.textureString));
                if (BitMasks.checkIfContains(g.getBitmask(), Animation.class))
                {
                    Animation anim = g.getComponent(Animation.class);
                    texture.tileMapLocation = (byte)(anim.spriteSheetAnimation + anim.spriteSheetDirection);
                }
                if (texture.tileMapLocation >= 0)
                {
                    s.setTextureRect(new IntRect(texture.tileMapLocation * (int)standardRenderSize.x, 0,(int)standardRenderSize.x,(int)standardRenderSize.y));
                }
                graphicLayers[texture.layer-1].addRectangle(s);
            }

        }




        Camera.cameraInstance().camerView.setCenter(Game.PLAYER.getComponent(Position.class).getPosition());
        screenTexture.setView(Camera.cameraInstance().camerView);
        backgroundTexture.setView(Camera.cameraInstance().camerView);

        backgroundTexture.draw(backGround,new RenderStates(TextureMap.TEXTUREMAP.get(MapManager.getManagerInstance().getMapID().block.getTexture())));
        for (Layer layer: graphicLayers)
        {
            screenTexture.draw(layer);
        }




        screenTexture.display();
        backgroundTexture.display();
        backgroundSprite.setTexture(backgroundTexture.getTexture());
        screenSprite.setTexture(screenTexture.getTexture());

        //Level.getLevel().getWindow().draw(backGround,new RenderStates(textureMap.get((byte) 0)));
        //Player is always index 0 on the list of Objects



    }




    public static RendererGameSystem getSystemInstance() {
        return systemInstance;
    }

}



class Layer implements Drawable
{
    ArrayList<RectangleShape> drawablesInLayer = new ArrayList<>();
    ArrayList<Text> textInLayer = new ArrayList<>();
    public Layer()
    {

    }
    public void addRectangle(RectangleShape d)
    {
        drawablesInLayer.add(d);
    }
    public void addText(Text t)
    {
        textInLayer.add(t);
    }
    @Override
    public void draw(RenderTarget renderTarget, RenderStates renderStates)
    {
        for(RectangleShape d: drawablesInLayer)
        {
            Transform t = new Transform();
            t = Transform.rotate(t,d.getRotation(),d.getPosition().x + d.getSize().x/2,d.getPosition().y+d.getSize().y/2);
            t = Transform.scale(t,d.getScale(),new Vector2f(d.getPosition().x + d.getSize().x/2,d.getPosition().y+d.getSize().y/2));
            d.setRotation(0);
            d.setScale(1,1);
            RenderStates r = new RenderStates(renderStates,t);
            renderTarget.draw(d,r);
        }
        for (Text t: textInLayer)
        {
            renderTarget.draw(t);
        }
        if (Game.getGame().isRunning)
        {
            drawablesInLayer.clear();
            textInLayer.clear();
        }

    }
}

