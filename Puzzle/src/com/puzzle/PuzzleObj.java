package com.puzzle;

import java.util.ArrayList;

public class PuzzleObj {


    //Matrix with which you start
    int[][] startMatrix = new int[3][3];

    //Solved matrix
    protected int[][] solutionMatrix =new int[3][3];


    //solution
    protected ArrayList<int[][]> solution= new ArrayList<>();





    //Getters and Setters
    public int[][] getStartMatrix() {
        return startMatrix;
    }

    public void setStartMatrix(int[][] startMatrix) {
        this.startMatrix = startMatrix;
    }

    public int[][] getSolutionMatrix() {
        return solutionMatrix;
    }

    public void setSolutionMatrix(int[][] solutionMatrix) {
        this.solutionMatrix = solutionMatrix;
    }



    public ArrayList<int[][]> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<int[][]> solution) {
        this.solution = solution;
    }

    //move empty cell
    //moving methods returns false,
    //when 0 can't be replaced in their directions

    //move left
    public static boolean moveLeft(int[][] currentMatrix){
        int zeroX =-1, zeroY = -1;

        for(int i = 0; i <3; i++){
            for(int j = 0; j <3; j++) {
                if (currentMatrix[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;

                }
            }
        }
        if(zeroX-1<0){

            return false;
        }
        else{
            int curr = currentMatrix[zeroY][zeroX];
            currentMatrix[zeroY][zeroX] = currentMatrix[zeroY][zeroX-1];
            currentMatrix[zeroY][zeroX-1]= curr;
            return true;
        }
    }

    //move right
    public static boolean moveRight( int[][] currentMatrix){
        int zeroX =-1, zeroY = -1;

        for(int i = 0; i <3; i++){
            for(int j = 0; j <3; j++) {
                if (currentMatrix[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;

                }
            }
        }
        if(zeroX+1>2){

            return false;
        }
        else{
            int curr = currentMatrix[zeroY][zeroX];
            currentMatrix[zeroY][zeroX] = currentMatrix[zeroY][zeroX+1];
            currentMatrix[zeroY][zeroX+1]= curr;
            return true;
        }
    }

    //move up
    public static boolean moveUp( int[][] currentMatrix){
        int zeroX =-1, zeroY = -1;

        for(int i = 0; i <3; i++){
            for(int j = 0; j <3; j++) {
                if (currentMatrix[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;

                }
            }
        }
        if(zeroY-1<0){

            return false;
        }
        else{
            int curr = currentMatrix[zeroY][zeroX];
            currentMatrix[zeroY][zeroX] = currentMatrix[zeroY-1][zeroX];
            currentMatrix[zeroY-1][zeroX]= curr;
            return true;
        }
    }

    //move down
    public static boolean moveDown( int[][] currentMatrix){
        int zeroX =-1, zeroY = -1;

        for(int i = 0; i <3; i++){
            for(int j = 0; j <3; j++) {
                if (currentMatrix[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;

                }
            }
        }
        if(zeroY+1>2){
            return false;
        }
        else{
            int curr = currentMatrix[zeroY][zeroX];
            currentMatrix[zeroY][zeroX] = currentMatrix[zeroY+1][zeroX];
            currentMatrix[zeroY+1][zeroX]= curr;
            return true;
        }
    }

    //compare two matrixs 3x3
    public static boolean areSame(int[][] firstMatrix, int[][] secondMatrix){
        boolean flag = true;
        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if(firstMatrix[i][j] != secondMatrix[i][j]){
                    flag = false;
                }
            }
        }
        return flag;
    }



}
