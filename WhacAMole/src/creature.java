import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class creature extends JPanel {

    private JLabel monster, display, misses, modeinfo, bang, cloudy, prompt;
    private ImageIcon pulledCloudImg;
    private int score, miss = 0;
    private int rX = (int) (Math.random() * (800 - 150)) + 150;
    private int rY = (int) (Math.random() * (350 - 0)) + 0;
    private int mode, r1 = 0;
    private int quant = 12;
    private JButton switchm;
    private JCheckBox switchHide;
    private final int DELAY = 1000;
    private Timer tlimit, fxt;
    private int tick = (int) (Math.random() * (4 - 2)) + 2;
    private ArrayList<JLabel> holes = new ArrayList<JLabel>();
    private ArrayList<coordinates> hc = new ArrayList<coordinates>();
    int r = (int) (Math.random() * (quant - 0)) + 0;
    private boolean hidden, wait, hit, cloud, start = false;
    Dimension holesize;

    public creature() throws IOException {

        setPreferredSize(new Dimension(850, 400));
        setBackground(Color.white);
        pulledCloudImg = new ImageIcon((ImageIO.read(new File("src/cloudy.png"))));
        Image imgCloudy = pulledCloudImg.getImage().getScaledInstance(35, 35, Image.SCALE_DEFAULT);

        //image = ImageIO.read(new File("image name and path"));
        display = new JLabel("Score: " + score);
        monster = new JLabel(new ImageIcon(ImageIO.read(new File("src/monst1.jpg"))));
        bang = new JLabel(new ImageIcon(ImageIO.read(new File("src/boomhit.png"))));
        cloudy = new JLabel(new ImageIcon(imgCloudy));
        misses = new JLabel("Misses: " + miss);
        modeinfo = new JLabel("Mode: Full");
        prompt = new JLabel("Click anywhere to begin!");
        switchm = new JButton("Change Mode");
        switchHide = new JCheckBox("Disappear", false);

        for (int i = 0; i < quant; i++) {
            holes.add(new JLabel(new ImageIcon(ImageIO.read(new File("src/HoleyMoley.jpg")))));
        }

        prompt.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
        prompt.setForeground(Color.GREEN);

        Dimension size = monster.getPreferredSize();
        Dimension size2 = display.getPreferredSize();
        Dimension size3 = misses.getPreferredSize();
        holesize = holes.get(1).getPreferredSize();
        Dimension modelabel = modeinfo.getPreferredSize();
        Dimension bdim = switchm.getPreferredSize();
        Dimension hidim = switchHide.getPreferredSize();

        tlimit = new Timer(DELAY, new TimerListener());
        fxt = new Timer(500, new FXTimer());
        switchm.addActionListener(new SwitchListener());
        //switchHide.addActionListener();

        setLayout(null);
        //setSize(650,260);


        holes.get(0).setBounds(rmize(250, 180), rmize(150, 50), holesize.width, holesize.height);
        holes.get(1).setBounds(rmize(320, 290), rmize(150, 50), holesize.width, holesize.height);
        holes.get(2).setBounds(rmize(390, 360), rmize(150, 50), holesize.width, holesize.height);
        holes.get(3).setBounds(rmize(465, 430), rmize(150, 50), holesize.width, holesize.height);
        holes.get(4).setBounds(rmize(550, 515), rmize(150, 50), holesize.width, holesize.height);
        holes.get(5).setBounds(rmize(630, 595), rmize(150, 50), holesize.width, holesize.height);

        holes.get(6).setBounds(rmize(250, 180), rmize(350, 200), holesize.width, holesize.height);
        holes.get(7).setBounds(rmize(320, 290), rmize(350, 200), holesize.width, holesize.height);
        holes.get(8).setBounds(rmize(390, 360), rmize(350, 200), holesize.width, holesize.height);
        holes.get(9).setBounds(rmize(465, 430), rmize(350, 200), holesize.width, holesize.height);
        holes.get(10).setBounds(rmize(550, 515), rmize(350, 200), holesize.width, holesize.height);
        holes.get(11).setBounds(rmize(630, 595), rmize(350, 200), holesize.width, holesize.height);


        //monster.setBounds(hole1.getX()+5, hole1.getY()-size.height+8, size.width, size.height);
        monster.setBounds(rX, rY, size.width, size.height);
        display.setBounds(10, 10, size2.width, size2.height);
        misses.setBounds(10, 25, size3.width, size3.height);
        modeinfo.setBounds(10, 40, modelabel.width, modelabel.height);
        switchm.setBounds(10, 65, bdim.width, bdim.height);
        switchHide.setBounds(10, 90, hidim.width, hidim.height);
        prompt.setBounds(175,175,prompt.getPreferredSize().width,prompt.getPreferredSize().height);
        prompt.setVisible(true);
        bang.setVisible(false);
        cloudy.setVisible(false);
        monster.setVisible(false);


        for (int i = 0; i < quant; i++) {
            holes.get(i).setVisible(false);
        }


        addMouseListener(new ClickListener());
        add(display);
        add(monster);
        add(bang);
        add(cloudy);
        add(misses);
        add(modeinfo);
        add(switchm);
        add(switchHide);
        add(prompt);
        for (int i = 0; i < quant; i++) {
            add(holes.get(i));
        }
        tlimit.start();


    }

    private class ClickListener implements MouseListener { //if I hit it I need to hide it for 1 second
        public void mousePressed(MouseEvent e) {
            if (start){
            if (hidden == false) {
                monster.setVisible(true);
                if (mode == 0) {
                    if ((e.getX() > rX) && (e.getX() < rX + monster.getPreferredSize().width) && (e.getY() > rY) && (e.getY() < rY + monster.getPreferredSize().height)) {
                        hit = true;
                        bang.setBounds(monster.getX(), monster.getY(), bang.getPreferredSize().width, bang.getPreferredSize().height);
                        bang.setVisible(true);
                        fxt.start();
                        if (switchHide.isSelected() == true) {
                            monster.setVisible(false);
                            hidden = true;
                            wait = true;
                            tick = 1;
                            tlimit.restart();
                        } else {
                            tick = (int) (Math.random() * (4 - 2)) + 2;
                        }
                        score++;

                        display.setText("Score: " + score);
                        display.setBounds(10, 10, display.getPreferredSize().width, display.getPreferredSize().height);
                        if(score>=20){
                            gameover();
                        }
                        rX = (int) (Math.random() * (800 - 150)) + 150;
                        rY = (int) (Math.random() * (300 - 0)) + 0;
                        monster.setBounds(rX, rY, monster.getPreferredSize().width, monster.getPreferredSize().height);

                    }
                } else {

                    if ((e.getX() > holes.get(r).getX() + 5) && (e.getX() < holes.get(r).getX() + 5 + monster.getPreferredSize().width) && (e.getY() > holes.get(r).getY() - monster.getPreferredSize().height + 8) && (e.getY() < holes.get(r).getY() - monster.getPreferredSize().height + 8 + monster.getPreferredSize().height)) {

                        hit = true;
                        bang.setBounds(monster.getX(), monster.getY(), bang.getPreferredSize().width, bang.getPreferredSize().height);
                        bang.setVisible(true);
                        fxt.restart();
                        if (switchHide.isSelected() == true) {

                            monster.setVisible(false);
                            hidden = true;
                            wait = true;
                            tick = 1;
                            tlimit.restart();
                        } else {
                            tick = (int) (Math.random() * (4 - 2)) + 2;
                            tlimit.restart();
                        }

                        score++;
                        display.setText("Score: " + score);
                        display.setBounds(10, 10, display.getPreferredSize().width, display.getPreferredSize().height);
                        if(score>=20){
                            gameover();
                        }
                        r = (int) (Math.random() * (quant - 0)) + 0;
                        monster.setBounds(holes.get(r).getX() + 5, holes.get(r).getY() - monster.getPreferredSize().height + 8, monster.getPreferredSize().width, monster.getPreferredSize().height);

                    }

                }
            }
        } else if (prompt.isVisible()){
                start=true;
                prompt.setVisible(false);
                monster.setVisible(true);
                tlimit.restart();
            }
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (start){
            //System.out.println(r + "and" + holes.get(r).getX()+ ","+holes.get(r).getY());
            tick--;
            if (wait == false) {
                if (tick == 0) {
                    tick = (int) (Math.random() * (4 - 2)) + 2;
                    tlimit.restart();
                    if (r1 == 0) {
                        miss++;


                        misses.setText("Misses: " + miss);
                        misses.setBounds(10, 25, misses.getPreferredSize().width, misses.getPreferredSize().height);
                        cloud = true;
                        cloudy.setBounds(monster.getX(), monster.getY(), cloudy.getPreferredSize().width, cloudy.getPreferredSize().height);
                        cloudy.setVisible(true);
                        fxt.restart();
                        r1++;
                    } else {

                        r1 = 0;
                    }


                    if ((switchHide.isSelected() == false || hidden == true) && wait == false) {
                        r1 = 0;
                        hidden = false;
                        monster.setVisible(true);
                        if (mode == 0) {

                            rX = (int) (Math.random() * (800 - 150)) + 150;
                            rY = (int) (Math.random() * (300 - 0)) + 0;
                            monster.setBounds(rX, rY, monster.getPreferredSize().width, monster.getPreferredSize().height);

                        } else {

                            r = (int) (Math.random() * (quant - 0)) + 0;
                            monster.setBounds(holes.get(r).getX() + 5, holes.get(r).getY() - monster.getPreferredSize().height + 8, monster.getPreferredSize().width, monster.getPreferredSize().height);
                        }
                    } else {
                        tick = 1;
                        wait = false;
                        hidden = true;
                        monster.setVisible(false);
                        //r1=0;
                    }
                }
            } else {
                tick = (int) (Math.random() * (4 - 2)) + 2;
                monster.setVisible(true);
                hidden = false;
                wait = false;
                r1 = 0;
            }
        }
        }
    }

    private class FXTimer implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (start) {
                if (hit = true) {
                    bang.setVisible(false);
                    hit = false;
                    fxt.stop();
                }
                if (cloud = true) {

                    cloudy.setVisible(false);
                    cloud = false;
                    fxt.stop();
                }
            }

        }
    }

    private class SwitchListener implements ActionListener {
        //--------------------------------------------------------------
        //  Updates the counter and label when the button is pushed.
        //--------------------------------------------------------------
        public void actionPerformed(ActionEvent event) {

            if (mode == 0) {
                //monster.setVisible(true);
                hidden = false;
                mode = 1;
                modeinfo.setText("Mode: Holes");
                modeinfo.setBounds(10, 40, modeinfo.getPreferredSize().width, modeinfo.getPreferredSize().height);
                for (int i = 0; i < quant; i++) {
                    holes.get(i).setVisible(true);
                }
                r = (int) (Math.random() * (quant - 0)) + 0;
                monster.setBounds(holes.get(r).getX() + 5, holes.get(r).getY() - monster.getPreferredSize().height + 8, monster.getPreferredSize().width, monster.getPreferredSize().height);
                //System.out.println(r);
                tick = (int) (Math.random() * (4 - 2)) + 2;
                tlimit.restart();
            } else {
                //monster.setVisible(true);
                hidden = false;
                mode = 0;
                modeinfo.setText("Mode: Full");
                modeinfo.setBounds(10, 40, modeinfo.getPreferredSize().width, modeinfo.getPreferredSize().height);
                for (int i = 0; i < quant; i++) {
                    holes.get(i).setVisible(false);
                }
                tlimit.restart();
                rX = (int) (Math.random() * (800 - 150)) + 150;
                rY = (int) (Math.random() * (300 - 0)) + 0;
                monster.setBounds(rX, rY, monster.getPreferredSize().width, monster.getPreferredSize().height);
            }

        }
    }

    public int rmize(int max, int min) {
        return (int) (Math.random() * (max - min)) + min;
    }
    public void gameover(){

            start=false;
            tlimit.stop();
            fxt.stop();
            int temp = score;
            score=0;
            display.setText("Score: " + score);
            display.setBounds(10, 10, display.getPreferredSize().width, display.getPreferredSize().height);
            int temp2 = miss;
            miss=0;
            misses.setText("Misses: " + miss);
            misses.setBounds(10, 25, misses.getPreferredSize().width, misses.getPreferredSize().height);


            monster.setVisible(false);
            bang.setVisible(false);
            cloudy.setVisible(false);
            int userChoice = JOptionPane.showConfirmDialog(null,"You WON! Your score was "+ temp + " hits and " + temp2 + " misses! \nWould you like to play again?","Game Over",JOptionPane.YES_NO_OPTION);
            if (userChoice == JOptionPane.YES_OPTION){
                prompt.setVisible(true);
                tlimit.restart();
                score=0;
                miss=0;
                mode = 0;
                modeinfo.setText("Mode: Full");
                modeinfo.setBounds(10, 40, modeinfo.getPreferredSize().width, modeinfo.getPreferredSize().height);
                tick = (int) (Math.random() * (4 - 2)) + 2;
                bang.setVisible(false);
                cloudy.setVisible(false);
                hidden = false;
                wait = false;
                rX = (int) (Math.random() * (800 - 150)) + 150;
                rY = (int) (Math.random() * (300 - 0)) + 0;
                monster.setBounds(rX, rY, monster.getPreferredSize().width, monster.getPreferredSize().height);

                holes.get(0).setBounds(rmize(250, 180), rmize(150, 50), holesize.width, holesize.height);
                holes.get(1).setBounds(rmize(320, 290), rmize(150, 50), holesize.width, holesize.height);
                holes.get(2).setBounds(rmize(390, 360), rmize(150, 50), holesize.width, holesize.height);
                holes.get(3).setBounds(rmize(465, 430), rmize(150, 50), holesize.width, holesize.height);
                holes.get(4).setBounds(rmize(550, 515), rmize(150, 50), holesize.width, holesize.height);
                holes.get(5).setBounds(rmize(630, 595), rmize(150, 50), holesize.width, holesize.height);

                holes.get(6).setBounds(rmize(250, 180), rmize(350, 200), holesize.width, holesize.height);
                holes.get(7).setBounds(rmize(320, 290), rmize(350, 200), holesize.width, holesize.height);
                holes.get(8).setBounds(rmize(390, 360), rmize(350, 200), holesize.width, holesize.height);
                holes.get(9).setBounds(rmize(465, 430), rmize(350, 200), holesize.width, holesize.height);
                holes.get(10).setBounds(rmize(550, 515), rmize(350, 200), holesize.width, holesize.height);
                holes.get(11).setBounds(rmize(630, 595), rmize(350, 200), holesize.width, holesize.height);
                for (int i = 0; i < quant; i++) {
                    holes.get(i).setVisible(false);
                }
                tlimit.start();
            }
            else{
                System.exit(0);

            }



    }
}

