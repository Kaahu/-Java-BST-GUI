package GUI;
import java.awt.*;

public class Informer extends Shape{

    public String text;

    Informer(int key, String text){

        super(key);
        this.text = text;
    }

    public void setText(String text){

        this.text = text;
    }

    public String getText(){

        return this.text;
    }

    public void setHeight(int height){

        this.y = height;

    }

    public void display(Graphics g){

        g.setColor(colour);
        
        String[] textList = this.text.split("\n");
        
        x = 5;
        y = 15;

        // drawString doesn't support line breaks! Ridiculous. Anyway, this
        // loop splits up the lines by "\n" and prints them on a new line.
        for(int i = 0; i < textList.length; i++){
            g.drawString(textList[i], x, y);
            y += g.getFontMetrics().getHeight();
        }
    }
}
    
   
