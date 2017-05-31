/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Random;

/* Driver class */
public class rrt extends PApplet {

    private static final int SCREEN_WIDTH           = 720;
    private static final int SCREEN_HEIGHT          = 500;
    private static final int NODE_WIDTH             = 5;
    private static final int NODE_HEIGHT            = 5;
    private static final int SPECIAL_NODE_WIDTH     = 30;
    private static final int SPECIAL_NODE_HEIGHT    = 30;
    private static final float STEP_LENGTH          = 7.0f;  // adjust for length of branches
    private static final float NEIGHBORHOOD_RADIUS  = 30f;
    private static final int NODE_LIMIT             = 5000;  // adjust for max amount of time that algorithm can spend looking for goal node
    private boolean goalNodeFound, locked, resourcesEmpty;
    private RapidRandomTree rapidRandomTree;
    private int counter, obstacleX, obstacleY, obstacleWidth, obstacleHeight;
    private String branch_count, node_error_msg;
    // Processing color constants
    public static int node_color, start_node_color, end_node_color, obstacle_color, edge_color_1, edge_color_2, edge_color_3;

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
        node_error_msg   = "Ran out of branches!";
        node_color       = color(34,139,34);
        start_node_color = color(0,0,255);
        end_node_color   = color(255,0,0);
        edge_color_1     = color(210,105,30);
        edge_color_2     = color(34,139,34);
        edge_color_3     = color(0,100,0);
        obstacle_color   = color(160,82,45);
        rapidRandomTree  = new RapidRandomTree(this);
    }

    public void draw() {
        background(color(0));
        rapidRandomTree.display();
        if(counter > 1 && !resourcesEmpty) {
            update();
            // Displays number of branches currently drawn to screen in upper left hand corner of screen
            textSize(15);
            fill(255,255,0);
            textAlign(CENTER);
            branch_count = "Branch count: " + Integer.toString(rapidRandomTree.getNodeCount());
            text(branch_count,75,25);
        }
        // Displays rectangle user is drawing to screen
        else if(locked && counter <= 1)
            rect(obstacleX,obstacleY,obstacleWidth,obstacleHeight);
        // Displays error message if program runs out of available branches
        else if(resourcesEmpty){
            textSize(50);
            fill(255,255,0);
            textAlign(CENTER);
            text(node_error_msg,SCREEN_WIDTH/2,SCREEN_HEIGHT/2);
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
        obstacleWidth = mouseX - obstacleX;
        obstacleHeight = mouseY - obstacleY;
    }

    // For drawing obstacles
    public void mouseReleased(){
        if(counter <= 1){ // obstacles can only be added before tree begins building
            locked = false;
            rapidRandomTree.addObstacle(new Obstacle(obstacleX,obstacleY,abs(obstacleWidth),abs(obstacleHeight),obstacle_color,this));
            obstacleX = 0; obstacleY = 0; obstacleHeight = 0; obstacleWidth = 0;
        }
    }

    // RRT* algorithm
    private void update(){
        // If we have not found the goal node and still have resources
        if(!goalNodeFound && rapidRandomTree.getNodeCount() < NODE_LIMIT){
            // Pick random location
            TreeNode randomNode = new TreeNode(0,0,NODE_WIDTH,NODE_HEIGHT,node_color,this);
            setRandomConfig(randomNode);
            // Find vertex in tree closest to that random location
            TreeNode nearestNode = rapidRandomTree.getNearestNode(randomNode);
            // Add path if in range of step length and not colliding with an obstacle
            if(dist(randomNode.getxCoord(),randomNode.getyCoord(),nearestNode.getxCoord(),nearestNode.getyCoord()) <= STEP_LENGTH
                    && !rapidRandomTree.intersectingObstacle(randomNode)){
                randomNode.setParentNode(nearestNode);
                rapidRandomTree.addNode(randomNode);
                rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, edge_color_2,1));
            }
            else{
                // Re-position random node
                double theta = Math.atan2(randomNode.getyCoord()-nearestNode.getyCoord(), randomNode.getxCoord()-nearestNode.getxCoord());
                randomNode.setCoordinates(nearestNode.getxCoord() + STEP_LENGTH*(float)Math.cos(theta), nearestNode.getyCoord() + STEP_LENGTH*(float)Math.sin(theta));
                // If new coordinates of random now are not intersecting an obstacle, add node
                if(!rapidRandomTree.intersectingObstacle(randomNode)){
                    randomNode.setParentNode(nearestNode);
                    rapidRandomTree.addNode(randomNode);
                    rapidRandomTree.addEdge(new Edge(nearestNode, randomNode, edge_color_2,1));
                }
            }

            // if goal node found
            if(rapidRandomTree.intersectingGoalNode(randomNode)) {
                rapidRandomTree.getGoalNode().setParentNode(randomNode);
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

    // Gives a node random x and y coordinates
    private void setRandomConfig(TreeNode node){
        Random rand = new Random();
        float randomXCoord = rand.nextInt(rrt.SCREEN_WIDTH) + 1;
        float randomYCoord = rand.nextInt(rrt.SCREEN_HEIGHT) + 1;
        node.setCoordinates(randomXCoord,randomYCoord);
    }

    // Calculates distance between two points on graph (distance formula)
    private float distance(TreeNode x1, TreeNode x2){
        return sqrt(pow(x2.getxCoord()-x1.getxCoord(),2) + pow(x2.getyCoord()-x1.getyCoord(),2));
    }

    // Finds node in a given radius that is closest to the root node
    private TreeNode nearestNode(TreeNode randomNode){
        ArrayList<TreeNode> nodeList = rapidRandomTree.getNodeList();
        TreeNode root = rapidRandomTree.getStartNode();
        TreeNode nearestNode = rapidRandomTree.getNearestNode(randomNode);
        for(TreeNode currentNode : nodeList){
            // make sure node being checked is within radius
            if(distance(currentNode,randomNode) <= NEIGHBORHOOD_RADIUS){
                if(distance(root,currentNode) < distance(root, nearestNode))
                    nearestNode = currentNode;
            }
        }
        return nearestNode;
    }



}
