import processing.core.PApplet;

public class rrt extends PApplet {

    public static final int SCREEN_WIDTH   = 1200;
    public static final int SCREEN_HEIGHT  = 750;
    public static final int WHITE = 255, GRAY = 167, BLACK = 0;
    public static final int NODE_WIDTH = 5, NODE_HEIGHT = 5, SPECIAL_NODE_WIDTH = 30, SPECIAL_NODE_HEIGHT = 30;
    public static final float STEP_LENGTH = .10f;
    public static int node_color;
    private boolean goalNodeFound = false;

    RapidRandomTree rapidRandomTree;

    public static void main(String[] args) {PApplet.main("rrt");}

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setup(){
        node_color = color(255,0,0);

        // Add initial configurations to RR Tree
        TreeNode startNode = new TreeNode(35,35,SPECIAL_NODE_WIDTH, SPECIAL_NODE_HEIGHT,color(50,205,50), this);
        TreeNode goalNode  = new TreeNode(1000,600,SPECIAL_NODE_WIDTH, SPECIAL_NODE_HEIGHT,color(128,0,128), this);
        rapidRandomTree    = new RapidRandomTree(startNode, goalNode, STEP_LENGTH, this);
    }

    public void draw(){
        background(0,0,20);
        rapidRandomTree.display();
        update();
    }

    public void mouseClicked(){

    }

    // RRT algorithm
    private void update(){

            if(!goalNodeFound){
                // Pick random location
                TreeNode randomNode = rapidRandomTree.getRandomConfig();
                // Find vertex in tree closest to that random location
                TreeNode nearestNode = rapidRandomTree.getNearestNode(randomNode.getxCoord(), randomNode.getyCoord());
                // Add path if in range of step length and not colliding with an obstacle
                if(dist(randomNode.getxCoord(),randomNode.getyCoord(),nearestNode.getxCoord(),nearestNode.getyCoord()) <= STEP_LENGTH
                        && !rapidRandomTree.intersectingObstacle(randomNode)){
                    randomNode.setParentNode(nearestNode);
                    rapidRandomTree.addNode(randomNode);
                    rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, color(255,0,0)));
                }
                else{
                    // Re-position random node
                    randomNode.setCoordinates((nearestNode.getxCoord()+randomNode.getxCoord())/2,
                            (nearestNode.getyCoord()+randomNode.getyCoord())/2);
                    // If new coordinates of random now are not intersecting an obstacle, add node
                    if(!rapidRandomTree.intersectingObstacle(randomNode)){
                        randomNode.setParentNode(nearestNode);
                        rapidRandomTree.addNode(randomNode);
                        rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, color(255,0,0)));
                    }
                }

                if(rapidRandomTree.intersectingGoalNode(randomNode, rapidRandomTree.getgoalNode()))
                    goalNodeFound = true;
            }
            else{
                rapidRandomTree.traceBack();
            }

    }



}
