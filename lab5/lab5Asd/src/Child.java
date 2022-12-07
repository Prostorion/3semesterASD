import java.util.ArrayList;

public class Child {
    private ArrayList<Integer> genes;

    public Child(ArrayList<Integer> genes) {
        this.genes = genes;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public boolean canExist(int[][] matrix){
        for (int i = 0; i < genes.size()-1; i++) {
            if (matrix[genes.get(i)][genes.get(i+1)]==0){
                return false;
            }
        }
        return true;
    }

    public int countDistance(int[][] matrix){
        int distance = 0;
        for (int i = 0; i < genes.size()-1; i++) {
            distance+=matrix[genes.get(i)][genes.get(i+1)];
        }
        return distance;
    }
}
