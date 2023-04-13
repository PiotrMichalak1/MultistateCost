package settings;

public class InitialSettings {

    public static final int DEFAULT_NUM_OF_STATES = 4;

    //Repair Defaults
    public static final int DEFAULT_REPAIR_COST_STEP = 10;
    public static final int DEFAULT_REPAIR_DURATION_STEP = 1;


    //Emergency Repair Defaults
    public static final boolean DEFAULT_EMERGENCY_REPAIR_CHK = false;
    public static final int DEFAULT_STATE_DROPS_TO = 3;
    public static final int DEFAULT_NEXT_INSPECTION_IN = 30;
    public static final int DEFAULT_EMERGENCY_COST = 25;
    public static final int DEFAULT_EMERGENCY_DELAY = 5;

    //Simulation defaults

    public static final int DEFAULT_PROD_CYCLES_NUM = 1000;
    public static final int DEFAULT_MIN_INTERVAL = 10;
    public static final int DEFAULT_MAX_INTERVAL = 200;
    public static final int DEFAULT_STEP = 1;
    public static final boolean DEFAULT_HOLD_THE_DATA = false;
    public static final boolean DEFAULT_RUN_MULTIPLE_TIMES = false;

    public static final int DEFAULT_RUN_MULTIPLE_TIMES_NUM = 5;

    //Plot Defaults
    public static final int DEFAULT_PLOT_MARGIN = 25;

    public static final int[] DEFAULT_X_AXIS_RANGE = {0,100};
    public static final int[] DEFAULT_Y_AXIS_RANGE = {0,10};

    public static final int SMALL_GRID_SPACING = 14;


    //Types
    public static final int REPAIR_COST = 1;
    public static final int REPAIR_DURATION = 2;
    public static final int OTHER_PROPERTIES = 3;
    public static final int STATIC_COST = 4;
    public static final int WEIBULL_SCALE = 5;
    public static final int WEIBULL_SHAPE = 6;
    public static final int INSPECTION_COST = 7;
    public static final int INSPECTION_OBJECTIVES = 8;
    public static final int EMERGENCY_REPAIR_CHK = 9;
    public static final int STATE_DROPS_TO = 10;
    public static final int NEXT_INSPECTION_IN = 11;
    public static final int EMERGENCY_COST = 12;
    public static final int EMERGENCY_DELAY = 13;
    public static final int PRODUCTION_CYCLES = 14;
    public static final int MIN_INTERVAL = 15;
    public static final int MAX_INTERVAL = 16;
    public static final int STEP = 17;
    public static final int HOLD_THE_DATA = 18;
    public static final int RUN_MULTIPLE_TIMES = 19;







}
