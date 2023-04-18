import java.awt.*;

public class Platform {

    //variables

    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public int dx;
    public int dy;
    public Rectangle rec;
    //methods


    //constructor
    public Platform(int pXpos,int pYpos,int pWidth, int pHeight){
        xpos=pXpos;
        ypos=pYpos;
        width=pWidth;
        height=pHeight;
        rec= new Rectangle (xpos,ypos,width,height);
    }
}
