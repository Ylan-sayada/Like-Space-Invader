import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;


public class AnimationsPanel extends JPanel implements ActionListener {

    int currentImg = 0, animationDelay;
    boolean alsoResized = false;
    Dimension size;

    public int getCurrentImg() {
        return currentImg;
    }

    public void setCurrentImg(int currentImg) {
        this.currentImg = currentImg;
    }

    public int getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(int animationDelay) {
        this.animationDelay = animationDelay;
    }

    public boolean isAlsoResized() {
        return alsoResized;
    }

    public void setAlsoResized(boolean alsoResized) {
        this.alsoResized = alsoResized;
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    public Timer getAnimationTimer() {
        return animationTimer;
    }

    public void setAnimationTimer(Timer animationTimer) {
        this.animationTimer = animationTimer;
    }


    Timer animationTimer;
    Image[] frames;


    public ImageIcon resizeImageIcon(Image img){
        return new ImageIcon(img.getScaledInstance(size.width,size.height,Image.SCALE_DEFAULT));
    }

    public AnimationsPanel(Image[] frames, int delay,Dimension frameSize) {


        this.frames = frames;
        size = Objects.requireNonNullElseGet(frameSize,
                () -> new Dimension((frames[0]).getWidth(null),
                        (frames[0]).getHeight(null))
        );
        setOpaque(false);
        animationDelay = delay;
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        setBounds(0,0,size.width,size.height);
        startAnimation();
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void startAnimation() {
        if (animationTimer == null) {
            currentImg = 0;
            animationTimer = new Timer(animationDelay, this);
            animationTimer.start();
        } else if (!animationTimer.isRunning())
            animationTimer.restart();
    }

    public void stopAnimation() {
        animationTimer.stop();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            if(!alsoResized) {
                frames[currentImg] = resizeImageIcon(frames[currentImg]).getImage();
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(frames[currentImg],0,0,null);
            if (currentImg < frames.length-1) {
                currentImg++;
            } else {

                currentImg = 0;
                alsoResized = true;
            }
        }
}

