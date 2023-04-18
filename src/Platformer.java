import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Platformer implements Runnable, KeyListener {
    //variables
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    public Sprite player1;
    public Platform[] platforms1;
    public Egg[] eggs1;
    public Egg[] eggs2;
    //public Egg[] eggs3;
    public Platform[] platforms2;
    public int gemWidth;
    public int gemHeight;
    //public Platform[] platforms3;

    public String weather;

    public Platform[] platforms3;

    public Button button3;

    public Image playerPic;
    public Image platformPic;
    public Image eggPic;
    public Image portalPic;
    public Image sunnyPic;
    public Image snowyPic;
    public Image rainyPic;
    public Image startScreen;
    public Image cloudyPic;
    public int level;
    public int portalXpos;
    public int portalYpos;
    public int portalWidth;
    public int portalHeight;
    public SoundFile theme;

    public boolean activated = false;
//methods

    //constructor
    public Platformer (){

        setupGraphics();
        canvas.addKeyListener(this);

        gemWidth = 20;
        gemHeight = 35;

        player1= new Sprite(0,200,35,50);

        //playerPic = Toolkit.getDefaultToolkit().getImage("Chicken.png");
        playerPic = Toolkit.getDefaultToolkit().getImage("Madeline.png");
        platformPic = Toolkit.getDefaultToolkit().getImage("Platform.png");
        eggPic = Toolkit.getDefaultToolkit().getImage("Gem.png");
        portalPic = Toolkit.getDefaultToolkit().getImage("portal.png");
        sunnyPic = Toolkit.getDefaultToolkit().getImage("sunny background.jpeg");
        snowyPic = Toolkit.getDefaultToolkit().getImage("snow background.png");
        cloudyPic = Toolkit.getDefaultToolkit().getImage("cloud background.png");
        rainyPic = Toolkit.getDefaultToolkit().getImage("rainy background.png");
        startScreen = Toolkit.getDefaultToolkit().getImage("startscreen.png");
        theme = new SoundFile("theme.wav");

        weather = "sunny";

        platforms1= new Platform[5];
        platforms1[0]=new Platform(0,250,150,100);
        platforms1[1]=new Platform(150,300,150,100);
        platforms1[2]=new Platform(500,250,200,100);
        platforms1[3]=new Platform(800,150,200,100);
        platforms1[4]=new Platform(300,400,200,100);

        platforms2 = new Platform[3];
        platforms2[0]=new Platform(0,500,150,100);
        platforms2[1]=new Platform(150,300,150,100);
        platforms2[2]=new Platform(800,600,200,100);


        eggs1= new Egg[3];
        eggs1[0]=new Egg(platforms1[0].xpos + (platforms1[0].width/4), platforms1[0].ypos-50,gemWidth,gemHeight);
        eggs1[1]=new Egg(platforms1[1].xpos + (platforms1[1].width/4), platforms1[1].ypos-50,gemWidth,gemHeight);
        eggs1[2]=new Egg(platforms1[2].xpos + (platforms1[2].width/4), platforms1[2].ypos-50,gemWidth,gemHeight);

        eggs2= new Egg[3];
        eggs2[0]=new Egg(200, 250,gemWidth,gemHeight);
        eggs2[1]=new Egg(450,300 ,gemWidth,gemHeight);
        eggs2[2]=new Egg(650,500 ,gemWidth,gemHeight);

        platforms3 = new Platform[6];
        platforms3[0]=new Platform(250,600,150,100);
        platforms3[1]=new Platform(0,450,150,100);
        platforms3[2]=new Platform(250,300,150,100);
        platforms3[3]=new Platform(0,150,150,100);
        platforms3[4]=new Platform(800,200,200,100);
        platforms3[5]=new Platform(600,200,100,100);

        button3 = new Button(60, 150);


        level=0;

        testURL();
    }

    public void run(){

        while(true){
            render();
            moveThings();
            checkIntersections();
            pause(50);
            theme.loop();
        }
    }

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0,0,1000,700);
        if (weather == "sunny"){
            g.drawImage(sunnyPic,0,0,1000, 700, null);
        }

        if(level==1){

            for(int x=0;x< platforms1.length;x++) {
                g.drawImage(platformPic, platforms1[x].xpos, platforms1[x].ypos, platforms1[x].width, platforms1[x].height, null);
            }

            for(int x=0;x< eggs1.length;x++){
                if(eggs1[x].isAlive){
                    g.drawImage(eggPic,eggs1[x].xpos, eggs1[x].ypos, eggs1[x].width, eggs1[x].height, null);
                }
            }
        }
        if(level==2){

            for(int x=0;x< platforms2.length;x++) {
                g.drawImage(platformPic, platforms2[x].xpos, platforms2[x].ypos, platforms2[x].width, platforms2[x].height, null);
            }

            for(int x=0;x< eggs2.length;x++){
                if(eggs2[x].isAlive){
                    g.drawImage(eggPic,eggs2[x].xpos, eggs2[x].ypos, eggs2[x].width, eggs2[x].height, null);
                }
            }
        }
        if(level==3){

            for(int x=0;x< platforms3.length-1;x++) {
                g.drawImage(platformPic, platforms3[x].xpos, platforms3[x].ypos, platforms3[x].width, platforms3[x].height, null);
            }
            g.drawImage(platformPic, button3.xpos, button3.ypos, button3.width, button3.height, null);

            if(activated){
                g.drawImage(platformPic, platforms3[5].xpos, platforms3[5].ypos, platforms3[5].width, platforms3[5].height, null);
            }


        }

        g.drawImage(portalPic,portalXpos,portalYpos,portalWidth,portalHeight,null);


        if (level!=0) g.drawImage(playerPic,player1.xpos,player1.ypos,player1.width,player1.height,null);
        if (level==0) g.drawImage(startScreen,0,0,1000,700,null);
        //last 2 lines
        g.dispose();
        bufferStrategy.show();
    }

    public void setupGraphics(){
        frame = new JFrame("Climb for Glory");
        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(1000,700));
        panel.setLayout(null);

        canvas= new Canvas();
        canvas.setBounds(0,0,1000,700);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
    }

    public void pause(int wait){
        try{
            Thread.sleep(wait);
        }catch (InterruptedException e){

        }

    }
    public void moveThings() {
        if (player1.move()){
            if (level == 1){
                for (Egg e : eggs1){
                    e.isAlive = true;
                }
            }
            else{
                for (Egg e : eggs2){
                    e.isAlive=true;
                }
            }
        }
    }
    public void checkIntersections(){

        if (level==1){
            portalXpos=810;
            portalYpos=90;
            portalWidth=80;
            portalHeight=80;
        }
        if (level==2){
            portalXpos=810;
            portalYpos=550;
            portalWidth=80;
            portalHeight=80;
        }
        if (level==3){
            portalXpos=810;
            portalYpos=90;
            portalWidth=80;
            portalHeight=80;
        }

        Rectangle portalrec = new Rectangle(portalXpos,portalYpos,portalWidth,portalHeight);


        if (level==1){
            for (int x=0; x<platforms1.length; x++) {
                if (platforms1[x].rec.intersects(player1.testrec)) {
                    player1.isGrounded = true;
                    player1.isJumping = true;
                    player1.ddy = 2;
                    player1.dy = 20;

                } else {
                    player1.isJumping=true;
                }

            }
        }
        if (level==2){
            for (int x=0; x<platforms2.length; x++) {
                if (platforms2[x].rec.intersects(player1.testrec)) {
                    player1.isGrounded = true;
                    player1.isJumping = true;
                    player1.ddy = 2;
                    player1.dy = 20;

                } else {
                    player1.isJumping=true;
                }

            }
        }
        if (level==3){
            if (button3.rec.intersects(player1.testrec)) {
                activated = true;
            }
            if(activated){
                for (int x=0; x<platforms3.length; x++) {
                    if (platforms3[x].rec.intersects(player1.testrec)) {
                        player1.isGrounded = true;
                        player1.isJumping = true;
                        player1.ddy = 2;
                        player1.dy = 20;

                    } else {
                        player1.isJumping = true;
                    }
                }
            }else{
                for (int x=0; x<platforms3.length-1; x++) {
                    if (platforms3[x].rec.intersects(player1.testrec)) {
                        player1.isGrounded = true;
                        player1.isJumping = true;
                        player1.ddy = 2;
                        player1.dy = 20;

                    } else {
                        player1.isJumping=true;
                    }

                }
            }
        }
        //if (level==3){
        //    for (int x=0; x<platforms3.length; x++) {
        //        if (platforms3[x].rec.intersects(player1.testrec)) {
        //            player1.isGrounded = true;
        //            player1.isJumping = true;
        //            player1.ddy = 2;
        //            player1.dy = 20;
//
        //        } else {
        //            player1.isJumping=true;
        //        }
//
        //    }
        //}

        if (level==1){
            for (int x=0; x< eggs1.length; x++) {
                if (eggs1[x].rec.intersects(player1.rec)){
                    eggs1[x].isAlive=false;
                }
            }
        }
        if (level==2){
            for (int x=0; x< eggs1.length; x++) {
                if (eggs2[x].rec.intersects(player1.rec)){
                    eggs2[x].isAlive=false;
                }
            }
        }
        if (level==3){
            for (int x=0; x< eggs1.length; x++) {
                if (eggs2[x].rec.intersects(player1.rec)){
                    eggs2[x].isAlive=false;
                }
            }
        }

        if (level==1){
            if (!eggs1[0].isAlive && !eggs1[1].isAlive && !eggs1[2].isAlive && player1.rec.intersects(portalrec)){
                level++;
                player1.xpos=0;
                player1.ypos=0;
            }
        }
        if (level==2){
            if (!eggs2[0].isAlive && !eggs2[1].isAlive && !eggs2[2].isAlive && player1.rec.intersects(portalrec)){
                level++;
                player1.xpos=0;
                player1.ypos=0;
            }
        }
        if (level==3){
            if (!eggs2[0].isAlive && !eggs2[1].isAlive && !eggs2[2].isAlive && player1.rec.intersects(portalrec)){
                player1.xpos=200;
                player1.ypos=600;
            }
        }
    }

    public void keyPressed( KeyEvent event )
    {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
//        System.out.println("Key Pressed: " + key+"  Code: "+keyCode);

        if(keyCode==68){
            player1.right=true;
        }
        if(keyCode==65){
            player1.left=true;
        }
        if(keyCode==87){
            player1.up=true;
        }
        if(keyCode==83){
            player1.down=true;
        }
        if(keyCode==32){
            if (level==0)level++;
        }

    }//keyPressed()

    public void keyReleased( KeyEvent event )
    {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();


        if(keyCode==68){
            player1.right=false;
        }
        if(keyCode==65){
            player1.left=false;
        }
        if(keyCode==87){
            player1.up=false;
        }
        if(keyCode==83){
            player1.down=false;
        }



        //This method will do something when a key is released
    }//keyReleased()

    public void keyTyped( KeyEvent event )
    {
// handles a press of a character key  (any key that can be printed but not keys like SHIFT)

    }//keyTyped()

    public void testURL(){
        try {
            URL url = new URL("http://api.weatherstack.com/current?access_key=da3a4ae7e15a6e6662bb020bfb435255&query=Massachusetts");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            //string contains all of the raw information from the api
            String data = br.readLine();
            System.out.println(data);
            //writes the info into a file

            if(data == "{\"request\":{\"type\":\"City\",\"query\":\"Massachusetts\",\"language\":\"en\",\"unit\":\"m\"},\"location\":{\"name\":\"Aberdeen\",\"country\":\"United States of America\",\"region\":\"Massachusetts\",\"lat\":\"42.344\",\"lon\":\"-71.150\",\"timezone_id\":\"America\\/New_York\",\"localtime\":\"2021-02-12 14:20\",\"localtime_epoch\":1613139600,\"utc_offset\":\"-5.0\"},\"current\":{\"observation_time\":\"07:20 PM\",\"temperature\":-4,\"weather_code\":113,\"weather_icons\":[\"https:\\/\\/assets.weatherstack.com\\/images\\/wsymbols01_png_64\\/wsymbol_0001_sunny.png\"],\"weather_descriptions\":[\"Sunny\"],\"wind_speed\":0,\"wind_degree\":319,\"wind_dir\":\"NW\",\"pressure\":1023,\"precip\":0,\"humidity\":34,\"cloudcover\":0,\"feelslike\":-9,\"uv_index\":2,\"visibility\":10,\"is_day\":\"yes\"}}"){
                weather = "sunny";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}