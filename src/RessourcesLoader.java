import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public class RessourcesLoader {
        Image img = null;
        Image[] frames = null;
        public    RessourcesLoader(File[] folder) {
                frames = new Image[folder.length];
                for (int i = 0; i < folder.length; i++) {
                        frames[i] = new RessourcesLoader(new File(folder[i].getAbsolutePath())).getImg();
                }
        }
        public    RessourcesLoader(File file) {
                try {
                       img =  ImageIO.read(file);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        public static File[] sortByNumber(File[] files) {
                Arrays.sort(files, new Comparator<File>() {
                        public int compare(File o1, File o2) {
                                int n1 = extractNumber(o1.getName());
                                int n2 = extractNumber(o2.getName());
                                return n1 - n2;
                        }

                        private int extractNumber(String name) {
                                int i = 0;
                                try {
                                        int s = name.indexOf('_')+1;
                                        int e = name.lastIndexOf('.');
                                        String number = name.substring(s, e);
                                        i = Integer.parseInt(number);
                                } catch(Exception e) {
                                        i = 0; // if filename does not match the format
                                        // then default to 0
                                }
                                return i;
                        }
                });
                return files;
        }
        public static File[] listFilesForFolder(final File folder) throws NullPointerException {
                File[] result = new File[Objects.requireNonNull(folder.listFiles()).length];

                for (int i = 0; i < Objects.requireNonNull(folder.listFiles()).length; i++) {

                        if (Objects.requireNonNull(folder.listFiles())[i].isDirectory()) {

                                listFilesForFolder(Objects.requireNonNull(folder.listFiles())[i]);

                        } else {

                                result[i] = Objects.requireNonNull(folder.listFiles())[i].getAbsoluteFile();
                        }
                }

                return sortByNumber(result);
        }

        public Image[] getFrames() {
                return frames;
        }

        public void setFrames(Image[] frames) {
                this.frames = frames;
        }
        public  Image getImg() {
                return img;
        }

        public void setImg(Image img) {
                this.img = img;
        }
}