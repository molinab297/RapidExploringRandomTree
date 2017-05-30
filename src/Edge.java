/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;

/*******************************************************************
 *  CLASS Edge EXTENDS Shape
 *
 *  OVERVIEW: A class that represents a single Edge object. An edge
 *  object is defined to have a source(a) and destination TreeNode(b),
 *  although in an undirected graph, this will not matter which TreeNode
 *  is which.
 *
 *  CONSTRUCTOR PARAMETERS:
 *   a (TreeNode)    : a connected TreeNode, A
 *   b (TreeNode)    : a connected TreeNode, B
 *   color (int)     : color of edge
 *
 ********************************************************************/
public class Edge {

    private TreeNode a, b;
    private int color;

    public Edge(TreeNode a, TreeNode b, int color){
        this.a = a;
        this.b = b;
        this.color  = color;
    }

    // Draws individual edge to screen
    public void display(PApplet parent){
        parent.stroke(color);
        parent.line(a.getxCoord(),a.getyCoord(),b.getxCoord(),b.getyCoord());
        parent.stroke(0);
    }

    public void setColor(int c){ color = c; }
    public void connect(TreeNode a, TreeNode b){
        this.a = a;
        this.b = b;
    }
    public int getColor() {
        return color;
    }
    public boolean connects(TreeNode a, TreeNode b){
        return (this.a == a && this.b == b || this.b == a && this.a == b);
    }

}
