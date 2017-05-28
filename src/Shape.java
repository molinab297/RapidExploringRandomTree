import processing.core.PApplet;

/********************************************************************
 *  CLASS (Abstract) Shape
 *
 *  OVERVIEW: Classes that extend this class should be basic shapes
 *  that satisfy the requirements below.
 *
 *  CONSTRUCTOR PARAMETERS:
 *      xCoord (int)     : x coordinate of shape
 *      yCoord (int)     : y coordinate of shape
 *      width  (int)     : width of shape
 *      height (int)     : height of shape
 *      color  (int)     : color of shape
 *      parent (PApplet) : Allows access to Processing's drawing methods
 *
 ********************************************************************/
public abstract class Shape {

    protected PApplet parent;
    private int xCoord, yCoord, width, height, color;

    Shape(int xCoord, int yCoord, int width, int height, int color, PApplet parent){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width  = width;
        this.height = height;
        this.color  = color;
        this.parent = parent;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setCoordinates(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setSize(int width, int height){
        this.width  = width;
        this.height = height;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
