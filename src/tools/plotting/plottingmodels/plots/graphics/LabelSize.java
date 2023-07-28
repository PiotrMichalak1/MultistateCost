package tools.plotting.plottingmodels.plots.graphics;

import java.awt.*;

public class LabelSize {
    public static final float PLOT_TITLE = 30.0f;
    public static final float AXIS_LABEL = 20.0f;
    public static final float STANDARD = 12.0f;

    public static final void setFontSize(Graphics2D g2, float newFontSize) {
        Font oldFont = g2.getFont();
        Font newFont = oldFont.deriveFont(newFontSize);
        g2.setFont(newFont);
    }
}
