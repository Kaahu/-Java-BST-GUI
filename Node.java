package GUI;
import java.awt.*;


/**
 * This class represents our Nodes, it extends the Shape class.
 */
public class Node extends Shape{

    // Dimensions of the frame we draw in and the nodes themselves
    public final int WIDTH = 480;
    // We draw in a range slightly smaller than the frame to avoid nodes colliding with the sides
    public final int HEIGHT = 500;
    public final int RADIUS = 15;
    
    Node parent;
    Node l; // Right child
    Node r; // Left child
    int layer; // The height of the node in the BST
    int pos; // We assign a position to each possible place the node can be drawn
    int lpos; // Position of the left child
    int rpos; // Position of the right child
    int x; // x-coordinate of the node
    int y; // y-coordinate of the node

    int posByLayer; // We also assign a position within that layer (how many nodes from the left)


    /**
     * Constructor for the our nodes.
     * @param parent is the parent node of the constructed node
     * @param key is the key that node should hold
     * @param pos is the position that node will be drawn in
     */
    Node(Node parent, int key, int pos){

        super(key);
        this.parent = parent;
        this.pos = pos;
        
        r = null;
        l = null;
        
        this.lpos = (pos*2)+1;
        this.rpos = (pos*2)+2;
        calcLayer();
        this.posByLayer = this.pos - ((int)Math.pow(2, this.layer)-2);
        calcCoord();
        
    }

    /**
     * Updates the node's colour, position and position of its children.
     */
    protected void update(){

        this.colour = Color.black;
        this.lpos = (pos*2)+1;
        this.rpos = (pos*2)+2;
        calcLayer();
        this.posByLayer = this.pos - ((int)Math.pow(2, this.layer)-2);
        calcCoord();
    }

    
    protected void calcLayer(){
        if (this.parent == null){
            this.layer = 0;
        }else{
            this.layer = this.parent.layer+1;
        }
    }

    /**
     * Calculates where to draw the node.
     */
    protected void calcCoord(){
        
        int centre = (WIDTH/2);

        //How much to divide the drawSpace by per layer
        int canvasDivFactor = (int)Math.pow(2, layer+1);

        int lay = this.layer;
        
        if(lay == 0){
            this.x = centre;
            this.y = 30;
            
        }else{

            this.y = 30 + lay*50;

            if (isL(pos)){
                this.x = (this.parent.x - ((WIDTH)/canvasDivFactor));
                
            }else{
                this.x = this.parent.x + (WIDTH/canvasDivFactor);

            }

        if(this.parent.hasl() && this.x < WIDTH-(2*RADIUS)){
            if(this.isR(this.pos) && this.isOnRight()){
                this.x += 20;
            }
        }

        if(this.parent.hasr() && this.x > 0){
            if(this.isL(this.pos) && !this.isOnRight()){
                this.x -= 20;
            }
        }
    }
    }

    /**
     * Returns the position of the node.
     * @return the position of the node
     */
    protected int getPos(){

        return this.pos;

    }

    /**
     * Package accessible method to set the position of a node (and update appropriate variables).
     * @param pos is the position to set the node to
     */
    protected void setPos(int pos){

        this.pos = pos;
        this.posByLayer = this.pos - ((int)Math.pow(2, this.layer)-2);
        this.lpos = (pos*2)+1;
        this.rpos = (pos*2)+2;
        calcCoord();
        
    }

    /**
     * Weirdly complex method to determine if a node is on the right hand side of the BST.
     * @return true if the node is on the right hand side of the BST
     */
    protected boolean isOnRight(){

        // Calculate the value of the last node in the layer above this node's.
        int lastNodePrevLayer = ((int)Math.pow(2, this.layer)-2);

        // Calculate the value of the last node in this node's layer.
        int lastNodeCurrentLayer =  ((int)Math.pow(2, this.layer+1)-2);

        // Calculate the value of the first node in this node's layer.
        int firstNodeCurrentLayer = lastNodePrevLayer + 1;

        // Find the middle value of this node's layer.
        double midVal = ((double)(lastNodeCurrentLayer + firstNodeCurrentLayer))/2;

        //Determine if the node is on the right hand side.
        if(this.posByLayer > midVal){
            return true;
        }else{
            return false;
        }
    }
           
    //Returns true if node is a right hand child
    protected boolean isR(int n){
        return (n%2 == 0);
    }

    //Returns true if node is a left hand child
    protected boolean isL(int n){
        return (n%2 != 0);
    }

    //Returns true if node has a right hand child
    private boolean hasr(){
        return (this.r != null);
    }
    
    //Returns true if node has a left hand child
    private boolean hasl(){
        return (this.l != null);
    }

    /**
     * Draws the node.
     * @param g is the graphics object needed to draw the node
     */
    public void display(Graphics g){

        int Diameter = 2*RADIUS-layer;
        
        g.setColor(colour);
        g.drawOval(x,y,Diameter,Diameter);
        g.setColor(Color.black);
        g.drawString(Integer.toString(key), x+11, y+(Diameter/2));
        if(this.l != null){
            g.drawLine(this.x+RADIUS, this.y+(Diameter), this.l.x+RADIUS, this.l.y);
        }
        if(this.r != null){
            g.drawLine(this.x+RADIUS, this.y+(Diameter), this.r.x+RADIUS, this.r.y);
        }
    }
}
            
       
