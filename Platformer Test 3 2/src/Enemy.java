import java.awt.*;

public class Enemy {

    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;
    public Enemy(Image picParameter) {


        xpos = (int) (Math.random()*1000);
        ypos = (int) (Math.random()*700);


        width = 50;
        height = 50;
        dx = (int) ((Math.random()*20)-10);
        dy = (int) ((Math.random()*20)-10);
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);

    }
    public void move() {
        xpos=xpos+dx;
        ypos=ypos+dy;

        if (ypos>700-height){
            ypos=700-height;
            dy=-dy;
        }
        if (ypos<0){
            ypos=0;
            dy=-dy;
        }
        if (xpos<0){
            xpos=0;
            dx=-dx;
        }
        if (xpos>1000-width){
            xpos=1000-width;
            dx=-dx;
        }

        rec = new Rectangle(xpos, ypos, width, height);

    }
}