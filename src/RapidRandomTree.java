import processing.core.PApplet;
import java.util.ArrayList;

/**
 * Created by Blake on 5/27/17.
 */

public class RapidRandomTree {
    private PApplet parent;
    private ArrayList<TreeNode> nodeList;
    private ArrayList<Edge> edgeList;
    private TreeNode startNode, goalNode;
    private double stepLength;

    public void RapidRandomTree(TreeNode startNode, TreeNode goalNode, double stepLength, PApplet parent){
        this.parent = parent;
        nodeList = new ArrayList<>();
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.stepLength = stepLength;
        nodeList.add(startNode);
    }

    public void addNode(TreeNode newNode){
        nodeList.add(newNode);
    }

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
    }

}

