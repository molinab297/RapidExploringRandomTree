import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;

import static processing.core.PApplet.sq;
import static processing.core.PApplet.sqrt;

/**
 * Created by Blake on 5/27/17.
 */

public class RapidRandomTree {
    private PApplet parent;
    private ArrayList<TreeNode> nodeList;
    private ArrayList<Edge> edgeList;
    private ArrayList<Obstacle> obstacleList;
    private TreeNode startNode, goalNode;
    private double stepLength;

    public RapidRandomTree(TreeNode startNode, TreeNode goalNode, double stepLength, PApplet parent){
        this.parent = parent;
        nodeList = new ArrayList<>();
        edgeList = new ArrayList<>();
        obstacleList = new ArrayList<>();
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.stepLength = stepLength;
        nodeList.add(startNode);
    }

    public void addNode(TreeNode newNode){
        nodeList.add(newNode);
    }

    public void addEdge(Edge newEdge) { edgeList.add(newEdge); }

    public void addObstacle(Obstacle newObstacle) { obstacleList.add(newObstacle); }

    public TreeNode getRoot(){
        return startNode;
    }

    public TreeNode getgoalNode() {
        return goalNode;
    }

    // Returns nearest node to a pair of vertices (goalNode) on the main screen
    public TreeNode getNearestNode(int xCoord, int yCoord){
        TreeNode nearestNode = startNode;
        float distance = parent.dist(startNode.getxCoord(), startNode.getyCoord(), xCoord, yCoord);
        for(TreeNode currentNode : nodeList){
            float temp = parent.dist(currentNode.getxCoord(), currentNode.getyCoord(), xCoord, yCoord);
            if(temp < distance){
                nearestNode = currentNode;
                distance = temp;
            }
        }
        return nearestNode;
    }

    public TreeNode getRandomConfig(){
        Random rand = new Random();
        int randomXCoord = rand.nextInt(rrt.SCREEN_WIDTH) + 1;
        int randomYCoord = rand.nextInt(rrt.SCREEN_HEIGHT) + 1;
        return new TreeNode(randomXCoord, randomYCoord, rrt.NODE_WIDTH, rrt.NODE_HEIGHT, rrt.node_color, parent);
    }

    // Draws all vertices in the graph to the screen
    private void drawObstacles(){
        for(Obstacle o: obstacleList){
            o.display();
            parent.fill(255, 255, 0);
        }
    }

    // Draws all vertices in the graph to the screen
    private void drawVertices(){
        for(TreeNode n: nodeList){
            n.display();
            parent.fill(255, 255, 0);
        }
    }

    // Draws all edges in the graph to the screen.
    private void drawEdges(){
        for(Edge e: edgeList)
            e.display(parent);
    }

    // Draws the Graph. Only call within the draw() method of rrt.
    public void display() {
        drawEdges();
        drawVertices();
        drawObstacles();
    }

    // Returns true if a vertex 'a' is intersecting with a vertex 'b'
    public boolean intersectingGoalNode(TreeNode a, TreeNode b){
        return (a.getxCoord() >= b.getxCoord() && a.getxCoord() <= b.getxCoord() + b.getWidth()
                && a.getyCoord() >= b.getyCoord() && a.getyCoord() <= b.getyCoord() + b.getHeight());
    }

    // Returns true if a vertex 'a' is intersecting with an obstacle object
    public boolean intersectingObstacle(TreeNode a){
        for(Obstacle o : obstacleList) {
            return (a.getxCoord() >= o.getxCoord() && a.getxCoord() <= o.getxCoord() + o.getWidth()
                    && a.getyCoord() >= o.getyCoord() && a.getyCoord() <= o.getyCoord() + o.getHeight());
        }
    }

}

