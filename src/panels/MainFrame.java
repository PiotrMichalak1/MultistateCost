package panels;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setSize(800,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
}
