import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Graph world = new Graph(200);

        GreedySearch search = new GreedySearch(world);
        int LMin = search.getMinLength();
        System.out.println("Lmin " + LMin);
        //-------------------------------------------------------
        double[][] pheromone = new double[world.getMatrix().length][world.getMatrix().length];
        for (int i = 0; i < pheromone.length; i++) {
            for (int j = 0; j < pheromone.length; j++) {
                pheromone[i][j] = 0.0001;
            }
        }
        ACO algorithm = new ACO(world.getMatrix(), pheromone);
        algorithm.setLMin(LMin);

        algorithm.solveProblem();
    }
}