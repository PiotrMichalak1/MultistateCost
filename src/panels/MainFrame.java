package panels;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final Dimension DEFAULT_MAIN_FRAME_DIMENSION = new Dimension(800,600);
    public MainFrame(){
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setSize(DEFAULT_MAIN_FRAME_DIMENSION);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

}
