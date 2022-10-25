package com.puzzle;

import java.util.ArrayList;

public class NodeSecond {
    //fields
    private NodeSecond parent;
    private int[][] position;
    private ArrayList<NodeSecond> children;
    private int score;
    private int g;

    //Getters and Setters
    //SOME ARE UNUSUAL
    public NodeSecond getParent() {
        return parent;
    }

    public void setParent(NodeSecond parent) {
        this.parent = parent;
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public ArrayList<NodeSecond> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<NodeSecond> children) {
        this.children = children;
    }

    public int getScore(int[][]solution) {
        setScore(solution);
        return score+g;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    //only for first parent
    public NodeSecond(int g){
        this.g = g;
    }

    //constructor
    public NodeSecond(int[][]position, NodeSecond parent, int g){
        this.position = position;
        this.parent = parent;
        this.g = g;
        this.children = new ArrayList<>();
    }

    //creating child-nodes (only if it is possible)
    //by moving zero to different sides
    public void doChildren(){
        //moving left
        if(PuzzleObj.moveLeft(position)){
            NodeSecond prev = this.parent;
            boolean unique =true;
            while(prev.getG()!=-1){
                if(PuzzleObj.areSame(prev.getPosition(), position)){
                    unique = false;
                }
                prev = prev.getParent();
            }
            int[][]matrix = new int[3][3];
            for(int i =0; i<3; i++){
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            if(unique){
                children.add(new NodeSecond(matrix, this, g+1));
            }
            PuzzleObj.moveRight(position);
        }

        //moving right
        if(PuzzleObj.moveRight(position)){
            NodeSecond prev = this.parent;
            boolean unique =true;
            while(prev.getG()!=-1){
                if(PuzzleObj.areSame(prev.getPosition(), position)){
                    unique = false;
                }
                prev = prev.getParent();
            }
            int[][]matrix = new int[3][3];
            for(int i =0; i<3; i++){
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            if(unique){
                children.add(new NodeSecond(matrix, this, g+1));
            }
            PuzzleObj.moveLeft(position);
        }

        //move up
        if(PuzzleObj.moveUp(position)){
            NodeSecond prev = this.parent;
            boolean unique =true;
            while(prev.getG()!=-1){
                if(PuzzleObj.areSame(prev.getPosition(), position)){
                    unique = false;
                }
                prev = prev.getParent();
            }
            int[][]matrix = new int[3][3];
            for(int i =0; i<3; i++){
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            if(unique){
                children.add(new NodeSecond(matrix, this, g+1));
            }
            PuzzleObj.moveDown(position);
        }

        //move down
        if(PuzzleObj.moveDown(position)){
            NodeSecond prev = this.parent;
            boolean unique =true;
            while(prev.getG()!=-1){
                if(PuzzleObj.areSame(prev.getPosition(), position)){
                    unique = false;
                }
                prev = prev.getParent();
            }
            int[][]matrix = new int[3][3];
            for(int i =0; i<3; i++){
                System.arraycopy(position[i], 0, matrix[i], 0, 3);
            }
            if(unique){
                children.add(new NodeSecond(matrix, this, g+1));
            }
            PuzzleObj.moveUp(position);
        }
    }


    //score calculator (manhattan distance)
    private void setScore(int [][]solution){
        this.score = 0;
        for (int i = 0; i< 3; i++){
            for (int j = 0; j <3; j++){
                for (int k = 0; k < 3; k++){
                    for (int l = 0; l<3; l++){
                        if(solution[k][l] == this.position[i][j] && solution[k][l] !=0){
                            this.score+= Math.abs(k-i)+ Math.abs(l-j);
                        }
                    }
                }
            }
        }
    }

    //just to string method
    @Override
    public String toString() {
        String result ="\n";
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                result+=position[i][j] + " ";
            }
            result+="\n";
        }
        return result;
    }

    //show are solution and node's position similar
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

}
