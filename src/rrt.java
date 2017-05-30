/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;

public class rrt extends PApplet {

    public static final int SCREEN_WIDTH = 1200, SCREEN_HEIGHT = 800;
    public static final int NODE_WIDTH = 10, NODE_HEIGHT = 10, SPECIAL_NODE_WIDTH = 30, SPECIAL_NODE_HEIGHT = 30;
    public static final float STEP_LENGTH = 20f;  // adjust for length of branches
    public static final int NODE_LIMIT = 2000;    // adjust for max amount of time that algorithm can spend looking for goal node
    public static int node_color, start_node_color, end_node_color, edge_color, obstacle_color;
    private boolean goalNodeFound,locked, resourcesEmpty;
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
        resourcesEmpty   = false;
        node_color       = color(34,139,34);
        start_node_color = color(0,0,255);
        end_node_color   = color(255,0,0);
        edge_color       = color(0,255,0);
        obstacle_color   = color(160,82,45);
        rapidRandomTree  = new RapidRandomTree(this);
    }

    public void draw() {
        background(color(0));
        if(counter > 1)
            update();
        rapidRandomTree.display();
        if(locked)
            rect(obstacleX,obstacleY,obstacleWidth,obstacleHeight);
        if(resourcesEmpty){
            textSize(50);
            fill(255,255,0);
            textAlign(CENTER);
            text("Ran out of nodes!",SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
        }
    }

    public void mousePressed(){
        // Right click to add start and end nodes
        if(mouseButton == RIGHT){
            if(counter ==  0)
                rapidRandomTree.addStartNode(new TreeNode(mouseX,mouseY,SPECIAL_NODE_WIDTH, SPECIAL_NODE_HEIGHT,start_node_color, this));
            else if(counter == 1)
                rapidRandomTree.addGoalNode(new TreeNode(mouseX,mouseY,SPECIAL_NODE_WIDTH+5, SPECIAL_NODE_HEIGHT+5,end_node_color, this));
            counter++;
        }
        // Left click and drag to draw an obstacle
        else if(mouseButton == LEFT){
            locked = true;
            obstacleX = mouseX;
            obstacleY = mouseY;
        }
    }

    // For drawing obstacles
    public void mouseDragged(){
        if(locked){
            obstacleWidth = mouseX - obstacleX;
            obstacleHeight = mouseY - obstacleY;
        }
    }

    // For drawing obstacles
    public void mouseReleased(){
        locked = false;
        rapidRandomTree.addObstacle(new Obstacle(obstacleX,obstacleY,abs(obstacleWidth),abs(obstacleHeight),obstacle_color,this));
        obstacleX = 0; obstacleY = 0; obstacleHeight = 0; obstacleWidth = 0;
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
                    rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, edge_color));
                }
                else{
                    // Re-position random node
                    double theta = Math.atan2(randomNode.getyCoord()-nearestNode.getyCoord(), randomNode.getxCoord()-nearestNode.getxCoord());
                    randomNode.setCoordinates(nearestNode.getxCoord() + STEP_LENGTH*(float)Math.cos(theta), nearestNode.getyCoord() + STEP_LENGTH*(float)Math.sin(theta));
                    // If new coordinates of random now are not intersecting an obstacle, add node
                    if(!rapidRandomTree.intersectingObstacle(randomNode)){
                        randomNode.setParentNode(nearestNode);
                        rapidRandomTree.addNode(randomNode);
                        rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, edge_color));
                    }
                }

                // if goal node found
                if(rapidRandomTree.intersectingGoalNode(randomNode)) {
                    goalNodeFound = true;
                }
            }
            // Goal node found, display path (in blue) back to root node
            else if(goalNodeFound)
                rapidRandomTree.traceBackToRoot();
            // Ran out of resources. Process will be aborted
            else
                resourcesEmpty = true;

    }

}
