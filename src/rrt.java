/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;

public class rrt extends PApplet {

    public static final int SCREEN_WIDTH = 1200, SCREEN_HEIGHT = 750;
    public static final int NODE_WIDTH = 1, NODE_HEIGHT = 1, SPECIAL_NODE_WIDTH = 30, SPECIAL_NODE_HEIGHT = 30;
    public static final float STEP_LENGTH = 20f; // adjust for length of branches
    public static final int NODE_LIMIT = 250;    // adjust for max amount of time that algorithm can spend looking for goal node
    public static int node_color;
    private boolean goalNodeFound,locked;
    private RapidRandomTree rapidRandomTree;
    private int counter;
    private int obstacleX, obstacleY, obstacleWidth, obstacleHeight;

    public static void main(String[] args) {
        PApplet.main("rrt");
    }

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setup(){
        counter          = 0;
        goalNodeFound    = false;
        locked           = false;
        node_color       = color(255,0,0);
        rapidRandomTree  = new RapidRandomTree(this);
    }

    public void draw() {
        background(0, 0, 20);
        if(counter > 1)
            update();
        rapidRandomTree.display();
        if(locked)
            rect(obstacleX,obstacleY,obstacleWidth,obstacleHeight);
    }

    public void mousePressed(){
        if(mouseButton == RIGHT){
            if(counter ==  0)
                rapidRandomTree.addStartNode(new TreeNode(mouseX,mouseY,SPECIAL_NODE_WIDTH, SPECIAL_NODE_HEIGHT,color(50,205,50), this));
            else if(counter == 1)
                rapidRandomTree.addGoalNode(new TreeNode(mouseX,mouseY,SPECIAL_NODE_WIDTH, SPECIAL_NODE_HEIGHT,color(128,0,128), this));
            counter++;
        }
        else if(mouseButton == LEFT){
            locked = true;
            obstacleX = mouseX;
            obstacleY = mouseY;
        }
    }

    public void mouseDragged(){
        if(locked){
            obstacleWidth = mouseX - obstacleX;
            obstacleHeight = mouseY - obstacleY;
        }
    }

    public void mouseReleased(){
        locked = false;
        rapidRandomTree.addObstacle(new Obstacle(obstacleX,obstacleY,abs(obstacleWidth),abs(obstacleHeight),this.color(160,82,45),this));
        obstacleX = 0;
        obstacleY = 0;
        obstacleHeight = 0;
        obstacleWidth = 0;
    }

    // RRT algorithm
    private void update(){

            // If we have not found the goal node and still have resources
            if(!goalNodeFound && rapidRandomTree.getNodeCount() < NODE_LIMIT){
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
                    double theta = Math.atan2(randomNode.getyCoord()-nearestNode.getyCoord(), randomNode.getxCoord()-nearestNode.getxCoord());
                    randomNode.setCoordinates(nearestNode.getxCoord() + STEP_LENGTH*(float)Math.cos(theta), nearestNode.getyCoord() + STEP_LENGTH*(float)Math.sin(theta));
                    // If new coordinates of random now are not intersecting an obstacle, add node
                    if(!rapidRandomTree.intersectingObstacle(randomNode)){
                        randomNode.setParentNode(nearestNode);
                        rapidRandomTree.addNode(randomNode);
                        rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, color(255,0,0)));
                    }
                }

                // if goal node found
                if(rapidRandomTree.intersectingGoalNode(randomNode, rapidRandomTree.getgoalNode()))
                    goalNodeFound = true;
            }
            // Goal node found, display path (in blue) back to root node
            else if(goalNodeFound)
                rapidRandomTree.traceBackToRoot();
            // Ran out of resources. Process will be aborted
            else{
                textSize(50);
                fill(255,255,0);
                textAlign(CENTER);
                text("Ran out of nodes!",SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
            }
    }

}
