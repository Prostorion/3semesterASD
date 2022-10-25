package com.puzzle;

public class LDFS extends PuzzleObj{

    //limit for LDFS
    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    //solving

    public boolean solveLDFS(int[][] old_matrix, int depth) {
        //print matrix
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                System.out.print(old_matrix[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        //
        int[][] matrix = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(old_matrix[i], 0, matrix[i], 0, 3);
        }
        if (!areSame(matrix, solutionMatrix) && depth < limit) {

            if (this.moveLeft(matrix)) {
                if (solveLDFS(matrix, depth + 1)) {
                    solution.add(old_matrix);
                    return true;
                }
                this.moveRight(matrix);
            }

            if (this.moveUp(matrix)) {
                if (solveLDFS(matrix, depth + 1)) {
                    solution.add(old_matrix);
                    return true;
                }
                this.moveDown(matrix);
            }
            if (this.moveRight(matrix)) {
                if (solveLDFS(matrix, depth + 1)) {
                    solution.add(old_matrix);
                    return true;
                }
                this.moveLeft(matrix);
            }
            if (this.moveDown(matrix)) {
                if (solveLDFS(matrix, depth + 1)) {
                    solution.add(old_matrix);
                    return true;
                }
                this.moveUp(matrix);
            }
        }else if(areSame(matrix, solutionMatrix)){
            solution.add(matrix);
            return true;
        }
        else {
            System.out.print("Limit\n");
            return false;
        }
        //solution.add(matrix);
        return false;
    }

    public void printSolution(){
        System.out.println("Solution:");
        for (int k =0; k<solution.size(); k++ ){
            System.out.println(k);
            for(int i = 0; i <3; i++){

                for(int j = 0; j <3; j++){
                    System.out.print( solution.get(k)[i][j]+" ");
                }
                System.out.print("\n");
            }
        }
    }


    //methods to print matrix
    public void printMatrix(String name){
        if("solutionmatrix".equals(name.toLowerCase())){
            printSolutionMatrix();
        }
        else if("startmatrix".equals(name.toLowerCase())){
            printStartMatrix();
        }

    }

    private void printSolutionMatrix(){
        System.out.println("Solution matrix:");
        for(int i = 0; i <3; i++){
            for(int j = 0; j <3; j++){
                System.out.print( solutionMatrix[i][j]+" ");
            }
            System.out.print("\n");
        }
    }

    private void printStartMatrix(){
        System.out.println("Start matrix:");
        for(int i = 0; i <3; i++){
            for(int j = 0; j <3; j++){
                System.out.print( startMatrix[i][j]+" ");
            }
            System.out.print("\n");
        }
    }


}
