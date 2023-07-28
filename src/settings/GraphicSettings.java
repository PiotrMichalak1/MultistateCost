package settings;

import java.awt.*;

public class GraphicSettings {
    public static final int PLOT_POINT_THICKNESS = 6;
    //colors
    public static final Color INVALID_VALUE_COLOR = new Color(234, 116, 116);
    public static final Color POI_COLOR = new Color(233, 86, 86);
    public static final Color MAIN_GRAPH_COLOR = new Color(19, 157, 253);

    public static final Color TITLE_COLOR = new Color(21, 20, 20, 205);

    public static final Color MARGIN_COLOR = new Color(236, 236, 237);

    public static final double DEFAULT_SCALE_UNIT_X = 2.0;
    public static final double DEFAULT_SCALE_UNIT_Y = 1.0;
    public static final double DEFAULT_SCALE_MULTIPLIER = 1.0;
    //Plot Defaults
    public static final int DEFAULT_PLOT_MARGIN = 70;

    public static final int POINT_OF_INTEREST_MARGIN_X = 15;
    public static final int POINT_OF_INTEREST_MARGIN_Y = 5;

    public static final int POINT_OF_INTEREST_STRING_SPACING = 15;
    public static final Point POINT_OF_INTEREST_OFFSET = new Point(10,19);

    public static final int POINT_OF_INTEREST_VISIBILITY_THRESHOLD = 40;

    public static final float FUNCTION_THICKNESS = 2.0f;
}
