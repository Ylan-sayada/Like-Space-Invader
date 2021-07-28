import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {
    static Rectangle frameSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    public Main(){
        GameScene game = new GameScene(Definitions.BACKGROUND,Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration()).top,this);
        add(game);
        setLayout(null);
        setSize(new Dimension(Definitions.FRAME_SIZE.width, Definitions.FRAME_SIZE.height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }
    public static void main(String[] args) {
    new Main();
    }

}
