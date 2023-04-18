import java.awt.*;

public class Button {

    //variables

    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public Rectangle rec;
    //methods


    //constructor
    public Button(int pXpos, int pYpos){
        xpos=pXpos;
        ypos=pYpos;
        width=50;
        height=50;
        rec= new Rectangle (xpos,ypos,width,height);
    }
}

