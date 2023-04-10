package tools;

import settings.Parameters;
import tools.interfaces.GridBagElement;

import javax.swing.*;
import java.awt.*;

public class GridBagLabel extends JLabel implements GridBagElement {
    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        JLabel label = new JLabel();
        label.setText(text);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(label, c);
    }

    public void putInGrid(JComponent parent, String text, int bagX, int bagY, boolean header) {
        GridBagConstraints c = new GridBagConstraints();
        JLabel label = new JLabel();
        label.setText(text);
        if (header) {
            c.gridwidth = Parameters.getInstance().NUM_OF_STATES;
        }
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(label, c);
    }
}