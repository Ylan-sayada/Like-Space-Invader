import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class Ennemy extends AnimationsPanel{
    private Random rand = new Random();
    private boolean inMove = true;
    private int velocity = Definitions.ENNEMY_VELOCITY_CAPACITY;
    private Point currentVelocity = new Point(rand.nextInt(velocity)+1,rand.nextInt(velocity)+1) ;

    public boolean isInMove() {
        return inMove;
    }

    public void setInMove(boolean inMove) {
        this.inMove = inMove;
    }


    public Ennemy(){
        super(Definitions.ENNEMY_FRAME,2000,new Dimension(Definitions.ENNEMY_WIDTH,Definitions.ENNEMY_HEIGHT));
        randomMove();
    }
    public void randomMove(){
        new Thread(()->{
            while (isInMove()){
                if(getX() < 0 || getX() > Definitions.FRAME_SIZE.width-getWidth() ){
                    currentVelocity.setLocation( rand.nextInt(velocity) * -1 , rand.nextInt(velocity));
                }
                else if (getY() < 0 || getY() > Definitions.FRAME_SIZE.height-getHeight()){
                    currentVelocity.setLocation( rand.nextInt(velocity),rand.nextInt(velocity) * -1  );
                }
                this.setLocation(getX()+currentVelocity.x,getY()+currentVelocity.y);
                try{
                    Thread.sleep(30);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
