import javax.swing.*;
import java.awt.*;
import java.util.Objects;

class ImagePanel extends JPanel {
    Dimension size;
    private ImageIcon img;
    private boolean toResized = false;

    public ImageIcon resizeImageIcon(Image img){
        return new ImageIcon(img.getScaledInstance(size.width,size.height,Image.SCALE_DEFAULT));
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }

    public boolean isToResized() {
        return toResized;
    }

    public void setToResized(boolean toResized) {
        this.toResized = toResized;
    }

    public ImagePanel(Image img, Dimension imgSize) {
        this.img = new ImageIcon(img);

        size =  Objects.requireNonNullElseGet(imgSize,
                () -> new Dimension(img.getWidth(null),
                        img.getHeight(null))
        );

        if(imgSize != null ) {
            toResized = true;
        }

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setDoubleBuffered(true);
        setLayout(null);
        setBounds(0,0,size.width,size.height);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
                if (toResized) {
                    img = resizeImageIcon(img.getImage());
                    toResized=false;
                }
                if (img.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawImage(img.getImage(),0,0,null);
                }

    }
}
