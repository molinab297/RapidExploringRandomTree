import processing.core.PApplet;

/**
 * Created by Blake on 5/27/17.
 */
public class TreeNode extends Shape{

    TreeNode parentNode;

    public TreeNode(float xCoord, float yCoord, int width, int height, int color, PApplet parent){
        super(xCoord,yCoord,width,height,color, parent);
    }

    public TreeNode(int xCoord, int yCoord, int width, int height, int color, PApplet parent, TreeNode parentNode){
        super(xCoord,yCoord,width,height,color, parent);
        this.parentNode = parentNode;
    }

    public void display(){
        parent.fill(getColor());
        parent.ellipse(getxCoord(), getyCoord(), getWidth(), getHeight());
    }

    public void setParentNode(TreeNode parent){
        this.parentNode = parent;
    }

    public TreeNode getParentNode(){
        return this.parentNode;
    }
}
