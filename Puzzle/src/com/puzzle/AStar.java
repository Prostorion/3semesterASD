package com.puzzle;




import java.util.ArrayList;

public class AStar extends PuzzleObj{

    //lists with nodes, needed to algorithm
    private ArrayList<int[][]> closedList;

    //Getters and Setters
    public ArrayList<int[][]> getClosedList() {
        return closedList;
    }


    //method to get node with min score from array
    //used for "openList"
    private NodeSecond getMinNode(ArrayList<NodeSecond> children, int[][]solution){
        int min = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0; i< children.size(); i++){
            if(children.get(i).getScore(solution)<min){
                min = children.get(i).getScore(solution);
                index = i;
            }
        }
        return children.get(index);
    }
    public void solve(){

        //make first node and it's parent
        NodeSecond parent = new NodeSecond(-1);
        NodeSecond currentNode = new NodeSecond(getStartMatrix(), parent, 0);

        //make main arrays for a A* algorithm
        ArrayList<NodeSecond> closedList = new ArrayList<>();
        ArrayList<NodeSecond> openList = new ArrayList<>();

        //counter (only for watching what was done)
        int counter = 0;
        while(!currentNode.solved(getSolutionMatrix())){
            //show current situation
            if(openList.size()> counter){
                System.out.println("Elements in open list:"+openList.size());
                System.out.println("Elements in closed list:"+closedList.size());
                counter +=1000;
            }

            //form children
            currentNode.doChildren();

            //remove children from parent list if they are in the closed list
            for (int i = 0; i< closedList.size(); i++){
                for (int j = 0; j< currentNode.getChildren().size(); j++){
                    if(PuzzleObj.areSame(currentNode.getChildren().get(j).getPosition(), closedList.get(i).getPosition())){
                        currentNode.getChildren().remove(currentNode.getChildren().get(j));
                    }
                }

            }

            closedList.add(currentNode);
            for(int i =0; i<currentNode.getChildren().size(); i++) {
                openList.add(currentNode.getChildren().get(i));
            }
            currentNode = getMinNode(openList, getSolutionMatrix());
            openList.remove(currentNode);
        }

        System.out.print("Solution:\n");
        int steps = 0;
        while(currentNode.getG()!=-1){
            System.out.print(currentNode +"\n");
            currentNode = currentNode.getParent();
            steps+=1;
        }
        System.out.print("\nNumber of steps:"+steps);

        System.out.print("\nOpen:"+openList.size());
        System.out.print("\nClosedList:"+closedList.size());
    }
}
