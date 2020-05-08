package GUI;
import java.awt.*;
import javax.swing.*;

public class BSTGUI extends JFrame{
  
  public static void main(String[]args){
    
    JFrame frame = new JFrame("Binary Search Tree");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new ShapePanel());
    frame.pack();
    frame.setVisible(true);
  }
}
