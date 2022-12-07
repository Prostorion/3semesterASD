import java.util.Arrays;
import java.util.Random;

public class Graph {
    int[][] distancesMatrix;

    static final int MAX_ROADS_IN_CITY = 10;
    static final int MAX_DISTANCE = 150;
    static final int MIN_DISTANCE = 5;

    public Graph(int size) {
        this.distancesMatrix = new int[size][size];
        fillMatrix(size);

    }

    private void fillMatrix(int size) {

        //put at least one road to each city
        putOneToRow();
        while(!canExist()){
            this.distancesMatrix = new int[size][size];
            putOneToRow();
        }

        Random rand = new Random();
        for (int i = 0; i < distancesMatrix.length; i++) {
            for (int j = i + 1; j < distancesMatrix.length; j++) {
                if (rand.nextDouble() < (double)MAX_ROADS_IN_CITY/size && canPut(i, j)) {
                    int distance = rand.nextInt(MAX_DISTANCE-MIN_DISTANCE+1)+MIN_DISTANCE;
                    distancesMatrix[i][j] = distance;
                    distancesMatrix[j][i] = distance;
                }
            }
        }
    }

    public boolean canExist() {
        for (int i = 0; i < distancesMatrix.length; i++) {
            int roads =0;
            for (int j = 0; j < distancesMatrix.length; j++) {
                if (distancesMatrix[i][j]!=0){
                    roads++;
                }
            }
            if (roads >MAX_ROADS_IN_CITY || roads == 0){
                return false;
            }
        }
        return true;
    }

    private void putOneToRow() {
        Random rand = new Random();
        for (int i = 0; i < distancesMatrix.length;i++ ) {
            int column = rand.nextInt(distancesMatrix.length);
            while(column==i){
                column = rand.nextInt(distancesMatrix.length);
            }
            int distance = rand.nextInt(MAX_DISTANCE-MIN_DISTANCE+1)+MIN_DISTANCE;
            distancesMatrix[i][column] = distance;
            distancesMatrix[column][i] = distance;
        }
    }

    private boolean canPut(int row, int column) {
        if (row == column) return false;
        int roadsInColumn = 0;
        int roadsInRow = 0;
        for (int i = 0; i < distancesMatrix.length; i++) {
            if (distancesMatrix[row][i] != 0) {
                roadsInRow++;
            }
            if (distancesMatrix[i][column] != 0) {
                roadsInColumn++;
            }
        }
        return roadsInRow < Graph.MAX_ROADS_IN_CITY && roadsInColumn < Graph.MAX_ROADS_IN_CITY;
    }

    public void printDistancesMatrix(){
        for (var i:distancesMatrix) {
            System.out.println(Arrays.toString(i));
        }
    }
}