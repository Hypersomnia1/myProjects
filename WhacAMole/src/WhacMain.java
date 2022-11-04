import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Notes: FOR BACKGROUND ONLY, downloaded BackgroundPanel extension class

public class WhacMain {

    public int score;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Whac-A-Mole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new creature());
        /*Image img = ImageIO.read(new File("src/HoleyMoley.jpg"));
        //BackgroundPanel panel = new BackgroundPanel(img);
        //frame.add(panel);*/
        frame.pack();
        frame.setVisible(true);

        //score = 1;
        //JLabel s = new JLabel("Score:" );
        //frame.add(s);
    }
}
