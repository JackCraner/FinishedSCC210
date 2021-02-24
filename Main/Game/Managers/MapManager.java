package Main.Game.Managers;

import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIModeEnum;
import Main.Game.MapGeneration.CellularA.CellularAutomata;
import Main.Game.MapGeneration.Map;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapManager
{
    private Random currentSeed = new Random();
    private Map mapID;
    private Boolean nextRoomIsMap;
    private static MapManager managerInstance = new MapManager();
    private ArrayList<GameObject> blocks = new ArrayList<>();
    private ArrayList<GameObject> fixtures = new ArrayList<>();
    private Vector2f playerPosition;


    public int MAPSIZEBLOCKSX = 100;
    public int MAPSIZEBLOCKSY = 100;
    public float EMPTYPERCMAP = 0.53f;
    public int BOSSROOMSIZEBLOCKX = 40;
    public int BOSSROOMSIZEBLOCKY = 40;
    public float EMPTYPERCBOSSROOM = 0.58f;
    public int MAPSIZEPIXELSX = MAPSIZEBLOCKSX * (int)Blueprint.BLOCKSIZE.x;
    public int MAPSIZEPIXELSY = MAPSIZEBLOCKSY * (int)Blueprint.BLOCKSIZE.y;

    private byte[][] binaryMapping;

    private boolean start = true;
    
    public static MapManager getManagerInstance() {
        return managerInstance;
    }

    private MapManager()
    {
        //default mapID
        nextRoomIsMap = true;
        mapID = Map.MAP1;
    }


    public ArrayList<GameObject> updateMap()
    {
        blocks.clear();
        fixtures.clear();
        playerPosition = null;


        nextRoomIsMap =!nextRoomIsMap;
        if (nextRoomIsMap)
        {
                createRoom(BOSSROOMSIZEBLOCKX,BOSSROOMSIZEBLOCKY,EMPTYPERCBOSSROOM);
                GameObject g = Blueprint.boss(Blueprint.convertToGlobal(new Vector2i(20,20)),mapID.boss);
                fixtures.add(g);
                GUIManager.getGUIinstance().enterBossRoom(g);

        }
        else
        {
            if (!start)
            {
                if (mapID == Map.MAP1)
                {
                    setMapID(Map.MAP2);
                }
                else if (mapID == Map.MAP2)
                {
                    setMapID(Map.MAP3);
                }
                else if (mapID == Map.MAP3)
                {
                    setMapID(Map.MAP4);
                }
                else {
                    GUIManager.getGUIinstance().swapModes(GUIModeEnum.FSCREEN);
                    GUIManager.getGUIinstance().GUIUpdateALL();
                }
            }
            else
            {
                start = false;
            }
            createRoom(MAPSIZEBLOCKSX,MAPSIZEBLOCKSY,EMPTYPERCMAP);
            createFixtures();
            GUIManager.getGUIinstance().leaveBossRoom();




        }

        ArrayList<GameObject> output = new ArrayList<>();
        output.addAll(blocks); output.addAll(fixtures);

        return output;



    }

    public void setCurrentSeed(int currentSeed) {
        this.currentSeed.setSeed(currentSeed);
    }

    public void setMapID(Map mapID) {
        this.mapID = mapID;
    }

    public Vector2f getPlayerPosition() {
        return playerPosition;
    }

    public Map getMapID() {
        return mapID;
    }

    public void createRoom(int sizeX, int sizeY, float initalEmptyRate)
    {
        binaryMapping = CellularAutomata.getInstance().generateBinaryMapping(currentSeed,sizeX,sizeY, initalEmptyRate);
        playerPosition = setPlayerPos(binaryMapping,0,0,sizeX,sizeY);
        for (int a = 1; a <sizeX; a++) {
            for (int b = 1; b < sizeY; b++) {

                Vector2f pos = new Vector2f(a * Blueprint.BLOCKSIZE.x, b * Blueprint.BLOCKSIZE.y);

                if (binaryMapping[a][b] == CellularAutomata.WALLID) {
                    blocks.add(Blueprint.block(pos, findWallAngle(binaryMapping, a, b), 95, mapID));


                }
            }
        }

        for(int a = 0; a < sizeX; a++)
        {
            blocks.add( Blueprint.block(new Vector2f(a * Blueprint.BLOCKSIZE.x,0), (byte) 3,95,mapID));
            blocks.add( Blueprint.block(new Vector2f(a * Blueprint.BLOCKSIZE.x,sizeY* Blueprint.BLOCKSIZE.y),(byte) 3,185,mapID));
        }
        for(int b =0; b<sizeY; b++)
        {
            blocks.add( Blueprint.block(new Vector2f(sizeX * Blueprint.BLOCKSIZE.x,b * Blueprint.BLOCKSIZE.y),(byte) 5, 95,mapID));
            blocks.add( Blueprint.block(new Vector2f(0,b * Blueprint.BLOCKSIZE.y),(byte) 1, 95,mapID));
        }
    }


    public void createFixtures()
    {
        fixtures.add(placeDoor(binaryMapping));

        for(int a = 1; a <binaryMapping.length; a++)
        {
            for(int b =1; b<binaryMapping[0].length; b++)
            {

                Vector2f pos = new Vector2f(a * Blueprint.BLOCKSIZE.x, b*Blueprint.BLOCKSIZE.y);

                if (binaryMapping[a][b] == CellularAutomata.WALLID)
                {
                    try
                    {
                        if (binaryMapping[a][b+1] == CellularAutomata.EMPTYID)
                        {
                            if ( currentSeed.nextFloat() < generateProbability(mapID.torchSpawnRate, findDistance(pos, Entity.TORCH.name), mapID.torchMinDistance))
                            {
                                fixtures.add(Blueprint.torch(pos));
                            }
                            if ( currentSeed.nextFloat() < generateProbability(mapID.chestSpawnRate, findDistance(pos, Entity.CHEST.name), mapID.chestMinDistance))
                            {
                                fixtures.add(Blueprint.chest(new Vector2f(pos.x, pos.y + Blueprint.BLOCKSIZE.y)));
                            }
                        }
                    }
                    catch (Exception e)
                    {

                    }






                }
                else
                {

                }






            }
        }
    }
    public ArrayList<GameObject> createNewMap(Map mapID,byte[][] binaryMapping)
    {
        playerPosition = setPlayerPos(binaryMapping,0,0,MAPSIZEBLOCKSX,MAPSIZEPIXELSY);

        fixtures.add(placeDoor(binaryMapping));

        for(int a = 1; a <MAPSIZEBLOCKSX; a++)
        {
            for(int b =1; b<MAPSIZEBLOCKSY; b++)
            {

                Vector2f pos = new Vector2f(a * Blueprint.BLOCKSIZE.x, b*Blueprint.BLOCKSIZE.y);

                if (binaryMapping[a][b] == CellularAutomata.WALLID)
                {
                   blocks.add(Blueprint.block(pos,findWallAngle(binaryMapping,a,b),95,mapID));

                    try
                    {
                        if (binaryMapping[a][b+1] == CellularAutomata.EMPTYID)
                        {
                            if ( currentSeed.nextFloat() < generateProbability(mapID.torchSpawnRate, findDistance(pos, Entity.TORCH.name), mapID.torchMinDistance))
                            {
                                fixtures.add(Blueprint.torch(pos));
                            }
                            if ( currentSeed.nextFloat() < generateProbability(mapID.chestSpawnRate, findDistance(pos, Entity.CHEST.name), mapID.chestMinDistance))
                            {
                                fixtures.add(Blueprint.chest(new Vector2f(pos.x, pos.y + Blueprint.BLOCKSIZE.y)));
                            }
                        }
                    }
                    catch (Exception e)
                    {

                    }






                }
                else
                {

                }






            }
        }
        //add outer wall
        for(int a = 0; a < MAPSIZEBLOCKSX; a++)
        {
            blocks.add( Blueprint.block(new Vector2f(a * Blueprint.BLOCKSIZE.x,0), (byte) 3,95,mapID));
            blocks.add( Blueprint.block(new Vector2f(a * Blueprint.BLOCKSIZE.x,MAPSIZEBLOCKSY * Blueprint.BLOCKSIZE.y),(byte) 3,185,mapID));
        }
        for(int b =0; b<MAPSIZEBLOCKSX; b++)
        {
            blocks.add( Blueprint.block(new Vector2f(MAPSIZEBLOCKSX * Blueprint.BLOCKSIZE.x,b * Blueprint.BLOCKSIZE.y),(byte) 5, 95,mapID));
            blocks.add( Blueprint.block(new Vector2f(0,b * Blueprint.BLOCKSIZE.y),(byte) 1, 95,mapID));
        }
        //placeDoor(binaryMapping);
        ArrayList<GameObject> output = new ArrayList<>();
        output.addAll(blocks); output.addAll(fixtures);
        return output;
    
    }

    /**
     * To Ensure no chests or torches spawn next to each other and spawn at nice distances
     * the probability a Fixture GameObject spawns is a function of bias probability based of parameters of
     * distance from next nearest similar Fixture and the spawnRate for the fixture in the mapID
     *
     * -- Meaning a torch/ chest should never spawn next to each other
     * -- Meaning a torch/ chest should always spawn at least X distance apart (No void spaces)
     * @param position Position being checked
     * @param name  Name of the fixture being checked
     * @return Distance from the next nearest fixture of the same name
     */
    public float findDistance(Vector2f position, String name)
    {
        float shortestDistance = 1000000;
        for (int i = 0; i <fixtures.size(); i++)
        {
            if(fixtures.get(i).getName() == name)
            {
                Vector2f mapObjectPosition = fixtures.get(i).getComponent(Position.class).getPosition();
                float x =(float)Math.pow((mapObjectPosition.x - position.x),2);
                float y =(float)Math.pow((mapObjectPosition.y - position.y),2);
                shortestDistance = Math.min((float)Math.pow(x + y, 0.5),shortestDistance);

            }
        }
        //System.out.println(shortestDistance);
        return shortestDistance;

    }

    /**
     * Function of bias probability given the parameters
     * @param exponent value within the MapID
     * @param distance distance found from FindDistance()
     * @param minDistance the minimum spawn distance
     * @return the probability value between 0 and 1
     */
    public float generateProbability(float exponent, float distance, float minDistance)
    {

        return (float)(Math.pow((1 + (exponent/10000)), (distance -minDistance))-1);
    }


    public GameObject placeDoor(byte[][] binaryMapping)
    {
        int randX = currentSeed.nextInt(MAPSIZEBLOCKSY-1);
        int randY = currentSeed.nextInt(10);
        randY =(MAPSIZEBLOCKSY - randY);


        if (CellularAutomata.checkNeighbours8(binaryMapping,randX,randY,MAPSIZEBLOCKSX,MAPSIZEBLOCKSY) == 9)
        {
            randY =(int) Blueprint.BLOCKSIZE.y * randY;
            randX =(int) Blueprint.BLOCKSIZE.x * randX;
            return (Blueprint.trapdoor(new Vector2f(randX,randY)));
        }
        else
        {
            return placeDoor(binaryMapping);
        }
    }

    public byte findWallAngle(byte[][] binaryMapping, int x, int y)
    {
        //Tile rules
        byte tilemapLocation=0;
        try
        {
            if (binaryMapping[x][y+1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation =6;

            }

            else if(binaryMapping[x-1][y] == CellularAutomata.EMPTYID && binaryMapping[x][y-1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=2;

            }
            else if(binaryMapping[x-1][y] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=1;

            }
            else if(binaryMapping[x][y-1] == CellularAutomata.EMPTYID && binaryMapping[x+1][y] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=4;

            }
            else if (binaryMapping[x+1][y+1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=5;

            }
            else if (binaryMapping[x-1][y+1] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=1;

            }

            else if(binaryMapping[x+1][y] == CellularAutomata.EMPTYID)
            {
                tilemapLocation=5;

            }
            else if(CellularAutomata.checkNeighbours4(binaryMapping,x,y,MAPSIZEBLOCKSX,MAPSIZEBLOCKSY) != 0)
            {
                tilemapLocation=3;

            }
        }
        catch(Exception e)
        {

        }
        return (tilemapLocation);


    }


    public Vector2f setPlayerPos(byte[][] binaryMapping, int x, int y, int mapsizeX, int mapsizeY)
    {
        int baseY = 10;

        if (CellularAutomata.checkNeighbours8(binaryMapping,mapsizeX/2+x,baseY+y, mapsizeX,mapsizeY)  == 9)
        {

            return Blueprint.convertToGlobal(new Vector2i((mapsizeX/2)+x,baseY+y));
        }
        else
        {
            if (x > mapsizeX -2)
            {
                return setPlayerPos(binaryMapping, 0,y+1,mapsizeX,mapsizeY);
            }
            else
            {

                return setPlayerPos(binaryMapping, x+1,y,mapsizeX,mapsizeY);
            }

        }
    }

    public boolean checkTile(Vector2f globalPos)
    {
        Vector2i localPos = Blueprint.convertToLocal(globalPos);
        try
        {
            return (binaryMapping[localPos.x][localPos.y] == CellularAutomata.EMPTYID);
        }
        catch(Exception e)
        {
            return false;
        }

    }
    public boolean checkTile8(Vector2f globalPos)
    {
        Vector2i localPos = Blueprint.convertToLocal(globalPos);
        return checkTile8(localPos);


    }
    public boolean checkTile8(Vector2i localPos)
    {
        try
        {
            return (CellularAutomata.checkNeighbours8(binaryMapping,localPos.x,localPos.y,MAPSIZEBLOCKSX,MAPSIZEBLOCKSY) == 9);
        }
        catch(Exception e)
        {
            return false;
        }

    }


    public int getMAPSIZEBLOCKSX() {
        return MAPSIZEBLOCKSX;
    }

    public int getMAPSIZEBLOCKSY() {
        return MAPSIZEBLOCKSY;
    }

    public int getMAPSIZEPIXELSX() {
        return MAPSIZEPIXELSX;
    }

    public int getMAPSIZEPIXELSY() {
        return MAPSIZEPIXELSY;
    }

    public Boolean getNextRoomIsMap() {
        return nextRoomIsMap;
    }
}
