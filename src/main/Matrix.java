package  main;

public class Matrix {
    private Double[][] matrix;

    public Matrix () {
        matrix = new Double[4][4];
        createI();  // As default create identity matrix
    }

    public Matrix (Double[][] m) {
        this.matrix = m.clone();
    }

    public Double[][] getMatrix () {
        Double[][] m = matrix.clone();
        return m;
    }

    protected void setMatrixValue (int x, int y, double val) {
        matrix[x][y] = val;
    }

    protected void multiple (Matrix p) {
        Double[][] x = new Double[4][4];
        Double[][] y = p.getMatrix();
        // Matrix multiplication
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                x[i][j] = y[i][0]*matrix[0][j]+y[i][1]*matrix[1][j]
                        +y[i][2] *matrix[2][j]+y[i][3]*matrix[3][j];
            }
        }
        matrix = x.clone();
    }

    protected void createI () {
        // Create identity matrix
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (i==j) matrix[i][j] = 1.0;
                else matrix[i][j] = 0.0;
            }
        }
    }
}