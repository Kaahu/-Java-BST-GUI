package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/** Holds all buttons and action objects */
public class ShapePanel extends JPanel{

  public final int WIDTH = 500;
  public final int HEIGHT = 500;
  private JPanel controlPanel = new JPanel();
  public InfoPanel infoPanel = new InfoPanel();
  public DrawingPanel drawPanel = new DrawingPanel();
  private Timer timer;
  private final int DELAY = 10;
  private JTextField delNum = new JTextField(1);
  private JTextField inNum = new JTextField(1);
  private JTextField seNum = new JTextField(1);
  private JButton delButt = new JButton("Delete");
  private JButton inButt = new JButton("Insert");
  private JButton seButt = new JButton("Search");
  private JButton addInfoButt = new JButton("Explain Insert");
  private JButton delInfoButt = new JButton("Explain Delete");
  private JButton searchInfoButt = new JButton("Explain Search");
  private JButton resetButt = new JButton("Reset");
  public BST bst = new BST();
  private boolean needExplain = false; // For if a process explanation is needed
  private String text = ""; // To hold the explanation
  
  /**
   * Adds the listener to objects and adds objects to the panel and sets the size
   * of the panel and adds the panel control panel to the shape panel.
   */
  public ShapePanel(){

    ButtonListener listener = new ButtonListener();
    inButt.addActionListener(listener);
    controlPanel.add(inButt);
    inNum.setText("");
    controlPanel.add(inNum);
    delButt.addActionListener(listener);
    controlPanel.add(delButt);
    delNum.setText("");
    controlPanel.add(delNum);
    seButt.addActionListener(listener);
    controlPanel.add(seButt);
    seNum.setText("");
    controlPanel.add(seNum);

    resetButt.addActionListener(listener);//action events
    controlPanel.add(resetButt);
    
    controlPanel.add(addInfoButt);
    addInfoButt.addActionListener(listener);
    controlPanel.add(delInfoButt);
    delInfoButt.addActionListener(listener);
    controlPanel.add(searchInfoButt);
    searchInfoButt.addActionListener(listener);
    
    controlPanel.setPreferredSize(new Dimension(125,HEIGHT));
    add(controlPanel);
    add(drawPanel);
    add(infoPanel);
    timer = new Timer(DELAY, listener);
    
  }
  
  /** where shapes are drawn */
  private class DrawingPanel extends JPanel{
    
    private DrawingPanel(){
      
      setPreferredSize(new Dimension(500,500));
      setBackground(Color.white);
    }
    
    /**
     * Paints the nodes and text.
     * @param g is the Graphics object needed to paint
     */
    public void paintComponent(Graphics g){
      
      super.paintComponent(g);

      // Call the display method of the node object, which calls the display method of
      // The node's children.
      display(bst.root, g);

      if(needExplain){
          g.setColor(Color.black);
          g.drawString(text, 20, 20);
      }
    }

      /**
       * Displays the nodes in the BST.
       * @param node is the root of the tree to be displayed
       * @param g is the Graphics object needed to draw
       */
      private void display(Node node, Graphics g){
          if(node == null){
              return;
          }
          node.display(g);
          display(node.l, g);
          display(node.r, g);
      }
  }

    /**
     * Displays the teaching information for the BST processes.
     */
    private class InfoPanel extends JPanel{
    
        private InfoPanel(){
      
            setPreferredSize(new Dimension(300,500));
        }

        public void paintComponent(Graphics g){
      
            super.paintComponent(g);

            infoDisplay(bst.informer, g);
        }

        private void infoDisplay(Informer info, Graphics g){
            if(info == null){
                return;
            }
            info.display(g);
        }
    }
  
  /** listener for all action events */
  private class ButtonListener implements ActionListener{
    
