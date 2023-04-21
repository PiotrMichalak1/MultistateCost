package tools;

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


}
