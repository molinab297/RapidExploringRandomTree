/****************************
 * Author: Blake A. Molina
 * Created on: 5/27/17
 * License: GPL
 ****************************/
import processing.core.PApplet;
/********************************************************************
 *  CLASS Obstacle EXTENDS Shape
 *
 *  OVERVIEW: Represents a barrier that Rapidly-Exploring Random Trees
 *  cannot pass through. This is to demonstrate how RRT's can
 *  maneuver around obstacles and still reach their target destination
 *
 *  CONSTRUCTOR PARAMETERS:
 *      xCoord (float)   : x coordinate of shape
 *      yCoord (float)   : y coordinate of shape
 *      width  (int)     : width of shape
 *      height (int)     : height of shape
 *      color  (int)     : color of shape
 *      parent (PApplet) : Allows access to Processing's drawing methods
 *
 ********************************************************************/
public class Obstacle extends Shape{
    public Obstacle(float xCoord, float yCoord, int width, int height, int color, PApplet parent){
        super(xCoord,yCoord,width,height,color, parent);
    }

    public void display(){
        parent.fill(getColor());
        parent.rect(getxCoord(), getyCoord(), getWidth(), getHeight());
    }
}
