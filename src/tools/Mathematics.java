package tools;

import java.awt.*;

public class Mathematics {
    public static double roundDownToTheNearestMultiple(double number, double multiple) {
        multiple = Math.abs(multiple);
        double roundedNumber = 0;
        if (number > 0) {
            while (roundedNumber + multiple < number) {
                roundedNumber += multiple;
            }
        } else {
            while (roundedNumber > number) {
                roundedNumber -= multiple;
            }
        }
        return roundedNumber;
    }

    public static double roundUpToTheNearestMultiple(double number, double multiple) {
        multiple = Math.abs(multiple);
        double roundedNumber = 0;
        if (number > 0) {
            while (roundedNumber < number) {
                roundedNumber += multiple;
            }
        } else {
            while (roundedNumber - multiple > number) {
                roundedNumber -= multiple;
            }
        }
        return roundedNumber;
    }

    public static double roundUpToTheNearestPowerOf(double number, double poweredNumber) {
            double power = Math.ceil(log(number, poweredNumber));
            return Math.pow(poweredNumber, power);
    }

    public static double log(double argument, double base) {
        return Math.log(argument) / Math.log(base);
    }

    public static int manhattan(Point p1, Point p2){
        return Math.abs(p1.x-p2.x)+Math.abs(p1.y-p2.y);
    }

}
