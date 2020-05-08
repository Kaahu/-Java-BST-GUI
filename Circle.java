package GUI;
import java.awt.*;

/** Circle class */
public class Circle extends Shape{

    public Circle(int key){

        super(key);
    }
  
    /** displays the circle */
  public void display(Graphics g){
  
    g.setColor(colour);
    g.fillOval(x,y,width,height);
    g.setColor(Color.black);
    g.drawString(Integer.toString(key), x, y);
  }
}
