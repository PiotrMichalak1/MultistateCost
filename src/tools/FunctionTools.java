package tools;

public class FunctionTools {
    public static double getSmallestValueOfFunction(double[] functionValues) {
        double min;
        if (functionValues.length > 0) {
            min = functionValues[0];
            for (int i = 1; i < functionValues.length; i++) {
                if (functionValues[i] < min) {
                    min = functionValues[i];
                }
            }
        } else throw new RuntimeException("Length of function values is 0");

        return min;
    }public static double getBiggestValueOfFunction(double[] functionValues) {
        double max;
        if (functionValues.length > 0) {
            max = functionValues[0];
            for (int i = 1; i < functionValues.length; i++) {
                if (functionValues[i] > max) {
                    max = functionValues[i];
                }
            }
        } else throw new RuntimeException("Length of function values is 0");

        return max;
    }
}
