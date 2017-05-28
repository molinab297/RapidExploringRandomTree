import processing.core.PApplet;

public class rrt extends PApplet {

    public static final int SCREEN_WIDTH   = 1200;
    public static final int SCREEN_HEIGHT  = 750;
    public static final int WHITE = 255, GRAY = 167, BLACK = 0;
    public static final int NODE_WIDTH = 5, NODE_HEIGHT = 5;
    public static final double STEP_LENGTH = 0.10;
    public static int node_color;

    RapidRandomTree rapidRandomTree;

    public static void main(String[] args) {PApplet.main("rrt");}

    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setup(){
        node_color = color(255,0,0);

        TreeNode startNode = new TreeNode(10,10,NODE_WIDTH, NODE_HEIGHT,color(50,205,50), this);
        TreeNode goalNode  = new TreeNode(1000,600,NODE_WIDTH, NODE_HEIGHT,color(128,0,128), this);
        rapidRandomTree    = new RapidRandomTree(startNode, goalNode, STEP_LENGTH, this);
        rapidRandomTree.addNode(goalNode);
    }

    public void draw(){
        background(0,0,20);
        rapidRandomTree.display();
    }

    public void mouseClicked(){

    }




}
