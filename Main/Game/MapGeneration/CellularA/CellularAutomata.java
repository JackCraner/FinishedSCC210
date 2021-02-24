package Main.Game.MapGeneration.CellularA;



import Main.Game.ECS.Factory.Blueprint;
import org.jsfml.system.Vector2i;

import java.util.*;
//Creates the skeleton of the map using Cellular Automata
//https://en.wikipedia.org/wiki/Cellular_automaton

public class CellularAutomata
{


    //parameters of creation;
    public static byte WALLID = 0;  //ID of wall blocks
    public static byte EMPTYID = 1; //ID of empty blocks

    private float initalEmptyFaceRate = 0.53f;    //0.54
    private int recursionDepth = 3; //Depth of the cellular Automata

    int totalEmptySpace;        //number number of empty blocks
    
    private int mapX,mapY;
    private byte[][] binaryMapping;
    private static CellularAutomata caInstance = new CellularAutomata();

    public static CellularAutomata getInstance()
    {
        return caInstance;
    }

    /**
     * Generates a random 2D array of 1s and 0s of size mapX and mapY
     * @param randomNumberGenerator Random number generater seed (defines the seed of the map)
     * @return  The 2D array
     */
    public byte[][] startMap(Random randomNumberGenerator)
    {

        byte[][] binaryMapping = new byte[mapX][mapY];
        for (int a = 0; a <mapX; a++)
        {
            for (int b = 0; b<mapY; b++)
            {
                if (randomNumberGenerator.nextFloat() > initalEmptyFaceRate)
                {
                    binaryMapping[a][b] =WALLID;
                }
                else
                {
                    binaryMapping[a][b] = EMPTYID;
                }

            }
        }

        return binaryMapping;


    }

    /**
     * Returns the number of empty blocks around a given point X,Y
     * @param givenMap  The Binary map to check on
     * @param x The X value of the point in the map
     * @param y The Y value of the point in the map
     * @return The number of empty blocks around the point (0 - 8)
     */
    public static int checkNeighbours8(byte[][] givenMap, int x, int y, int mapsizeX, int mapsizeY)
    {

        int spaceCount = 0;

        int xStart = Math.max(x-1, 0);
        int xEnd = Math.min(x+1,mapsizeX -1);

        int yStart = Math.max(y-1, 0);
        int yEnd = Math.min(y+1,mapsizeX -1);


        for (int a = xStart; a <= xEnd; a++)
        {
            for (int b = yStart; b<= yEnd; b++)
            {

                if (givenMap[a][b] == EMPTYID) spaceCount++;

            }
        }
        return spaceCount;


    }
    public static int checkNeighbours4(byte[][] givenMap, int x,int y, int mapsizeX, int mapsizeY)
    {
        int spaceCount = 0;

        int xStart = Math.max(x-1, 0);
        int xEnd = Math.min(x+1,mapsizeX -1);

        int yStart = Math.max(y-1, 0);
        int yEnd = Math.min(y+1,mapsizeY -1);

        for (int a = xStart; a <= xEnd ;a++)
        {
            if(givenMap[a][y] == EMPTYID) spaceCount++;
        }
        for (int b = yStart; b<= yEnd; b++)
        {

            if (givenMap[x][b] == EMPTYID) spaceCount++;

        }
        return spaceCount;

    }


    /**
     * Recursively iterates over the map, performing Cellular Automata
     * @param tempMap  The map it iterates over
     * @param depth The recursion depth
     * @return  The final map output after the Cellular Automata is applied
     */
    public byte[][] updateMap(byte[][] tempMap, int depth)
    {


        for (int a = 0; a < mapX; a++)
        {
            for (int b = 0; b< mapY; b++)
            {
                if (checkNeighbours8(tempMap, a, b,mapX,mapY) >= 5)
                {
                    tempMap[a][b] = EMPTYID;
                }
                else
                {
                    tempMap[a][b] = WALLID;
                }

            }
        }
        if (depth == 0)
        {
            return tempMap;
        }
        else
        {
            return updateMap(tempMap, depth-1);
        }

    }

    /**
     * Generates a binary cellular automata 2D array
     * @param randomNumberGenerator map seed
     * @return 2D array of bytes
     */
    public byte[][] generateBinaryMapping(Random randomNumberGenerator, int mapX, int mapY, float initalEmptyFaceRate)
    {
        setMapSize(mapX,mapY);
        this.initalEmptyFaceRate = initalEmptyFaceRate;
        binaryMapping = floorFillController(updateMap(startMap(randomNumberGenerator), recursionDepth));
        if (totalEmptySpace < mapX * mapY * initalEmptyFaceRate)
        {
            generateBinaryMapping(randomNumberGenerator, mapX,mapY, initalEmptyFaceRate);
        }
        return binaryMapping;

    }


    /**
     *
     * -- It will remove all small disconnected cave parts and only return the main core cave
     *
     * @param mappingCopy Copy of the 2D cellular automata array
     * @return  a new 2D array of bytes with only one cave
     */
    public byte[][] floorFillController(byte[][] mappingCopy)
    {

        ArrayList<Integer> caveSizes = new ArrayList<>();
        for(int a = 0; a <mapX;a++)
        {
            for(int b =0; b< mapY; b++)
            {
                Integer caveS = floodFll(a,b, (byte)(caveSizes.size() + 2),mappingCopy);
                if (caveS > 0)
                {
                    caveSizes.add(caveS);
                }
            }
        }
        totalEmptySpace = Collections.max(caveSizes);
        Integer caveIndex = caveSizes.indexOf(totalEmptySpace) + 2;

        for (int a = 0; a<mapX;a++)
        {
            for(int b = 0; b<mapY; b++)
            {
                if (mappingCopy[a][b] == caveIndex)
                {
                    mappingCopy[a][b] = EMPTYID;
                }
                else
                {
                    mappingCopy[a][b] = WALLID;
                }
            }
        }
        return mappingCopy;

    }

    /**
     * The flood fill is Queue-Based instead of Recursion based because on large maps, StackOverFlows can occur on the recursive call stack
     * @param x The X position of the location being checked
     * @param y The Y position of the location being checked
     * @param fillID Each distinct cave is given a unique FillID
     * @param mapping   The binary cellular automata mapping being checked
     * @return The Size of the current FillID cave
     */
    public int floodFll(int x, int y, byte fillID, byte[][] mapping)
    {

        Queue<Vector2i> nodeQueue = new ArrayDeque<>();
        int size= 0;
        if (mapping[x][y] == EMPTYID)
        {
            nodeQueue.add(new Vector2i(x,y));
        }

        while(!nodeQueue.isEmpty())
        {
            Vector2i currentNode = nodeQueue.poll();
            try
            {

                if (mapping[currentNode.x][currentNode.y] == EMPTYID)
                {


                    size ++;
                    mapping[currentNode.x][currentNode.y] = fillID;
                    nodeQueue.add(new Vector2i(currentNode.x + 1, currentNode.y));
                    nodeQueue.add(new Vector2i(currentNode.x - 1, currentNode.y));
                    nodeQueue.add(new Vector2i(currentNode.x, currentNode.y + 1));
                    nodeQueue.add(new Vector2i(currentNode.x, currentNode.y - 1));
                }
            }
            catch (Exception e)
            {

            }


        }

        return size;

    }
    
    private void setMapSize(int x, int y)
    {
        this.mapX = x;
        this.mapY =y;
    }
}
