package tools.gridbagelements;

import javax.swing.*;
import java.awt.*;

public class GridBagTextField implements IGridBagElement{
    JTextField textField = new JTextField();

    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        textField.setText(text);
        parent.add(textField, c);
    }
}
