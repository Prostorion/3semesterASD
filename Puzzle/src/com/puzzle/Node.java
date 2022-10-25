package com.puzzle;

import java.util.ArrayList;

public class Node {
    private int [][] position;

    private Node parent;

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getG() {
        return g;
    }

    //number of a step
    private int g;

    //constructors
    public Node(int[][] position, Node parent, int g){
        this.position = position;
        this.parent = parent;
        this.g = g;
    }

    public Node(){
        this.g = -1;
    }

    public boolean solved(int[][]solutionPosition){
        boolean flag = true;
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if(position[i][j] != solutionPosition[i][j]){
                    flag = false;
                }
            }
        }
        return flag;
    }

    public int Score(int [][]solution){
        int score = 0;
        for (int i = 0; i< 3; i++){
            for (int j = 0; j <3; j++){
                for (int k = 0; k < 3; k++){
                    for (int l = 0; l<3; l++){
                        if(solution[k][l] == this.position[i][j] && solution[k][l] !=0){
                            score+= Math.abs(k-i)+ Math.abs(l-j);
                        }
                    }
                }
            }
        }
        //formula is h+g
        //h - manhattan distance
        return score+g;
    }

    public ArrayList<Node> getChildren(){
        ArrayList<Node> childrenArray= new ArrayList<Node>();
        if(PuzzleObj.moveLeft(position)){
            int[][] matrix = new int[3][3];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            Node prev = parent;
            boolean flag  =true;
            while(prev.g!=-1){
                if(prev.solved(matrix)){
                    flag = false;
                }
                prev = prev.getParent();
            }
            if(flag) {
                childrenArray.add(new Node(matrix, this, g + 1));
            }
            PuzzleObj.moveRight(position);
        }
        if(PuzzleObj.moveRight(position)){
            int[][] matrix = new int[3][3];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            Node prev = parent;
            boolean flag  =true;
            while(prev.g!=-1){
                if(prev.solved(matrix)){
                    flag = false;
                }
                prev = prev.getParent();
            }
            if(flag) {
                childrenArray.add(new Node(matrix, this, g + 1));
            }
            PuzzleObj.moveLeft(position);
        }
        if(PuzzleObj.moveUp(position)){
            int[][] matrix = new int[3][3];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            Node prev = parent;
            boolean flag  =true;
            while(prev.g!=-1){
                if(prev.solved(matrix)){
                    flag = false;
                }
                prev = prev.getParent();
            }
            if(flag) {
                childrenArray.add(new Node(matrix, this, g + 1));
            }
            PuzzleObj.moveDown(position);
        }
        if(PuzzleObj.moveDown(position)){
            int[][] matrix = new int[3][3];
            for (int i = 0; i < 3; i++) {
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            Node prev = parent;
            boolean flag  =true;
            while(prev.g!=-1){
                if(prev.solved(matrix)){
                    flag = false;
                }
                prev = prev.getParent();
            }
            if(flag) {
                childrenArray.add(new Node(matrix, this, g + 1));
            }
            PuzzleObj.moveUp(position);
        }
        return childrenArray;
    }

    @Override
    public String toString() {
        String result ="";
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                result+=position[i][j] + " ";
            }
            result+="\n";
        }
        return result;
    }
}
