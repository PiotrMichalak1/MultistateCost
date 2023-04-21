package tools;

public class TestingValues {
    public double[] testDomain;
    public double[] testCodomain;

    public double[] testDomain2;
    public double[] testCodomain2;

    public TestingValues() {
        initializeTestFunction();
    }

    private void initializeTestFunction() {
        this.testDomain = new double[100];
        this.testCodomain = new double[100];
        for (int i = 0; i < 100; i++) {
            testDomain[i] = -50.0 + i ;
        }
        for (int j = 0; j < testDomain.length; j++) {
            double x = testDomain[j];
            //testCodomain[j] = Math.pow(x, 3) + 2 * Math.pow(x, 2)+ 7 * x - 1;
            //testCodomain[j] = Math.pow(-1,j);
            testCodomain[j] = x*1.2;

        }

        this.testDomain2 = new double[100];
        this.testCodomain2 = new double[100];
        for (int i = 0; i < 100; i++) {
            testDomain2[i] = -50.0 + i ;
        }
        for (int j = 0; j < testDomain2.length; j++) {
            double x = testDomain2[j];
            //testCodomain[j] = Math.pow(x, 3) + 2 * Math.pow(x, 2)+ 7 * x - 1;
            //testCodomain[j] = Math.pow(-1,j);
            testCodomain2[j] = x*(-0.5);

        }

    }
}
