package tools.gridbagelements;

import tools.interfaces.GridBagElement;

import javax.swing.*;
import java.awt.*;

public class GridBagButton implements GridBagElement {
    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        JButton button = new JButton();
        button.setFocusable(false);
        button.setText(text);

        button.addActionListener(e -> {

        });

        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(button, c);
    }
}
