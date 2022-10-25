import com.puzzle.AStar;
import com.puzzle.LDFS;
import com.puzzle.PuzzleObj;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        LDFS puzzle = new LDFS();
        int[][]start = {{1,2,3}, {4,5,8}, {7,0,6}};
        int[][]end = {{1,2,3}, {4,5,6}, {7,8,0}};

        puzzle.setLimit(14);
        puzzle.setStartMatrix(start );
        puzzle.printMatrix("startmatrix");
        puzzle.setSolutionMatrix(end);

        System.out.println(puzzle.solveLDFS(puzzle.getStartMatrix(), 0));
        puzzle.printMatrix("startmatrix");

        puzzle.printSolution();

       /* AStar puzzle = new AStar();
        //{5,3,4}, {8,2,0},{1,7,6}
        //{0,7,8}, {2,5,4},{6,1,3}
        //{5,1,3}, {7,8,6},{4,2,0}
        //{7,1,0}, {8,3,2},{5,6,4}
        //{5,4,1}, {7,0,8},{3,2,6}
        //{8,2,3}, {0,4,7},{5,1,6}
        //{3,2,0}, {7,8,4},{1,5,6}
        int[][]start = {{1,4,3}, {6,0,8},{5,7,2}};
        int[][]end = {{1,2,3}, {4,5,6}, {7,8,0}};

        puzzle.setLimit(2);
        puzzle.setStartMatrix(start);
        puzzle.printMatrix("startmatrix");
        puzzle.setSolutionMatrix(end);
        puzzle.solve();
*/



    }
}