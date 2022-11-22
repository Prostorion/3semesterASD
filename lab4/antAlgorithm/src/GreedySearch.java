import java.util.ArrayList;

public class GreedySearch {
    Graph graph;

    public GreedySearch(Graph graph) {
        this.graph = graph;
    }

    public int getMinLength() {
        ArrayList<Integer> revisedVertices = new ArrayList<>();
        int result = 0;
        int currentVertex = 0;
        while (revisedVertices.size() < graph.getMatrix().length - 1) {
            int newVertex = findMin(graph.getMatrix()[currentVertex], revisedVertices);
            result += graph.getMatrix()[newVertex][currentVertex];
            revisedVertices.add(currentVertex);
            currentVertex = newVertex;
        }
        result += graph.getMatrix()[currentVertex][0];
        revisedVertices.add(currentVertex);
        System.out.println(revisedVertices);
        return result;
    }

    private int findMin(int[] distances, ArrayList<Integer> revisedVertices) {
        int min = Graph.MAX_DISTANCE + 1;
        int minIndex = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < min && !revisedVertices.contains(i) && distances[i] > 0) {
                minIndex = i;
                min = distances[i];
            }
        }
        return minIndex;
    }
}
