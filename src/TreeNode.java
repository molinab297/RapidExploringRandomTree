import processing.core.PApplet;

/**
 * Created by Blake on 5/27/17.
 */
public class TreeNode extends Shape{

    public TreeNode(int xCoord, int yCoord, int width, int height, int color, PApplet parent){
        super(xCoord,yCoord,width,height,color, parent);
    }

    public void display(){
        parent.fill(getColor());
        parent.ellipse(getxCoord(), getyCoord(), getWidth(), getHeight());
    }
}
