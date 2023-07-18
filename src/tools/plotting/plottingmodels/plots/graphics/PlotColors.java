package tools.plotting.plottingmodels.plots.graphics;

import settings.InitialSettings;

import java.awt.*;


public class PlotColors {
    private static int colorNumber =0;

    public static void resetColor(){
        colorNumber =0;
    }

    public static Color next(){
        colorNumber++;
        return switch (colorNumber){
            case 1 -> new Color(19, 158, 254);
            case 2 -> new Color(253, 104, 42);
            case 3 -> new Color(234, 165, 6);
            case 4 -> new Color(137, 68, 152);
            case 5 -> new Color(118, 171, 49);
            default -> randomColor(colorNumber);
        };
    }
    public static Color nextLightColor(){
        colorNumber++;
        return switch (colorNumber){
            case 1 -> new Color(252, 183, 150);
            case 2 -> new Color(235, 221, 152);
            case 3 -> new Color(172, 208, 237);
            case 4 -> new Color(192, 227, 152);
            case 5 -> new Color(201, 139, 219);
            default -> randomColor(colorNumber);
        };
    }
    public static Color nextStateColor(){
        double middleColorNumber = InitialSettings.DEFAULT_NUM_OF_STATES/2.0;
        int r = colorNumber <= middleColorNumber ? (int) (255 * (colorNumber / middleColorNumber)) : 255;
        int g = colorNumber <= middleColorNumber ? 255 : (int) (255 * (2 - colorNumber / middleColorNumber));
        colorNumber++;
        return new Color(r,g,0);
    }

    private static Color randomColor(int colorNumber){

        int colorBrightness = (65*colorNumber)%256+ (51*colorNumber)%256+ (95*colorNumber)%256;
        if (colorBrightness>535){
            colorNumber++;
            return randomColor(colorNumber);
        }else {
            return new Color((65*colorNumber)%256,
                    (51*colorNumber)%256,
                    (95*colorNumber)%256);
        }
    }

    public static Color nextLayered() {
        colorNumber++;
        return switch (colorNumber){
            case 1 -> new Color(19, 158, 254);
            case 2 -> new Color(234, 165, 6);
            case 3 -> new Color(253, 104, 42);
            case 4 -> new Color(137, 68, 152);
            case 5 -> new Color(118, 171, 49);
            default -> randomColor(colorNumber);
        };
    }
    public static void setColorToBlack(){
        colorNumber = -1;
    }
}
