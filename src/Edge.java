import processing.core.PApplet;

/*******************************************************************
 *  CLASS Edge
 *
 *  OVERVIEW: A class that represents a single Edge object. An edge
 *  object is defined to have a source(a) and destination TreeNode(b),
 *  although in an undirected graph, this will not matter which TreeNode
 *  is which.
 *
 *  CONSTRUCTOR PARAMETERS:
 *   a (TreeNode)       : a connected TreeNode, A
 *   b (TreeNode)       : a connected TreeNode, B
 *   color (int)      : color of edge
 *   weight (int)     : weight of edge
 *
 ********************************************************************/
public class Edge {

    private TreeNode a, b;
    private int color, weight;

    public Edge(TreeNode a, TreeNode b, int color, int weight){
        this.a = a;
        this.b = b;
        this.color  = color;
        this.weight = weight;
    }

    // Draws individual edge to screen
    public void display(PApplet parent){
        parent.stroke(color);
        parent.line(a.getxCoord(),a.getyCoord(),b.getxCoord(),b.getyCoord());
        parent.stroke(255);
    }

    public void setWeight(int w){
        weight = w;
    }
    public void setColor(int c){ color = c; }
    public void connect(TreeNode a, TreeNode b){
        this.a = a;
        this.b = b;
    }
    public int getColor() {
        return color;
    }
    public int getWeight() {
        return weight;
    }
    public boolean connects(TreeNode a, TreeNode b){
        return (this.a == a && this.b == b || this.b == a && this.a == b);
    }

}
