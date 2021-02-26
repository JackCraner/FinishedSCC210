package Main.Game.ECS.Systems;

import Main.Game.ECS.Components.ComponentENUMs.InputTypes;
import Main.Game.ECS.Components.SpecialComponents.Backpack;
import Main.Game.ECS.Components.StandardComponents.Animation;
import Main.Game.ECS.Components.StandardComponents.Inputs;
import Main.Game.ECS.Components.StandardComponents.Position;
import Main.Game.ECS.Components.StandardComponents.TransformComponent;
import Main.Game.ECS.Entity.Camera;
import Main.Game.ECS.Entity.GameObject;
import Main.Game.ECS.Factory.BitMasks;
import Main.Game.ECS.Factory.Blueprint;
import Main.Game.ECS.Factory.Entity;
import Main.Game.GUI.GUIComponents.GUIInventory;
import Main.Game.Game;
import Main.Game.Managers.GUIManager;
import Main.Game.Managers.MapManager;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Mouse;
import org.jsfml.system.Vector2f;
import Main.Game.Managers.EntityManager;
import Main.Game.MapGeneration.Map;
import Main.Game.Managers.MapManager;
import java.lang.System;

import javax.swing.table.TableRowSorter;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class InputGameSystem extends GameSystem {
    private static InputGameSystem systemInstance = new InputGameSystem();

    public static InputGameSystem getSystemInstance() {
        return systemInstance;
    }

    private InputGameSystem() {
        setBitMaskRequirement(BitMasks.getBitMask(Inputs.class));
    }

    /**
     * Method for updating all gameobjects with input component
     * @param dt Time interval since last frame
     */
    @Override
    public void update(float dt) {
        for (GameObject g : getGameObjectList()) {
            Inputs gameObjectInputs = g.getComponent(Inputs.class);
            gameObjectInputs.backwards = false;
            gameObjectInputs.drop = false;
            gameObjectInputs.forward = false;
            gameObjectInputs.left = false;
            gameObjectInputs.pickUP = false;
            gameObjectInputs.use = false;
            gameObjectInputs.right = false;

            if (gameObjectInputs.inputType == InputTypes.HUMAN) {
                if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
                    gameObjectInputs.forward = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
                    gameObjectInputs.left = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
                    gameObjectInputs.backwards = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
                    gameObjectInputs.right = true;
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.E)) {
                    gameObjectInputs.drop = true;
                }
                if (Mouse.isButtonPressed(Mouse.Button.RIGHT)) {
                    gameObjectInputs.use = true;
                }
                if (Mouse.isButtonPressed(Mouse.Button.LEFT)) {
                    gameObjectInputs.pickUP = true;
                }
                if (BitMasks.checkIfContains(g.getBitmask(), Position.class) && BitMasks.checkIfContains(g.getBitmask(), TransformComponent.class)) {
                    Vector2f pos = g.getComponent(Position.class).getPosition();
                    Vector2i mousePos = Mouse.getPosition(Game.getGame().getWindow());
                    Vector2f camPos = new Vector2f(Camera.cameraInstance().camerView.getCenter().x - (Camera.cameraInstance().camerView.getSize().x / 2), Camera.cameraInstance().camerView.getCenter().y - (Camera.cameraInstance().camerView.getSize().y / 2));
                    //find angle
                    mousePos = new Vector2i((int) camPos.x + mousePos.x, (int) camPos.y + mousePos.y);
                    float angle = (float) ((Math.atan2(mousePos.y - pos.y, mousePos.x - pos.x)) * 180 / Math.PI);
                    g.getComponent(TransformComponent.class).setDirection(angle);


                }

                if (BitMasks.checkIfContains(g.getBitmask(), Backpack.class)) {
                    Backpack b = g.getComponent(Backpack.class);
                    if (Keyboard.isKeyPressed(Keyboard.Key.NUM1)) {
                        b.setCurrentBackpackPosition(0);
                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.NUM2)) {
                        b.setCurrentBackpackPosition(1);
                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.NUM3)) {
                        b.setCurrentBackpackPosition(2);
                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.NUM4)) {
                        b.setCurrentBackpackPosition(3);
                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.NUM5)) {
                        b.setCurrentBackpackPosition(4);
                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                    }
                    if (Keyboard.isKeyPressed(Keyboard.Key.NUM6)) {
                        b.setCurrentBackpackPosition(5);
                        GUIManager.getGUIinstance().GUIUpdate(GUIInventory.class);
                    }
                }
            }
                    if (gameObjectInputs.inputType == InputTypes.AIMELEE) {
                        MoveMelee(g, gameObjectInputs);
                    } else if (gameObjectInputs.inputType == InputTypes.AIRANGED) {
                        MoveRanged(g, gameObjectInputs);
                    } else {
                        MoveBoss(g, gameObjectInputs);
                    }

                }
            }

    /**
     *  Moves melee enemies towards player using astar algorithm. Enemies attack once in certain range to player
     * @param g Gameobject to move
     * @param gameObjectInputs The inputs for Gameobject to move
     */
    void MoveMelee(GameObject g, Inputs gameObjectInputs) {
        //sets rows and columns of grid mapping
        int rows = 33, cols = 33;
        Vector2f playerPos = Game.PLAYER.getComponent(Position.class).getPosition();
        Vector2f aiPos = g.getComponent(Position.class).getPosition();
        float distanceFromPlayer = vectorToDistance(playerPos, aiPos);
        //mapping enemy and ai to grid position
        Vector2i playerLOcal = Blueprint.convertToLocal(playerPos);
        Vector2i gridStart = new Vector2i(playerLOcal.x - rows/2   , playerLOcal.y - cols/2);
        Vector2i start = new Vector2i(Blueprint.convertToLocal(aiPos).x - gridStart.x,Blueprint.convertToLocal(aiPos).y - gridStart.y);
        Vector2i end = new Vector2i(Blueprint.convertToLocal(playerPos).x - gridStart.x,Blueprint.convertToLocal(playerPos).y - gridStart.y );
        //calculate fastest path and set enemy movement
        AStar aStar = new AStar(rows,cols,start,end,gridStart);
        List<PathingNode> path = aStar.findPath();
        if (path != null) {
            if (path.size() > 1) {
                PathingNode moveTo = path.get(1);
                if (moveTo != null) {
                        if (distanceFromPlayer > 50) {
                            if (moveTo.getX() > start.x) {
                                gameObjectInputs.right = true;
                            }
                            if (moveTo.getX() < start.x) {
                                gameObjectInputs.left = true;
                            }
                            if (moveTo.getY() < start.y) {
                                gameObjectInputs.forward = true;
                            }
                            if (moveTo.getY() > start.y) {
                                gameObjectInputs.backwards = true;
                            }
                        } else {
                            gameObjectInputs.use = true;
                        }
                    }
                }
            }
        if (BitMasks.checkIfContains(g.getBitmask(), Position.class) && BitMasks.checkIfContains(g.getBitmask(), TransformComponent.class)) {
            Vector2f pos = g.getComponent(Position.class).getPosition();
            //find angle
            float angle = (float) ((Math.atan2(playerPos.y - pos.y, playerPos.x - pos.x)) * 180 / Math.PI);
            g.getComponent(TransformComponent.class).setDirection(angle);
        }
        }

    /**
     * Moves ranged enemies towards player using astar algorithm. Uses raycast to prevent enemies from firing behind walls, enemies will fire when in range
     * @param g Gameobject to move
     * @param gameObjectInputs The inputs for Gameobject to move
     */
    void MoveRanged(GameObject g,Inputs gameObjectInputs) {
        //sets rows and columns of grid mapping
        int rows = 33, cols = 33;
        Vector2f playerPos = Game.PLAYER.getComponent(Position.class).getPosition();
        Vector2f aiPos = g.getComponent(Position.class).getPosition();
        float distanceFromPlayer = vectorToDistance(playerPos, aiPos);
        //mapping enemy and ai to grid position
        Vector2i playerLOcal = Blueprint.convertToLocal(playerPos);
        Vector2i gridStart = new Vector2i(playerLOcal.x - rows / 2, playerLOcal.y - cols / 2);
        Vector2i start = new Vector2i(Blueprint.convertToLocal(aiPos).x - gridStart.x, Blueprint.convertToLocal(aiPos).y - gridStart.y);
        Vector2i end = new Vector2i(Blueprint.convertToLocal(playerPos).x - gridStart.x, Blueprint.convertToLocal(playerPos).y - gridStart.y);
        //calculate fastest path and set enemy movement
        AStar aStar = new AStar(rows, cols, start, end, gridStart);
        List<PathingNode> path = aStar.findPath();
        if (path != null) {
            if (path.size() > 1) {
                PathingNode moveTo = path.get(1);
                if (moveTo != null) {
                    boolean attacking = raycasting(aiPos, playerPos, (x, y) ->
                    {
                        return isBlockAtPos(new Vector2f(x, y));
                    });
                    if (distanceFromPlayer > 200) {
                        if (moveTo.getX() > start.x) {
                            gameObjectInputs.right = true;
                        }
                        if (moveTo.getX() < start.x) {
                            gameObjectInputs.left = true;
                        }
                        if (moveTo.getY() < start.y) {
                            gameObjectInputs.forward = true;
                        }
                        if (moveTo.getY() > start.y) {
                            gameObjectInputs.backwards = true;
                        }
                    } else {
                        gameObjectInputs.use = true;
                    }
                }
            }
        }
        if (BitMasks.checkIfContains(g.getBitmask(), Position.class) && BitMasks.checkIfContains(g.getBitmask(), TransformComponent.class)) {
            Vector2f pos = g.getComponent(Position.class).getPosition();
            //find angle
            float angle = (float) ((Math.atan2(playerPos.y - pos.y, playerPos.x - pos.x)) * 180 / Math.PI);
            g.getComponent(TransformComponent.class).setDirection(angle);
        }
    }

    /**
     * Moves boss enemy type directly to player
     * @param g The game object to move
     * @param gameObjectInputs The inputs for gameobject
     */
    void MoveBoss(GameObject g,Inputs gameObjectInputs) {
        int rows = 33, cols = 33;
        Vector2f playerPos = Game.PLAYER.getComponent(Position.class).getPosition();
        Vector2f aiPos = g.getComponent(Position.class).getPosition();
        float distanceFromPlayer = vectorToDistance(playerPos, aiPos);
        if (distanceFromPlayer > 20)
        {
            if (playerPos.x > aiPos.x) {
                gameObjectInputs.right = true;
            }
        }
        if (playerPos.x < aiPos.x) {
            gameObjectInputs.left = true;
        }
        if (playerPos.y < aiPos.y) {
            gameObjectInputs.forward = true;
        }
        if (playerPos.y > aiPos.y) {
            gameObjectInputs.backwards = true;
        }
    }

    /**
     * Dot product between two vectors
     * @param position1 Vector2f of position1
     * @param position2 Vector2f of position2
     * @return returns Vector2f dot product of position1 and position2
     */
    private float dotProduct(Vector2f position1, Vector2f position2) {
        return (position1.x * position2.x + position1.y * position2.y);
    }

    /**
     * Checks if block as pos
     * @param point Vector2f point to check
     * @return Returns true or false if block at point
     */
    private boolean isBlockAtPos(Vector2f point) {
        ArrayList<GameObject> hello = EntityManager.getEntityManagerInstance().getGameObjectInVicinity(point, 0);
        for (GameObject g : hello) {
            if (g.getSID() instanceof Map.Block) {
                Vector2f L = g.getComponent(Position.class).getPosition();
                Vector2f M = new Vector2f(L.x + 32, L.y);
                Vector2f N = new Vector2f(L.x, L.y + 32);
                float LMLM = dotProduct(pointToVector(L, M), pointToVector(L, M));
                float MNMN = dotProduct(pointToVector(M, N), pointToVector(M, N));
                float LMLP = dotProduct(pointToVector(L, M), pointToVector(L, point));
                float MNMP = dotProduct(pointToVector(M, N), pointToVector(M, point));

                if (0 < LMLP && LMLP < LMLM && 0 < MNMP && MNMP < MNMN) {
                    //System.out.println("True");
                    return true;
                }
            }


        }
        return false;
    }

    /**
     * Converts points to a vector
     * @param position1 Vector2f of position1
     * @param position2 Vector2f of position2
     * @return returns Vector2f giving vector between position1 and position2
     */
    private Vector2f pointToVector(Vector2f position1, Vector2f position2) {
        return Vector2f.sub(position2, position1);
    }

    /**
     * Gets distance between two pathing nodes for astar
     * @param a 1st pathing node
     * @param b 2nd pathing node
     * @return returns int distance between PathingNode a and b
     */
    private  int getDistance(PathingNode a, PathingNode b) {
        return  Math.abs(b.x - a.x) + Math.abs(b.x- a.x);
    }

    /**
     * Checks whether PathingNode b is at a diagnol to PathingNOde a
     * @param a 1st Pathing Node
     * @param b 2nd Pathing Node
     * @return returns true if PathingNode b is at a diagnol to PathingNode a
     */
    private boolean checkDiagnol(PathingNode a, PathingNode b){
        if (Math.abs(a.x - b.x) == Math.abs(a.y - b.y)){
            return  true;
        }
        return false;
    }


    /**
     * Class used for AStar algorithm
     *
     */
    public class PathingNode {
        private int g;
        //private int f;
        private int h;
        private int x;
        private int y;
        private boolean walkable;
        private PathingNode parent;

        /**
         * Contructor
         * @param x int x position of PathingNode
         * @param y int y position of PathingNode
         * @param walkable boolean of whether node is walkable or not
         */
        public PathingNode(int x, int y, boolean walkable ) {
            this.walkable = walkable;
            this.x = x;
            this.y = y;
        }

        /**
         * Checks if better path
         * @param current PathingNode to check
         * @param price Current price of path
         * @return returns true/false if path is better/worse
         */
        public boolean checkPath(PathingNode current, int price) {
            int gPrice = current.getG() + price;
            if (gPrice < getG()) {
                parent = current;
                g=gPrice;
                return true;
            }
            return false;
        }

        /**
         * Sets heuristic for PathingNode
         * @param h int h to set
         */
        public void setH(int h) {
            this.h = h;
        }

        /**
         * Gets  cost of path to start node
         * @return int g cost to start node
         */
        public int getG() {
            return g;
        }

        /**
         * Sets cost of path to start node
         * @param g int cost of path to start node
         */
        public void setG(int g) {
            this.g = g;
        }

        /**
         * f = g + h
         * @return returns  int f of node
         */
        public int getF() {
            return h+g;
        }

        /**
         * Gets parent of node
         * @return returns PathingNode parent
         */
        public PathingNode getParent() {
            return parent;
        }

        /**
         * Sets parent of node
         * @param parent PathingNode parent of node
         */
        public void setParent(PathingNode parent) {
            this.parent = parent;
        }

        /**
         * gets x value of node
         * @return int x value of node
         */
        public int getX() {
            return x;
        }

        /**
         * sets x value of node
         * @param x int x value of node
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * gets y value of node
         * @return int y value of node
         */
        public int getY() {
            return y;
        }

        /**
         * sets y value of node
         * @param y int y value of node
         */
        public void setY(int y) {
            this.y = y;
        }
    }

    /**
     * Class used for AStar algorithm
     *
     */
    public class AStar {
        //Cost of horizontal movement, and diagnol movement
        private int flat = 10;
        private int diag = 14;
        private PathingNode[][] grid;
        private PriorityQueue<PathingNode> openSet;
        private HashSet<PathingNode> closedSet;
        private PathingNode start;
        private PathingNode end;

        /**
         * Contructor for AStar class
         * @param rows int number of rows
         * @param cols int number of cols
         * @param pstart Vector2i local position of starting position
         * @param pend Vector2i local position of ending position
         * @param gridStart Vector2i local position of grid start
         */
        public AStar(int rows, int cols, Vector2i pstart, Vector2i pend,Vector2i gridStart) {
            start = new PathingNode(pstart.x, pstart.y, true);
            end =new PathingNode(pend.x, pend.y, true);
            closedSet = new HashSet<>();
            openSet = new PriorityQueue<PathingNode>(new Comparator<PathingNode>() {
                @Override
                public int compare(PathingNode a, PathingNode b) {
                    return Integer.compare(a.getF(), b.getF());
                }
            });

            grid = new PathingNode[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    PathingNode temp = new PathingNode(i, j, MapManager.getManagerInstance().checkTile8(new Vector2i(gridStart.x + i, gridStart.y + j)));
                    temp.setH(getDistance(temp, end));
                    grid[i][j] = temp;
                }

            }
            grid[pend.x][pend.y].walkable = true;
        }

        /**
         * Finds path that minimizes f = g + h
         * @return returns List of PathingNOde containing path that minimizes f = g + h
         */
        public List<PathingNode> findPath() {
            openSet.add(start);
            while (!openSet.isEmpty()) {
                PathingNode current = openSet.poll();
                closedSet.add(current);
                if (current.x == end.x && current.y == end.y ) {
                    return retracePath(current);
                } else {
                    List<PathingNode> neighbours = new ArrayList<PathingNode>();
                    for (int x = -1; x <= 1; x++)
                        for (int y = -1; y <= 1; y++) {
                            if (x == 0 && y == 0) continue;

                            int checkX = current.x + x;
                            int checkY = current.y + y;

                            if (checkX >= 0 && checkX < grid.length && checkY >= 0 && checkY < grid[0].length)
                                neighbours.add(grid[checkX][checkY]);
                        }
                    for (PathingNode p : neighbours) {
                        if (p.walkable && !closedSet.contains(p)) {
                            if (!openSet.contains(p)) {
                                p.setG(current.g + (checkDiagnol(p, current) ? diag : flat));
                                p.setParent(current);
                                openSet.add(p);
                            } else {
                                boolean changed = p.checkPath(current, (checkDiagnol(p, current) ? diag : flat));
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    /**
     * Retraces path of a PathingNode
     * @param current PathingNode to retrace path from
     * @return List of PathingNode
     */
    private List<PathingNode> retracePath(PathingNode current) {
        List<PathingNode> path = new ArrayList<PathingNode>();
        path.add(current);
        PathingNode parent;
        while ((parent = current.getParent()) != null) {
            path.add(0, parent);
            current = parent;
        }
        return path;
    }

    /**
     * Interface for raycast
     * @param <T1>
     * @param <T2>
     * @param <TResult>
     */
    private interface func<T1, T2, TResult> {
        TResult invoke(T1 t1, T2 t2);
    }

    /**
     * Traces ray between two Vector2f positions
     * @param position1 Vector2f 1st position
     * @param position2 Vector2f 2nd position
     * @param endloop  Boolean end
     * @return returns true/false if raytrace is connected/not connected
     */
    private boolean raycasting(Vector2f position1, Vector2f position2, func<Integer, Integer, Boolean> endloop) {
        //COME BACK TO THIS unknown bug
        int dx = (int) Math.abs(position2.x - position1.x);
        int dy = (int) Math.abs(position2.y - position1.y);

        int x = (int) position1.x;
        int y = (int) position1.y;

        int n = 1 + dx + dy;

        int x_inc = (position2.x > position1.x) ? 1 : -1;
        int y_inc = (position2.y > position1.y) ? 1 : -1;
        int error = dx - dy;
        boolean done = false;
        while (n > 0 && !done) {
            if (error >0){
                error-=dy;
                x+=x_inc;

            } else {
                error+=dx;
                y+=y_inc;

            }
            done = endloop.invoke(x, y);
            n --;

        }
        if (done) {
            // System.out.println("NOT FOUND");
            return false;
        } else {
            // System.out.println("FOUND");
            return true;
        }
    }

    /**
     * converts two positions into a distance
     * @param position1 Vector2f 1st position
     * @param position2 Vector2f 2nd position
     * @return returns float distance between 1st position and 2nd position
     */
    private float vectorToDistance(Vector2f position1, Vector2f position2) {
        Vector2f sub = Vector2f.sub(position1, position2);
        return (float) Math.sqrt(sub.x * sub.x + sub.y * sub.y);
    }
}

