import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import java.util.TimerTask;

public class GameScene extends ImagePanel implements KeyListener {


    private boolean gameIsRunning = true;
    private int insetTop;
    private int currentSceneId = 1;
    private JLabel score;
    private  SpaceShip spaceShip;
    private int lastKeyPressed;

    public GameScene (Image img,int insetTop,JFrame context){
        super(img,new Dimension(Definitions.FRAME_SIZE.width,Definitions.FRAME_SIZE.height));
        this.insetTop = insetTop;
        context.addKeyListener(this);
        setLayout(null);
        setBounds(0,0,Definitions.SCREEN_SIZE.width,Definitions.SCREEN_SIZE.height);
        setDoubleBuffered(true);
        enhanceScenes();
        gameLoop();


    }

    public void removeFromGame(Component component){
        this.remove(component);
    }

    public void checkColisionWithPlayer(){
        for(Component ennemy:this.getEnnemyContext()){
            if(ennemy != null){
                if(isCollision(spaceShip.getBounds(),ennemy.getBounds())) {
                    currentSceneId = 2;
                    enhanceScenes();
                }
            }
            }
    }
    public void enhanceScenes(){
        switch (currentSceneId){
            case 1:
                score = new JLabel("score : 0");
                spaceShip = new SpaceShip();
                ennemyGenerator();
                score.setBounds(0,0,400,200);
                score.setFont(new Font("Arial", Font.PLAIN, 50));
                score.setForeground(Color.red);
                spaceShip.moveTo(new Point(Definitions.VEHICLE_BASE_POSITION.x,Definitions.VEHICLE_BASE_POSITION.y-insetTop));
                add(score);
                add(spaceShip);
                break;
            case 2:
                removeAll();
                JLabel gameOver = new JLabel("<html><p>Game over </br>Your score:<strong>"+spaceShip.getCurrentScore()+"</strong></p></html>");
                JLabel replayText = new JLabel("<html><p>Press Enter to try again</p></html>");
                gameOver.setBounds(this.size.width/2-(400/2),this.size.height/2-(100/2),400,100);
                replayText.setBounds(0,this.size.height - 125,this.size.width/2,100);
                gameOver.setFont(new Font("Aria", Font.PLAIN, 50));
                gameOver.setForeground(Color.red);
                replayText.setFont(new Font("Aria", Font.PLAIN, 30));
                replayText.setForeground(Color.green);
                add(gameOver);
                add(replayText);
                break;
        }
    }
    public void ennemyGenerator(){
        int x,y,keepTheX,margin = 10;
        Random rand = new Random();
        int randomEnnemyCount = rand.nextInt(Definitions.MAX_ENNEMY_COUNT);
        keepTheX=margin;
        for(int i = 0; i < randomEnnemyCount + 1;i++ ){
            Ennemy ennemy = new Ennemy();
            x=ennemy.getWidth();
            y=ennemy.getHeight();
            ennemy.setLocation(x + keepTheX,y);
            keepTheX += x + margin;
            add(ennemy);
        }
    }

    public boolean isCollision(Rectangle object1,Rectangle object2){
        return object1.getX() < object2.getX() + object2.getWidth() && object1.getX() + object1.getWidth() > object2.getX() && object1.getY() < object2.getY() + object2.getHeight() && object1.getY() + object1.getHeight() > object2.getY();
    }

    public Component[] getEnnemyContext(){
        int i,j;
        j=i=0;
        Component[] tmp = new Component[this.getComponentCount()];
        try{
            while(i < this.getComponentCount()){
                if((this.getComponent(i).getClass()).toString().equals("class Ennemy")){
                    tmp[j++] = this.getComponent(i);
                }
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Component[] res;
        res=tmp;

        return  res;
    }
    public void gameLoop(){
        Toolkit.getDefaultToolkit().sync();
        new Thread( () ->{
            while(gameIsRunning){
                if(currentSceneId == 1){
                    score.setText("score : "+spaceShip.getCurrentScore());
                    if(getEnnemyContext().length == 2 ){
                        ennemyGenerator();
                    };
                    checkColisionWithPlayer();
                }

                repaint();
                try{
                    Thread.sleep(5);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(currentSceneId == 1){
            switch (e.getKeyCode()){
                case 37:
                    if(spaceShip.getLocation().x > 0 ) {
                        spaceShip.rotate(Definitions.POSITION.LEFT,Definitions.VEHICLE_ICON);
                        if(lastKeyPressed == e.getKeyCode()){
                            spaceShip.moveTo(new Point(spaceShip.getLocation().x - Definitions.VEHICLE_VELOCITY, spaceShip.getLocation().y));
                        }

                    }
                    break;
                case 38:
                    if(spaceShip.getLocation().y > Definitions.FRAME_SIZE.y ){
                        spaceShip.rotate(Definitions.POSITION.TOP,Definitions.VEHICLE_ICON);
                        if(lastKeyPressed == e.getKeyCode()){
                            spaceShip.moveTo(new Point(spaceShip.getLocation().x,spaceShip.getLocation().y - Definitions.VEHICLE_VELOCITY));
                        }


                    }
                    break;
                case 39:
                    if(spaceShip.getLocation().x < Definitions.FRAME_SIZE.width - Definitions.VEHICLE_WIDTH ){
                        spaceShip.rotate(Definitions.POSITION.RIGHT,Definitions.VEHICLE_ICON);
                        if(lastKeyPressed == e.getKeyCode()){
                            spaceShip.moveTo(new Point(spaceShip.getLocation().x+Definitions.VEHICLE_VELOCITY,spaceShip.getLocation().y));
                        }


                    }
                    break;
                case 40:
                    if(spaceShip.getLocation().y < Definitions.VEHICLE_BASE_POSITION.y - Definitions.FRAME_SIZE.y ){
                        spaceShip.rotate(Definitions.POSITION.BOTTOM,Definitions.VEHICLE_ICON);
                        if(lastKeyPressed == e.getKeyCode()){
                            spaceShip.moveTo(new Point(spaceShip.getLocation().x,spaceShip.getLocation().y + Definitions.VEHICLE_VELOCITY));
                        }
                    }
                    break;
            }
            lastKeyPressed = e.getKeyCode();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case 32:
                if(currentSceneId == 1)
                spaceShip.sendMissile(GameScene.this);
                break;
            case 10:
                if(currentSceneId == 2){
                    this.removeAll();
                    currentSceneId = 1;
                    enhanceScenes();
                }

        }
    }


}
