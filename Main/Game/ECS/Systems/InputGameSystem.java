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
    void MoveMelee(GameObject g, Inputs gameObjectInputs) {
        int rows = 33, cols = 33;
        Vector2f playerPos = Game.PLAYER.getComponent(Position.class).getPosition();
        Vector2f aiPos = g.getComponent(Position.class).getPosition();
        float distanceFromPlayer = vectorToDistance(playerPos, aiPos);
        Vector2i playerLOcal = Blueprint.convertToLocal(playerPos);
        Vector2i gridStart = new Vector2i(playerLOcal.x - rows/2   , playerLOcal.y - cols/2);
        Vector2i start = new Vector2i(Blueprint.convertToLocal(aiPos).x - gridStart.x,Blueprint.convertToLocal(aiPos).y - gridStart.y);
        Vector2i end = new Vector2i(Blueprint.convertToLocal(playerPos).x - gridStart.x,Blueprint.convertToLocal(playerPos).y - gridStart.y );

        AStar aStar = new AStar(rows,cols,start,end,gridStart);
        List<PathingNode> path = aStar.findPath();
        if (path != null) {
            if (path.size() > 1) {
                PathingNode moveTo = path.get(1);
                if (moveTo != null) {
                        if (distanceFromPlayer > 50) {//CHANGE HERE FOR RANGED OR WHAT NOT sfpjhg sddolfjhgsdlfjhgsdlfjhgsdlhjLSDJHFGSDLHFGDSLHJFGSDLHJFGSDLHJFGSDLFHGSDLFHJGSDLFHJSDGLFHJSDGLFHJSDGFLHJSDGFLSDHJGFLSDHJFGL
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

    void MoveRanged(GameObject g,Inputs gameObjectInputs) {
        int rows = 33, cols = 33;
        Vector2f playerPos = Game.PLAYER.getComponent(Position.class).getPosition();
        Vector2f aiPos = g.getComponent(Position.class).getPosition();
        float distanceFromPlayer = vectorToDistance(playerPos, aiPos);
        Vector2i playerLOcal = Blueprint.convertToLocal(playerPos);
        Vector2i gridStart = new Vector2i(playerLOcal.x - rows / 2, playerLOcal.y - cols / 2);
        Vector2i start = new Vector2i(Blueprint.convertToLocal(aiPos).x - gridStart.x, Blueprint.convertToLocal(aiPos).y - gridStart.y);
        Vector2i end = new Vector2i(Blueprint.convertToLocal(playerPos).x - gridStart.x, Blueprint.convertToLocal(playerPos).y - gridStart.y);

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
                    if (distanceFromPlayer > 200) {//CHANGE HERE FOR RANGED OR WHAT NOT sfpjhg sddolfjhgsdlfjhgsdlfjhgsdlhjLSDJHFGSDLHFGDSLHJFGSDLHJFGSDLHJFGSDLFHGSDLFHJGSDLFHJSDGLFHJSDGLFHJSDGFLHJSDGFLSDHJGFLSDHJFGL
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




    private float dotProduct(Vector2f position1, Vector2f position2) {
        // Vector2f sub = Vector2f.sub(position1, position2);
        return (position1.x * position2.x + position1.y * position2.y);
    }
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
    private Vector2f pointToVector(Vector2f position1, Vector2f position2) {
        return Vector2f.sub(position2, position1);
    }

    private int convertVecToMapPos(double pos){
        return (int)(pos / Blueprint.BLOCKSIZE.x);
    }
    private  int getDistance(PathingNode a, PathingNode b) {
        return  Math.abs(b.x - a.x) + Math.abs(b.x- a.x);

    }
    private boolean checkDiagnol(PathingNode a, PathingNode b){
        if (Math.abs(a.x - b.x) == Math.abs(a.y - b.y)){
            return  true;
        }
        return false;
    }

    public class PathingNode {

        private int g;
        //private int f;
        private int h;
        private int x;
        private int y;
        private boolean walkable;
        private PathingNode parent;

        public PathingNode(int x, int y, boolean walkable ) {
            this.walkable = walkable;
            this.x = x;
            this.y = y;
        }

        public boolean checkPath(PathingNode current, int price) {
            int gPrice = current.getG() + price;
            if (gPrice < getG()) {
                parent = current;
                g=gPrice;
                return true;
            }
            return false;
        }

        public int getH() {
            return h;
        }
        public void setH(int h) {
            this.h = h;
        }
        public int getG() {
            return g;
        }
        public void setG(int g) {
            this.g = g;
        }
        public int getF() {
            return h+g;
        }
        public PathingNode getParent() {
            return parent;
        }
        public void setParent(PathingNode parent) {
            this.parent = parent;
        }
        public int getX() {
            return x;
        }
        public void setX(int x) {
            this.x = x;
        }
        public int getY() {
            return y;
        }
        public void setY(int y) {
            this.y = y;
        }
    }

    public class AStar {
        private int flat = 10; //TEMP HIDE FIX OVER HERE PARTIALLY WORKS
        private int diag = 14;
        private PathingNode[][] grid;
        private PriorityQueue<PathingNode> openSet;
        private HashSet<PathingNode> closedSet;
        private PathingNode start;
        private PathingNode end;

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
            //System.out.print("--------------------------------------------------------------------------------------------------------- ");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    PathingNode temp = new PathingNode(i, j, MapManager.getManagerInstance().checkTile8(new Vector2i(gridStart.x + i, gridStart.y + j)));
                    temp.setH(getDistance(temp, end));
                    grid[i][j] = temp;
                }

            }
            grid[pend.x][pend.y].walkable = true;
        }


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
                                p.setG(current.g + (checkDiagnol(p, current) ? diag : flat)); //sMAYBE HEREsdfsdfsdfsdfsfsdfdsfsfsdfsdfsfsdfdsfsdfsfsdfsdfsdfsdfsdfsdfsdsdsdfsdsd
                                p.setParent(current);
                                openSet.add(p);
                                //p.setF();
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
    private interface func<T1, T2, TResult> {
        TResult invoke(T1 t1, T2 t2);
    }
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
        //dx *=3;
        //dy*=3;
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
    private float vectorToDistance(Vector2f position1, Vector2f position2) {
        Vector2f sub = Vector2f.sub(position1, position2);
        return (float) Math.sqrt(sub.x * sub.x + sub.y * sub.y);
    }
}

