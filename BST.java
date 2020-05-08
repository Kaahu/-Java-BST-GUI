package GUI;
import java.awt.*;

/**
 * Class to represent our binary search tree.
 */
public class BST {

    // Introductory text when you start up the GUI.
    String intro = "A Binary Search Tree (\"BST\") is a data structure\n" +
        "which stores data in nodes. Each node has an\n" +
        "integer value called a \"key\" which\n" +
        "determines where in the tree it is placed.\n" +
        "\n \n" +
        "If the key value of a node is LARGER than the\n" +
        "key value of the node it is added to, it is\n" +
        "added to the right child of that node. If it\n" +
        "is SMALLER, it is added to its LEFT child.\n" +
        "Adding continues recursively until the new\n" +
        "node reaches the end of the tree.\n" +
        "\n\n" +
        "Try inserting a number between 1 and 9.";

    Node root;
    Informer informer = new Informer(99,intro);

    // Variable to hold the parent of the current node, for insertion information.
    Node currentParent;

    // Variables for the informer object.
    int currentChildKey = 0;
    int currentSuccessorKey = 0;
    int successorParentKey = 0;

    boolean delCase1 = false;
    boolean delCase2 = false;
    boolean delCase3 = false;
    String delString = "";
    
    /**
     * Add a node to the tree: calls the addRec method.
     * @param key to be added to the BST
     */
    public void add(int key) {

        // Variable for informator.
        String keyStr = Integer.toString(key);

        // Insertion case 0: There are no nodes in the tree         
        if (root == null) {
            root = new Node(null, key, 0);
            
            // Since the root has no parent, we manually set the variable here.
            currentParent = root;

            // Text to be displayed on screen when add the first node
            informer.setText("Added " + keyStr + " to tree!\n\n" +
                             keyStr + " is the root node of the tree.");
            
            return;
        }else{

            // Since the currentParent is set to null at the end of the loop, we
            // Need to set it here to avoid a nullpointer exception.
            currentParent = root;
            
            root = addRec(null, root, key, root.pos);

            //Update to catch any positions altered by insertion
            updateVars(root);
        }

        /*___________INSERTION STRING INFO FOR INFORMER______________*/
        
        // Variables for informator.
        String parentStr = Integer.toString(currentParent.key);
        String comparison = "";
        String direction = "";

        // Illegal insertion: BST cannot contain 2 of the same key
        if(key == currentParent.key){
            informer.setText(keyStr + " is already in the tree, pick another number!");
            return;
        }

        // Insertion case 1: Key is larger than the current node.
        if (key > currentParent.key){
            comparison = "larger";
            direction = "right";
        } else {

            // Insertion case 2: Key is smaller than the current node.
            comparison = "smaller";
            direction = "left";
        }

        //Text to be displayed on screen when adding nodes
        informer.setText("Added " + keyStr + " to tree!\n\n" +
                         keyStr + " is " + comparison + " than " + parentStr + ", so " + keyStr +
                         " is inserted as node\n" + parentStr + "'s " +
                         direction + " child.");

        currentParent = null;

        /*___________________________________________________________*/
    }

    /**
     * Recursively add a node of given key to the tree.
     * @param parent is the root of the subtree to be added to
     * @param current is the node that is being processed
     * @param key is the key that the added node should hold
     * @param pos is the position that the current node is in
     * @return the current node
     */
    
    private Node addRec(Node parent, Node current, int key, int pos) {
        
        if (current == null) {
            current = new Node(parent, key, pos);

            return current;
        }
                
        if (key < current.key){

            if (current.l != null){
                currentParent = current.l;
            }

            current.l = addRec(current, current.l, key, current.lpos);

        }else if(key > current.key) {

            if (current.r != null){
                
                    currentParent = current.r;
                }

            current.r = addRec(current, current.r, key, current.rpos);

        } else {
            return current;
        }
        return current;
    }

    /**
     * Delete a given node: calls the deleteRec method.
     * @param key is the key of the node to be deleted
     */
    public void delete(int key){

        // Delete recursively from the root node downwards
        root = deleteRec(root, key);

        // Update root variables (coordinates etc.) from the root downards.
        // Necessary to visually reflect the changes to the BST.
        updateVars(root);


        /*___________DELETION STRING INFO FOR INFORMER______________*/
        
        delString = "";
        
        String keyStr = Integer.toString(key);

        delString += "Found and deleted node " + keyStr + "!\n\n";
        
        if(delCase3){
            
            delString+=
                "Node  " + keyStr + " had 2 children, so it was swapped\n" +
                "with its successor: " + currentSuccessorKey + ", then deleted.\n\n" +
                "A node's \"successor\" is the smallest\n" +
                "node in its RIGHT sub-tree ie. the node with\n" +
                "the next largest key with the sub-tree.";   
            
        }else if(delCase2){
            
            System.out.println("Deletion case 2");
            
            delString +=
                "Node " + keyStr + " had one child: " + currentChildKey +
                ", so " + keyStr + " was deleted, and \n" + currentChildKey +
                " was promoted to take its place.";
            
        }else if(delCase1){
            
            delString+=
                keyStr + " had no children, so no further actions\n" +
                "are needed.";
            
        }else{
            delString = "The tree doesn't contain a " + keyStr + " node, \n" +
                "please input a different number.";
        }
        
    
        informer.setText(delString);

        //Reset the deletion case variables
        delCase1=false;
        delCase2=false;
        delCase3=false;

        currentSuccessorKey = 0;
        currentChildKey = 0;
        /*__________________________________________________________*/
    }

