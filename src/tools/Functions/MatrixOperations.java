package tools.Functions;

public class MatrixOperations {

    public static void fillRow(double[][][] matrix, double valueToFillIn, int numOfRow) {
        int layers = matrix[0][0].length;
        int cycles = matrix[0].length;
        int states = matrix.length;

        for (int layer = 0; layer < layers; layer++) {
            for (int cycle = 0; cycle < cycles; cycle++) {
                matrix[numOfRow - 1][cycle][layer] = valueToFillIn;
            }
        }
    }

    public static double sum(double[][] array) {
        double sum1 = 0;
        for (double[] arr : array)
            for (double i : arr)
                sum1 += i;

        return sum1;
    }

    public static double sum(double[] vector) {
        double sum1 = 0;
        for (double item : vector)
                sum1 += item;

        return sum1;
    }

    public static void multiplyMatrixRowsByVector(double[][] matrix, double[] vector) {
        int nRow = vector.length;

        if (nRow == matrix.length) {
            int nCol = matrix[0].length;
            for (int row = 0; row < nRow; row++) {
                for (int col = 0; col < nCol; col++) {
                    matrix[row][col] = matrix[row][col] * vector[row];
                }
            }
        } else
            throw new IllegalArgumentException("Number of rows is " + matrix.length + " number of vector elements is: " + vector.length + ". these numbers should be equal");

    }

    public static <T extends Number> double[] concatenateVectors(T[]... vectors) {
        int length = 0;
        for (T[] vector : vectors) {
            length += vector.length;
        }
        double[] result = new double[length];
        int index = 0;
        for (T[] vector : vectors) {
            for (T number : vector) {
                result[index] = number.doubleValue();
                index++;
            }
        }
        return result;
    }

    //concatenate given amount of vectors of double values
    public static double[] concatenateVectors(double[]... vectors){
        int length = 0;
        for (double[] vector : vectors) {
            length += vector.length;
        }
        double[] result = new double[length];
        int index = 0;
        for (double[] vector : vectors){
            for (double val : vector) {
                result[index] = val;
                index++;
            }
        }
        return result;
    }

    //concatenate given amount of vectors of int values
    public static int[] concatenateVectors(int[]... vectors){
        int length = 0;
        for (int[] vector : vectors) {
            length += vector.length;
        }
        int[] result = new int[length];
        int index = 0;
        for (int[] vector : vectors){
            for (int val : vector) {
                result[index] = val;
                index++;
            }
        }
        return result;
    }

    public static int[] getReversedArray(int[] array) {
        int[] reversedArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }

        return reversedArray;
    }

    public static double[] getReversedArray(double[] array) {
        double[] reversedArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i];
        }

        return reversedArray;
    }

}
