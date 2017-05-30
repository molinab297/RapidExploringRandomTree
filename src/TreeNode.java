/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;

/********************************************************************
 *  CLASS TreeNode EXTENDS Shape
 *
 *  OVERVIEW: Represents a node on a Rapidly-Exploring Random Tree.
 *  TreeNodes in this particular implementation only know their
 *  location(x and y coordinates) on the graph and who their parent is.
 *
 *  CONSTRUCTOR PARAMETERS:
 *      xCoord (float)     : x coordinate of shape
 *      yCoord (float)     : y coordinate of shape
 *      width  (int)       : width of shape
 *      height (int)       : height of shape
 *      color  (int)       : color of shape
 *      parent (PApplet)   : Allows access to Processing's drawing methods
 *
 ********************************************************************/
public class TreeNode extends Shape{

    private TreeNode parentNode;
    private int cost;


    public TreeNode(float xCoord, float yCoord, int width, int height, int color, PApplet parent){
        super(xCoord,yCoord,width,height,color, parent);
    }

    public void display(){
        parent.fill(getColor());
        parent.rect(getxCoord(), getyCoord(), getWidth(), getHeight());
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public int getCost(){ return cost; }

    public void setParentNode(TreeNode parent){
        this.parentNode = parent;
    }

    public TreeNode getParentNode(){
        return this.parentNode;
    }
}