    /**
     * Recursively search for, then delete node with a given key
     * @param current is the node being processed
     * @param key is the key of the node we want to delete
     * @return the next node to be processed if needed
     */
    private Node deleteRec(Node current, int key){

        String keyStr = Integer.toString(key);
        
        if (current == null){
            return null;
        }

        // If the node is found
        if (key == current.key) {
            
            //______Case 1: Node has no children______
            if (current.l == null && current.r == null) {

                //Flag for informer
                delCase1 = true;

                return null;

            //______Case 2: node has one (LEFT) child______   
            }else if (current.r == null){

                //Flag for informer
                if(!delCase2){
                    delCase2 = true;
                    currentChildKey = current.l.key;
                }
                
                // For root node
                if (current.parent == null){
                    current.l.setPos(0);
                    current.l.parent = null;
                    return current.l;
                }

                boolean leftChild = false;

                if (current.isL(current.pos)){

                    leftChild = true;
                    
                    current.parent.l = current.l;
                    current.l.parent = current.parent;
                    current.parent.l.setPos(current.l.parent.lpos);
                    current.parent.l.update();
                    
                }else{
                    current.parent.r = current.l;
                    current.l.parent = current.parent;
                    current.parent.r.setPos(current.l.parent.rpos);
                    current.parent.r.update();
                }

                if (leftChild){
                    leftChild = false;
                    return current.parent.l;
                }else{
                    return current.parent.r;
                }
                

            //______Case 2: node has one (RIGHT) child______   
            }else if (current.l == null){

                //Flag for informer
                if(!delCase2){
                    delCase2 = true;
                    currentChildKey = current.r.key;
                }
                
                // For root node
                if (current.parent == null){
                    current.r.setPos(0);
                    current.r.parent = null;
                    return current.r;
                }

                boolean leftChild = false;

                if (current.isL(current.pos)){

                    leftChild = true;
                    
                    current.parent.l = current.r;
                    current.r.parent = current.parent;
                    current.parent.l.setPos(current.r.parent.lpos);
                    current.parent.l.update();
                }else{
                    current.parent.r = current.r;
                    current.r.parent = current.parent;
                    current.parent.r.setPos(current.r.parent.rpos);
                    current.parent.r.update();
                }

                if (leftChild){
                    leftChild = false;
                    return current.parent.l;
                }else{
                    return current.parent.r;
                }
                

            //______Case 3: Node has two children______
            }else{

                if (!delCase3){
                    delCase3 = true;
                    currentSuccessorKey = findSmallestChild(current.r).key;
                }
                
                current.key = findSmallestChild(current.r).key;
                current.r = deleteRec(current.r, current.key);
                
            }
        }

        // If the key is smaller, search l
        if(key < current.key) {
            current.l = deleteRec(current.l, key);
            return current;
        }

        // If the key is smaller, search r
        current.r = deleteRec(current.r, key);
        return current;
    }

    /**
     * A recursive method that executes a pre-order search to update the node
     * coordinate variables.
     * @param current is the root of the subtree to be updated
     */
    
    private void updateVars(Node current){

        if(current == null){
            return;
        }

        current.update();
            
        if(current.l != null){
            updateVars(current.l);
        }

        if(current.r != null){
            updateVars(current.r);
        }

        return;
    }

    /**
     * Recursive method to return the smallest key in a given tree.
     * @param current is the root of the subtree of which the smallest child is searched for
     * @return the smallest child of the subtree
     */
    private Node findSmallestChild(Node current) {
        if (current.l == null){
            return current;
        }else{
            return findSmallestChild(current.l);
        }
    }

    /**
     * Get rid of those pesky nodes.
     */
    public void reset(){

        while(root!=null){
            delete(root.key);
        }

        informer.setText("Tree deleted :(");
    }

    /**
     * Check whether tree contains a given node: calls the containsNodeRec method.
     * @param key is the key of the node to be found
     * @return the node to be found
     */
     
    public Node findNode(int key){
        return findNodeRec(root, key);
    }
    
    /**
     * Method to determine if the tree contains a node with a given key.
     * @param current is the node being processed
     * @param key is the key of the node to be found
     * @return the next node to be processed
     */
    private Node findNodeRec(Node current, int key) {
        if (current.key == 0){
            return current;
        }
        if (key == current.key) {
            
            current.colour = Color.red;
            return current;
        }
        if (key > current.key) {
            return findNodeRec(current.r, key);
        } else {
            return findNodeRec(current.l, key);
        }
    }
}
