package tools.Functions;

import settings.Parameters;

public class TestingValues {
    public double[] testDomain;
    public double[] testCodomain;

    public double[] testDomain2;
    public double[] testCodomain2;

    Parameters parameters;

    public TestingValues() {
        parameters = Parameters.getInstance();
        initializeTestFunction();
    }

    public void initializeTestFunction() {
        this.testDomain = new double[parameters.getMaxInterval() - parameters.getMinInterval() + 1];
        this.testCodomain = new double[testDomain.length];
        for (int i = 0; i < testDomain.length; i++) {
            testDomain[i] = parameters.getMinInterval() + i;
            testCodomain[i] = testDomain[i];
        }


        this.testDomain2 = new double[100];
        this.testCodomain2 = new double[100];
        for (int i = 0; i < 100; i++) {
            testDomain2[i] = -50.0 + i;
        }
        for (int j = 0; j < testDomain2.length; j++) {
            double x = testDomain2[j];
            //testCodomain[j] = Math.pow(x, 3) + 2 * Math.pow(x, 2)+ 7 * x - 1;
            //testCodomain[j] = Math.pow(-1,j);
            testCodomain2[j] = x * (-0.5);

        }

    }
}
