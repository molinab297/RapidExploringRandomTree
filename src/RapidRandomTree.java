/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;
import java.util.Random;
import java.util.ArrayList;

/********************************************************************
 *  CLASS RapidRandomTree
 *
 *  OVERVIEW: A data structure that is designed to efficiently search
 *  nonconvex, high-dimensional spaces by randomly building a space-filling
 *  tree. The tree is constructed incrementally from samples drawn
 *  randomly from the search space and is inherently biased to grow
 *  towards large unsearched areas of the problem.
 *
 *  CONSTRUCTOR PARAMETERS:
 *      PApplet parent     : Reference to Processing parent object
 *                           (used for drawing to things to screen)
 ********************************************************************/
public class RapidRandomTree {
    private PApplet parent;
    private ArrayList<TreeNode> nodeList;
    private ArrayList<Edge> edgeList;
    private ArrayList<Obstacle> obstacleList;
    private TreeNode startNode, goalNode;
    private int nodeCount;

    public RapidRandomTree(PApplet parent){
        this.nodeCount = 0;
        this.parent = parent;
        nodeList = new ArrayList<>();
        edgeList = new ArrayList<>();
        obstacleList = new ArrayList<>();
    }

    public void addNode(TreeNode newNode){
        nodeList.add(newNode);
        nodeCount++;
    }

    public void addNode(TreeNode newNode, TreeNode parent){
        newNode.setParentNode(parent);
        nodeList.add(newNode);
        nodeCount++;
    }

    public void addGoalNode(TreeNode goalNode){
        this.goalNode = goalNode;
    }

    public void addStartNode(TreeNode startNode){
        this.startNode = startNode;
        nodeList.add(startNode);
        nodeCount++;
    }

    public TreeNode getStartNode() { return startNode; }

    public TreeNode getGoalNode() { return goalNode; }

    public ArrayList<TreeNode> getNodeList() {return nodeList; }

    public void addEdge(Edge newEdge) { edgeList.add(newEdge); }

    public void addObstacle(Obstacle newObstacle) { obstacleList.add(newObstacle); }

    public int getNodeCount() { return nodeCount; }

    // Returns nearest node to a pair of vertices (goalNode) on the main screen
    public TreeNode getNearestNode(float xCoord, float yCoord){
        TreeNode nearestNode = startNode;
        float shortestDistance = parent.dist(nearestNode.getxCoord(), nearestNode.getyCoord(), xCoord, yCoord);
        for(TreeNode currentNode : nodeList){
            float temp = parent.dist(currentNode.getxCoord(), currentNode.getyCoord(), xCoord, yCoord);
            if(temp < shortestDistance){
                nearestNode = currentNode;
                shortestDistance = temp;
            }
        }
        return nearestNode;
    }

    // Draws all vertices in the graph to the screen
    public void drawObstacles(){
        for(Obstacle o: obstacleList){
            o.display();
            parent.fill(255, 255, 0);
        }
    }

    // Draws all vertices in the graph to the screen
    private void drawVertices(){
        if(!nodeList.isEmpty())
            startNode.display();
        if(goalNode != null)
            goalNode.display();
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
    public boolean intersectingGoalNode(TreeNode a){
        return (a.getxCoord() >= goalNode.getxCoord() && a.getxCoord() <= goalNode.getxCoord() + (float)goalNode.getWidth()
                && a.getyCoord() >= goalNode.getyCoord() && a.getyCoord() <= goalNode.getyCoord() + (float)goalNode.getHeight());
    }

    // Returns true if a vertex 'a' is intersecting with an obstacle object
    public boolean intersectingObstacle(TreeNode a){
        for(Obstacle o : obstacleList) {
            if(a.getxCoord() >= o.getxCoord() && a.getxCoord() <= o.getxCoord() + o.getWidth()
                    && a.getyCoord() >= o.getyCoord() && a.getyCoord() <= o.getyCoord() + o.getHeight())
                return true;
        }
        return false;
    }

    // Draws path in blue from the goal node back to the start node
    public void traceBackToRoot(){
        TreeNode start = this.goalNode.getParentNode();
        while(start != startNode){
            edgeList.add(new Edge(start,start.getParentNode(), 255, 2));
            start = start.getParentNode();
        }
    }

    // Returns the incident edge between vertex 'A' and vertex 'B'
    public Edge getEdge(TreeNode a, TreeNode b){
        for(Edge e : edgeList){
            if(e.connects(a,b))
                return e;
        }
        return null;
    }


}

