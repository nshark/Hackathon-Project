import java.awt.*;

public class Sprite {

    //variables

    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public int dx;
    public double dy;
    public int ddx;
    public double ddy;
    public Rectangle rec;
    public Rectangle testrec;
    public int health;
    public int stamina;
    public int score;
    public boolean isWin;
    public boolean isAlive;
    public boolean right;
    public boolean left;
    public boolean up;
    public boolean down;
    public boolean isGrounded;
    public boolean isJumping;


    //methods


    //constructor

    public Sprite(int pXpos,int pYpos,int pWidth, int pHeight){
        xpos=pXpos;
        ypos=pYpos;
        width=pWidth;
        height=pHeight;
        dx = 10;
        dy = 10;
        ddx=5;
        ddy=2;
        right=false;
        left=false;
        up=false;
        down=false;
        isGrounded=true;
        isJumping=false;
        rec= new Rectangle (xpos,ypos,width,height);
        testrec = new Rectangle(xpos, ypos+height, width,1);
    }

    public boolean move() {
        if (ypos>700){
            xpos=0;
            ypos=0;
            return true;
        }
        if (right){
            xpos=xpos+dx;
            if (xpos>1000-width) xpos=1000-width;

        }
        if (left){
            xpos=xpos-dx;
            if (xpos<0) xpos=0;

        }
        if (up){
            jump();
        }
        if (isJumping){
            dy-=ddy;
            ypos-=dy;
        }

        if (down){
            if (dy <= -5){
                dy -= dy/2;
            }
        }


        rec = new Rectangle(xpos, ypos, width, height);
        testrec = new Rectangle(xpos, ypos+height, width,1);
        return false;
    }

    public void jump(){
        isJumping=true;
        isGrounded=false;



    }



}