    /**
     * Reacts accordingly to any user actions.
     * @param e is the user action
     */
    public void actionPerformed(ActionEvent e){     
        
        JButton button = (JButton)e.getSource();

        //______DELETE BUTTON______
        if(button.getText().equals("Delete")){
          try{
            int delKey = Integer.parseInt(delNum.getText());
            if(delKey > 0 && delKey < 10){
                bst.delete(delKey);
            }else{
                bst.informer.setText("Please enter a number between 1 and 9");
            }
            delNum.setText("");
          }catch(NumberFormatException numE){
          System.out.println("NumberFormatException, please enter a number");
          delNum.setText("");
          }catch(IndexOutOfBoundsException indE){
          System.out.println("IndexOutOfBoundsException, number out of range");
          delNum.setText("");
          }
        }

        //______SEARCH BUTTON______
        else if(button.getText().equals("Search")){
            try{
                int seKey = Integer.parseInt(seNum.getText());
                if(seKey > 0 && seKey < 10){
                    Node foundNode = bst.findNode(seKey);
                    if(foundNode.key == seKey){
                        bst.informer.setText("Node " + seKey + " found!");
                    }else{
                        bst.informer.setText("Node " + seKey + " not found");
                    }
                }else{
                    bst.informer.setText("Please enter a number between 1 and 9");
                }
                seNum.setText("");
            }catch(NumberFormatException numE){
                bst.informer.setText("Please enter a NUMBER between 1 and 9");
                seNum.setText("");
            }catch(IndexOutOfBoundsException indE){
                System.out.println("IndexOutOfBoundsException, number out of range");
                seNum.setText("");
            }
        }
        
        //______INSERT BUTTON______
        else if(button.getText().equals("Insert")){
                try{
                    int inKey = Integer.parseInt(inNum.getText());
                    if((inKey > 0 && inKey < 10)){
                        bst.add(inKey);
                    }
                    else{
                        bst.informer.setText("Please enter a number between 1 and 9");
                    }
                }catch(NumberFormatException numE){
                    bst.informer.setText("Please enter a number between 1 and 9");
                }catch(IndexOutOfBoundsException indE){
                    bst.informer.setText("IndexOutOfBoundsException, number out of range");
                }
                inNum.setText("");
        }
        
        //_______RESET BUTTON______
        else if(button.getText().equals("Reset")){

            bst.reset();
        }

        //______EXPLAIN INSERT BUTTON______
        else if(button.getText().equals("Explain Insert")){

            String insertionExplanation =

                "When a node is added to the tree, it is first\n" +
                "compared to the root node of the tree (the\n" +
                "node at the top): ff the new node is larger\n" +
                "than the root node, it is moved to the right.\n"+
                "If the new node is smaller than the root node,\n" +
                "it is moved to the left.\n\n"+

                "If the root node has any \"children\" (larger\n" +
                "or smaller nodes connected to it), the new\n"+
                "node is then compared to them and, again,\n" +
                "moved either left or right depending on\n" +
                "whether it is smaller or larger respectively.\n\n" +

                "The comparisons continue until the node\n"+
                "encounters a vacant position in the tree. For\n"+
                "example: if the new node is larger than a\n"+ 
                "node withno right hand child, it becomes that\n" +
                "node's right child.";

                bst.informer.setText(insertionExplanation);
        }

        

        //______EXPLAIN DELETE BUTTON______
        else if(button.getText().equals("Explain Delete")){


            String deleteExplanation =

                "When a node is deleted, there are three\n " +
                "possible currentcases:\n\n" +

                "Case 1: The node has no children.\n" +
                "In this case, the node is simply removed, and\n" +
                "no further action is needed.\n\n" +

                "Case 2: The node has one child.\n"+
                "In order to remove the desired node, but still\n"+
                "keep the tree whole and balanced, the node's\n"+
                "child takes the place of the deleted node, and\n"+
                "its entire sub-tree is promoted accordingly.\n\n"+
                
                "Case 3: The node has two children.\n"+
                "This case is trickier. In order to keep the\n"+
                "balance of the tree, we must replace the\n"+
                "deleted node with either its \"successor\"\n"+
                "or its \"predecessor\". A node's successor\n"+
                "is the smallest child of its right sub-tree,\n"+
                "which will be the next highest value. Its\n" +
                "predecessor is the largest child of its left\n"+
                "left subtree, which will be the next lowest\n"+
                "value. Either approach will keep the tree\n"+
                "balanced, as long as it is used consistently.\n"+
                "";
            
            bst.informer.setText(deleteExplanation);

        //______EXPLAIN SEARCH BUTTON______
        }else if(button.getText().equals("Explain Search")){

            String searchExplanation =

                "Searching a BST is a similar operation to\n"+
                "inserting a node into one. The value being\n"+
                "searched for is compared to the root node then,\n"+
                "if it larger than the root node, it is\n"+
                "compared to the root node's right child. If it\n"+
                "is smaller, it is compared to the root node's\n"+
                "left child.\n\n"+

                "The comparisons then continue between the\n"+
                "value being searched for, and the root nodes\n"+
                "children, then on to those nodes' children,\n"+
                "recursively until either the value is found,\n"+
                "or until there are no further children to\n"+
                "search, in which case the tree does not\n"+
                "contain the search value.\n\n"+

                "An advantage of the BST data structure is\n"+
                "that the average search takes O(log n)\n"+
                "comparisons. However, in the worst case,\n"+
                "search complexity can be O(n). This occurs\n"+
                "when the entire tree is a single branch,\n"+
                "and the value being searched for is at the\n"+
                "bottom, meaning every node in the tree has\n"+
                "to be checked.";

            bst.informer.setText(searchExplanation);
        }
          
      drawPanel.repaint();
      infoPanel.repaint();
    }
  }
}
