package GUI;
import java.awt.*;
import javax.swing.*;

/** abstract parent class for each shape */
public abstract class Shape{

    // coordinates, dimension and colour of the shape
    protected int x, y, width, height, key;
    protected Color colour;
   
    /**
     * Constructor for shape object.
     * @param key is the key that shape will hold
     */
    public Shape(int key){
        
        this.key = key;
        
        width = 30;
        height = width;
        x = 235;
        y = 30;
        colour = Color.black;
    }
    
    /** displays the shape */
    public abstract void display(Graphics g);
    
}
