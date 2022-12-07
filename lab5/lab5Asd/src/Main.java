public class Main {
    public static void main(String[] args) {

        Graph world = new Graph(300);
        world.printDistancesMatrix();
        System.out.println("Is in each city no more than "+ Graph.MAX_ROADS_IN_CITY + " roads and at least 1: " + world.canExist());

        GeneticAlgorithm solve= new GeneticAlgorithm(world.distancesMatrix, 0, 299);

        String summary = "";
        for (int j = 1; j < 4; j++) {
            int iterations =1;
            int averageDistanceFirst = 0;
            for (int i = 0; i < iterations; i++) {
                averageDistanceFirst += solve.solve(j,1, 1);
            }
            averageDistanceFirst /=iterations;

            int averageDistanceSecond = 0;
            for (int i = 0; i < iterations; i++) {
                averageDistanceSecond += solve.solve(j,1, 2);
            }
            averageDistanceSecond /=iterations;

            int averageDistanceThird = 0;
            for (int i = 0; i < iterations; i++) {
                averageDistanceThird += solve.solve(j,2, 1);
            }
            averageDistanceThird /=iterations;

            int averageDistanceFourth = 0;
            for (int i = 0; i < iterations; i++) {
                averageDistanceFourth += solve.solve(j,2, 2);
            }
            averageDistanceFourth /=iterations;

            System.out.println("Average distance in first variation("+j+",1,1): "+ averageDistanceFirst);
            summary+="Average distance in first variation("+j+",1,1): "+ averageDistanceFirst+"\n";
            System.out.println("Average distance in second variation("+j+",1,2): "+ averageDistanceSecond);
            summary+="Average distance in second variation("+j+",1,2): "+ averageDistanceSecond+"\n";
            System.out.println("Average distance in third variation("+j+",2,1): "+ averageDistanceThird);
            summary+="Average distance in third variation("+j+",2,1): "+ averageDistanceThird+"\n";
            System.out.println("Average distance in fourth variation("+j+",2,2): "+ averageDistanceFourth);
            summary+="Average distance in fourth variation("+j+",2,2): "+ averageDistanceFourth+"\n";
        }
        System.out.println();
        System.out.println(summary);





    }
}