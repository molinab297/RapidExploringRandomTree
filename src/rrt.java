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

    public static final int SCREEN_WIDTH = 1000, SCREEN_HEIGHT = 700;
    public static final int NODE_WIDTH = 10, NODE_HEIGHT = 10, SPECIAL_NODE_WIDTH = 30, SPECIAL_NODE_HEIGHT = 30;
    public static final float STEP_LENGTH = 30f;  // adjust for length of branches
    public static final int NODE_LIMIT = 3000;    // adjust for max amount of time that algorithm can spend looking for goal node
    public static int node_color, start_node_color, end_node_color, obstacle_color;
    public static int edge_color_1, edge_color_2, edge_color_3;
    private boolean goalNodeFound,locked, resourcesEmpty;
    private RapidRandomTree rapidRandomTree;
    private int counter;
    private int obstacleX, obstacleY, obstacleWidth, obstacleHeight;
    private String branch_count, node_error_msg;

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
        rapidRandomTree.display();
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

    // RRT algorithm
    private void update(){

        // If we have not found the goal node and still have resources
        if(!goalNodeFound && rapidRandomTree.getNodeCount() < NODE_LIMIT){

            TreeNode rand   = new TreeNode(0, 0, rrt.NODE_WIDTH, rrt.NODE_HEIGHT, rrt.node_color, this);
            TreeNode rand2  = new TreeNode(0, 0, rrt.NODE_WIDTH, rrt.NODE_HEIGHT, rrt.node_color, this);
            TreeNode rand3  = new TreeNode(0, 0, rrt.NODE_WIDTH, rrt.NODE_HEIGHT, rrt.node_color, this);
            TreeNode parentNode = rapidRandomTree.getStartNode();
            boolean foundNext = false;
            while(!foundNext){
                setRandomConfig(rand);
                setRandomConfig(rand2);
                setRandomConfig(rand3);
                parentNode = rapidRandomTree.getStartNode();
                ArrayList<TreeNode> nodeList = rapidRandomTree.getNodeList();
                for(TreeNode p : nodeList){
                    float d1 = dist(p.getxCoord(),p.getyCoord(),rand.getxCoord(),rand.getyCoord());
                    float d2 = dist(p.getxCoord(),p.getyCoord(),rand2.getxCoord(),rand2.getyCoord());
                    float d3 = dist(p.getxCoord(),p.getyCoord(),rand3.getxCoord(),rand3.getyCoord());

                    // evaluate distance costs
                    if(d1 <= dist(parentNode.getxCoord(),parentNode.getyCoord(),rand.getxCoord(),rand.getyCoord())
                       && d2 <= dist(parentNode.getxCoord() ,parentNode.getyCoord(),rand2.getxCoord(),rand2.getyCoord())
                       && d3 <= dist(parentNode.getxCoord() ,parentNode.getyCoord(),rand3.getxCoord(),rand3.getyCoord())){
                        if(!rapidRandomTree.intersectingObstacle(rand) && !rapidRandomTree.intersectingObstacle(rand2)
                                && !rapidRandomTree.intersectingObstacle(rand3)){
                            parentNode = p;
                            foundNext = true;
                        }
                    }
                }
            }

            // Re-position random nodes
            setNewConfig(parentNode, rand);
            setNewConfig(parentNode, rand2);
            setNewConfig(parentNode, rand3);

            // If new coordinates of random now are not intersecting an obstacle, add node
            if(!rapidRandomTree.intersectingObstacle(rand)) {
                rapidRandomTree.addNode(rand, parentNode);
                rapidRandomTree.addEdge(new Edge(rand, parentNode, edge_color_1,1));
            }

            if(!rapidRandomTree.intersectingObstacle(rand2)){
                rapidRandomTree.addNode(rand2, parentNode);
                rapidRandomTree.addEdge(new Edge(rand2, parentNode, edge_color_2,1));
            }

            if(!rapidRandomTree.intersectingObstacle(rand3)){
                rapidRandomTree.addNode(rand3, parentNode);
                rapidRandomTree.addEdge(new Edge(rand3, parentNode, edge_color_3,1));
            }

            // if goal node found
            if(rapidRandomTree.intersectingGoalNode(rand) || rapidRandomTree.intersectingGoalNode(rand2)
                    || rapidRandomTree.intersectingGoalNode(rand3)) {
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

    //
    public void setRandomConfig(TreeNode node){
        Random rand = new Random();
        float randomXCoord = rand.nextInt(rrt.SCREEN_WIDTH) + 1;
        float randomYCoord = rand.nextInt(rrt.SCREEN_HEIGHT) + 1;
        node.setCoordinates(randomXCoord,randomYCoord);
    }

    public void setNewConfig(TreeNode parentNode, TreeNode randomNode){
        double theta = Math.atan2(randomNode.getyCoord()-parentNode.getyCoord(), randomNode.getxCoord()-parentNode.getxCoord());
        randomNode.setCoordinates(parentNode.getxCoord() + STEP_LENGTH*(float)Math.cos(theta), parentNode.getyCoord() + STEP_LENGTH*(float)Math.sin(theta));
    }

}
