package tools;

import settings.InitialSettings;
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

    public void putInGrid(JComponent parent, String text, int bagX, int bagY, int headerType) {
        GridBagConstraints c = new GridBagConstraints();
        JLabel label = new JLabel();
        label.setText(text);
        if (headerType == InitialSettings.OTHER_PROPERTIES) {
            c.gridwidth = InitialSettings.DEFAULT_NUM_OF_STATES +1;
        }else c.gridwidth = InitialSettings.DEFAULT_NUM_OF_STATES;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(label, c);
    }
    public void putEmptyInGrid(JComponent parent, int bagX,int bagY){
        GridBagConstraints c = new GridBagConstraints();
        Component emptyComponent= Box.createHorizontalStrut(10);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(emptyComponent,c);
    }
}
