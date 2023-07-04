package tools;

import java.util.Arrays;

public class MatrixOperations {

    public static void fillRow(double[][][] matrix, double valueToFillIn, int numOfRow) {
        int layers = matrix[0][0].length;
        int cycles = matrix[0].length;
        int states = matrix.length;

        for (int layer = 0; layer < layers; layer++) {
            for (int cycle = 0; cycle < cycles; cycle++) {
                matrix[numOfRow-1][cycle][layer] = valueToFillIn;
            }
        }
    }

    public static double sum(double[][]array){
        double sum1 = 0;
        for (double[] arr : array)
            for(double i: arr)
                sum1+=i;

        return sum1;
    }

    public static void multiplyMatrixRowsByVector(double[][] matrix, int[] vector){
        int nRow = vector.length;
        int nCol = matrix[0].length;
        for (int row = 0; row < nRow; row++) {
            for (int col = 0; col < nCol; col++) {
                matrix[row][col]=matrix[row][col]*vector[row];
            }
        }
    }

}
