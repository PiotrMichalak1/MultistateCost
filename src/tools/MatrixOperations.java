package tools;

import java.util.Arrays;

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


}
