import processing.core.PApplet;

public class Obstacle extends Shape{
    public Obstacle(int xCoord, int yCoord, int width, int height, int color, PApplet parent){
        super(xCoord,yCoord,width,height,color, parent);
    }

    public void display(){
        parent.fill(getColor());
        parent.rect(getxCoord(), getyCoord(), getWidth(), getHeight());
    }
}
