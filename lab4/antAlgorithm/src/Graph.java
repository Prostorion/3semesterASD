import java.util.Random;

public class Graph {
    private int[][] matrix;

    public static final int MIN_DISTANCE = 1;
    public static final int MAX_DISTANCE = 40;

    public Graph(int numberOfVertexes) {
        this.matrix = new int[numberOfVertexes][numberOfVertexes];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[i].length; j++) {
                if (j == i) {
                    matrix[i][j] = 0;
                } else {
                    matrix[i][j] = getRandomDistance();
                    matrix[j][i] = matrix[i][j];
                }

            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    private static int getRandomDistance() {
        Random random = new Random();
        return random.nextInt(MAX_DISTANCE - MIN_DISTANCE + 1) + MIN_DISTANCE;
    }
}
