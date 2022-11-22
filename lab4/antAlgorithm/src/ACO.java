import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ACO {
    static final double ALPHA = 3;
    static final double BETA = 2;
    static final double RO = 0.7;
    int LMin;
    int LBest; // = Integer.MAX_VALUE;
    int[][] distancesMatrix;
    double[][] pheromoneMatrix;

    public ACO(int[][] distancesMatrix, double[][] pheromoneMatrix) {
        this.distancesMatrix = distancesMatrix;
        this.pheromoneMatrix = pheromoneMatrix;
    }

    public void setLMin(int LMin) {
        this.LMin = LMin;
    }

    static final int NUMBER_OF_ANTS = 45;
    static final int NUMBER_OF_ELITE_ANTS = 10;
    static final int tMax = 1000;

    public void solveProblem() {
        Ant[] ants = setAnts();
        int bestLength = Integer.MAX_VALUE;
        int[] bestWay = new int[distancesMatrix.length];
        for (int t = 0; t < tMax; t++) {
            ArrayList<int[]> ways = new ArrayList<>();

            for (int i = 0; i < ants.length; i++) {
                //visit all vertices
                int currentPosition = ants[i].getPosition();
                ArrayList<Integer> visitedVertices = new ArrayList<Integer>();
                while (visitedVertices.size() < distancesMatrix.length) {
                    visitedVertices.add(currentPosition);
                    currentPosition = calculateNewPosition(currentPosition, visitedVertices);
                }
                //add way to ways
                int[] array = new int[visitedVertices.size()];
                for (int j = 0; j < array.length; j++) {
                    array[j] = visitedVertices.get(j);
                }
                ways.add(array);
            }
            for (var way : ways) {
                int length = countLength(way);
                if (length < bestLength){
                    bestWay = way;
                    bestLength = length;
                }
            }

            pheromoneMatrix = rewritePheromoneMatrix(ways);
            if (t%20 == 0){
                //System.out.println(Arrays.toString(bestWay));
                System.out.print(bestLength+ " ");
            }
        }

        //sout lBest tBest
        System.out.println();
        System.out.println("Best way vertices"+Arrays.toString(bestWay));
        System.out.println("It's length - "+ bestLength);
    }


    private int countLength(int[]way){
        int length = 0;
        for (int i = 0; i < way.length-1; i++) {
            length += distancesMatrix[way[i]][way[i+1]];
        }
        length+=distancesMatrix[distancesMatrix.length-1][0];
        return length;
    }
    private double[][] rewritePheromoneMatrix(ArrayList<int[]> ways) {
        for (int i = 0; i < pheromoneMatrix.length; i++) {
            for (int j = 0; j < pheromoneMatrix.length; j++) {
                pheromoneMatrix[i][j]*= (1-RO);
            }
        }
        for (var way: ways){
            int deltaPheromone = LMin/countLength(way);
            for (int i = 0; i < way.length-1; i++) {
                pheromoneMatrix[way[i]][way[i+1]] += deltaPheromone;
                pheromoneMatrix[way[i+1]][way[i]] += deltaPheromone;
                if(i<=NUMBER_OF_ELITE_ANTS){
                    pheromoneMatrix[way[i]][way[i+1]] += deltaPheromone;
                    pheromoneMatrix[way[i+1]][way[i]] += deltaPheromone;
                }
            }
            pheromoneMatrix[way[way.length-1]][way[0]] += deltaPheromone;
            pheromoneMatrix[way[0]][way[way.length-1]] += deltaPheromone;
        }
        return pheromoneMatrix;
    }


    private int calculateNewPosition(int currentPosition, ArrayList<Integer> visitedVertices) {
        double[] probabilities = new double[distancesMatrix.length];
        //calculate probabilities
        for (int i = 0; i < probabilities.length; i++) {
            if (visitedVertices.contains(i)) {
                probabilities[i] = 0;
            } else {
                probabilities[i] = Math.pow(pheromoneMatrix[currentPosition][i], ALPHA) * (Math.pow((1.0 / distancesMatrix[currentPosition][i]), BETA));
            }
        }
        Random rand = new Random();
        double sum = 0;
        for (var i : probabilities) {
            sum += i;
        }
        double nextPosProb = rand.nextDouble() * sum;
        double sumOfLittleProb = 0;
        int result = findMax(probabilities);
        while (sumOfLittleProb < nextPosProb) {
            result = findMax(probabilities);
            sumOfLittleProb += probabilities[result];
            probabilities[result] = 0;
        }
        return result;
    }

    private int findMax(double[] probabilities) {
        double max = Integer.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < probabilities.length; i++) {
            if (probabilities[i] > max && probabilities[i]!=0) {
                max = probabilities[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }


    /**
     * method to set initial unique vertex for each ant
     *
     * @return array of ants, elite ants at start
     */
    public Ant[] setAnts() {
        Ant[] ants = new Ant[NUMBER_OF_ANTS];
        int countOfEliteAnts = 0;
        ArrayList<Integer> positions = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < ants.length; ) {

            int pos = rand.nextInt(distancesMatrix.length);
            if (!positions.contains(pos)) {
                Ant ant;
                if (countOfEliteAnts < NUMBER_OF_ELITE_ANTS) {
                    ant = new Ant(true, pos);
                    countOfEliteAnts++;
                } else {
                    ant = new Ant(false, pos);
                }
                positions.add(pos);
                ants[i] = ant;
                i++;
            }
        }
        return ants;
    }

}
