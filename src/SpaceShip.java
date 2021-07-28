import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SpaceShip extends ImagePanel {
    int currentScore = 0;

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    private Definitions.POSITION currentPosition = Definitions.POSITION.TOP;

    public Definitions.POSITION getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Definitions.POSITION currentPosition) {
        this.currentPosition = currentPosition;
    }

    public SpaceShip(){
        super(Definitions.VEHICLE_ICON[0],Definitions.VEHICULE_DIMENSION);
    setOpaque(false);
    setDoubleBuffered(true);
    }

    private boolean isSameAxis(Definitions.POSITION position){
        boolean result = true;
        if(currentPosition != position){
            switch (position){
                case TOP :
                    if(currentPosition != Definitions.POSITION.BOTTOM){
                        result = false;
                    }
                    break;
                case BOTTOM:
                    if(currentPosition != Definitions.POSITION.TOP){
                        result = false;
                    }
                    break;
                case RIGHT:
                    if(currentPosition != Definitions.POSITION.LEFT){
                        result = false;
                    }
                    break;
                case LEFT:
                    if(currentPosition != Definitions.POSITION.RIGHT){
                        result = false;
                    }
                    break;
            }
        }

        return result;
    }

    public void sendMissile(GameScene context){
        Point missileDirection,launchBase;

        int spaceShipCenter= (int)(getSize().width / 2);

        switch (currentPosition){
            case BOTTOM -> missileDirection = new Point(0,1);
            case LEFT -> missileDirection = new Point(-1,0);
            case RIGHT -> missileDirection= new Point(1,0);
            default -> missileDirection= new Point(0,-1);
        }

        ImagePanel missile = new ImagePanel(Definitions.MISSILE[currentPosition.ordinal()],Definitions.MISSILE_DIMENSION);

        if(currentPosition.equals(Definitions.POSITION.TOP) || currentPosition.equals(Definitions.POSITION.BOTTOM)){

            launchBase = new Point(getLocation().x + spaceShipCenter - (missile.getWidth()/2) ,getLocation().y - (getSize().height/2));
        }
        else{
            launchBase = new Point(getLocation().x + spaceShipCenter - (missile.getWidth()/2) ,getLocation().y + (getSize().height/2)- (missile.getHeight()/2));
        }

        missile.setBounds(launchBase.x, launchBase.y, Definitions.MISSILE_WIDTH,Definitions.MISSILE_HEIGHT);
        missile.setOpaque(false);
        context.add(missile);
        new Thread( () ->{
            Component[] ennemyContext;
            while(!missileisOutOfTheFrame(missile)){
                ennemyContext = context.getEnnemyContext();
                missile.setLocation(missile.getX() + ( missileDirection.x * Definitions.MISSILE_VELOCITY ) ,missile.getY() + ( missileDirection.y * Definitions.MISSILE_VELOCITY ));
                for (Component ennemy:ennemyContext) {
                    if(ennemy != null){
                        if( context.isCollision(ennemy.getBounds(),missile.getBounds())){
                            ((Ennemy) ennemy).setInMove(false);
                            currentScore +=10;
                            context.removeFromGame(ennemy);
                            context.removeFromGame(missile);
                            return;

                        }
                    }

                }
                try{
                    Thread.sleep(17);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
            context.remove(missile);

        }).start();
    }
    public boolean missileisOutOfTheFrame(JPanel context){
        boolean res = true;
        if(context.getLocation().y > 0 && context.getLocation().y < Definitions.FRAME_SIZE.height && context.getLocation().x > 0 && context.getLocation().x < Definitions.FRAME_SIZE.width){
            res = false;
        }
        return res;
    }
    public void rotate(Definitions.POSITION position,Image[] FramesIcon){
        if(currentPosition != position){
            if(!isSameAxis(position)) {
                setSize(new Dimension(getHeight(), getWidth()));
            }
            setToResized(true);
            setImg(new ImageIcon(FramesIcon[position.ordinal()]));
            currentPosition = position;
        }
    }
    public void moveTo(Point point){
        setBounds(point.x, point.y,size.width,size.height);

    }

}
