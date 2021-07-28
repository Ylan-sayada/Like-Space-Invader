import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Definitions {

     final static Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
     static Rectangle FRAME_SIZE = Main.frameSize;

     final static Image BACKGROUND = new RessourcesLoader(new File("ressources/bg3.jpg")).getImg();
     final static Image[] ENNEMY_FRAME = new RessourcesLoader(RessourcesLoader.listFilesForFolder(new File("ressources/ennemyFrame"))).getFrames();
     final static Image[] VEHICLE_ICON = new RessourcesLoader(RessourcesLoader.listFilesForFolder(new File("ressources/playerFrame"))).getFrames();
     final  static Image[] MISSILE =new RessourcesLoader(RessourcesLoader.listFilesForFolder(new File("ressources/missileFrame"))).getFrames();


     final static int MISSILE_WIDTH = Math.round((float) (FRAME_SIZE.width * 0.05));
     final static int MISSILE_HEIGHT = Math.round((float) (FRAME_SIZE.height * 0.05));
     final static int MISSILE_VELOCITY = 10;
     final static Dimension MISSILE_DIMENSION = new Dimension(MISSILE_WIDTH,MISSILE_HEIGHT);

     final static int ENNEMY_WIDTH = Math.round((float) (FRAME_SIZE.width * 0.1));
     final static int ENNEMY_HEIGHT = Math.round((float) (FRAME_SIZE.width * 0.1));
     final static int MAX_ENNEMY_COUNT = 8;
     final static int ENNEMY_VELOCITY_CAPACITY = 5;

     final static int VEHICLE_VELOCITY = 40;
     final static int VEHICLE_WIDTH = Math.round((float) (FRAME_SIZE.width * 0.05));
     final static int VEHICLE_HEIGHT = Math.round((float) (FRAME_SIZE.width * 0.05));
     final static Dimension VEHICULE_DIMENSION = new Dimension(VEHICLE_WIDTH,VEHICLE_HEIGHT);
     final static Point VEHICLE_BASE_POSITION = new Point((FRAME_SIZE.width/2)-VEHICLE_WIDTH/2,FRAME_SIZE.height-VEHICLE_HEIGHT);


     enum POSITION
     {
          TOP,
          LEFT,
          RIGHT,
          BOTTOM
     };

}
