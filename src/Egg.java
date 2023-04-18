import java.awt.*;

public class Egg {

    //variables

    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public int dx;
    public int dy;
    public Rectangle rec;
    public boolean isAlive;
    //methods


    //constructor
    public Egg(int pXpos,int pYpos,int pWidth, int pHeight){
        xpos=pXpos;
        ypos=pYpos;
        width=pWidth;
        height=pHeight;
        isAlive=true;
        rec= new Rectangle (xpos,ypos,width,height);
    }
}
