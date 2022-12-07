import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
    static final int NUMBER_OF_CHILDREN = 100;
    static final int GENERATIONS = 10000;

    int[][] distancesMatrix;
    int firstGen;
    int lastGen;

    int mutations;
    int crossings;
    int improvements;

    public GeneticAlgorithm(int[][] distancesMatrix, int firstGen, int lastGen) {
        this.distancesMatrix = distancesMatrix;
        this.firstGen = firstGen;
        this.lastGen = lastGen;
        mutations = 0;
    }

    public Child[] doChildren() {
        Child[] children = new Child[NUMBER_OF_CHILDREN];
        for (int i = 0; i < NUMBER_OF_CHILDREN; i++) {
            children[i] = doChild();
        }
        return children;
    }

    private Child doChild() {
        ArrayList<Integer> firstGenes = new ArrayList<>();
        firstGenes.add(firstGen);
        ArrayList<Integer> secondGenes = new ArrayList<>();
        secondGenes.add(lastGen);
        Random rand = new Random();
        //do two lists with random genes,
        //first starts with first gen, second starts with second gen
        for (int j = 0; j < distancesMatrix.length * 0.7; j++) {
            int nextGenForFirst = rand.nextInt(distancesMatrix.length);
            int nextGenForSecond = rand.nextInt(distancesMatrix.length);
            while (distancesMatrix[firstGenes.get(firstGenes.size() - 1)][nextGenForFirst] == 0) {
                nextGenForFirst = rand.nextInt(distancesMatrix.length);
            }
            firstGenes.add(nextGenForFirst);
            while (distancesMatrix[secondGenes.get(secondGenes.size() - 1)][nextGenForSecond] == 0) {
                nextGenForSecond = rand.nextInt(distancesMatrix.length);
            }
            secondGenes.add(nextGenForSecond);
        }
        //stick up first and second lists
        ArrayList<Integer> child = new ArrayList<>();
        boolean isAdded = false;
        for (int j = 0; j < firstGenes.size(); j++) {
            for (int k = 0; k < secondGenes.size(); k++) {
                if (distancesMatrix[firstGenes.get(firstGenes.size() - 1 - j)][secondGenes.get(secondGenes.size() - 1 - k)] != 0 && !isAdded) {
                    for (int l = 0; l < firstGenes.size() - j; l++) {
                        child.add(firstGenes.get(l));
                    }
                    for (int l = 0; l < secondGenes.size() - k; l++) {
                        child.add(secondGenes.get(secondGenes.size() - k - l - 1));
                    }
                    isAdded = true;
                }
            }
        }
        if (child.isEmpty()) return doChild();
        else return new Child(child);

    }

    public int solve(int crossingVariation, int mutationVersion, int improvementVariation) {
        mutations = 0;
        improvements = 0;
        crossings = 0;
        Child[] children = doChildren();
        Child record = findMinDistance(children);
        Random rand = new Random();
        for (int i = 0; i < GENERATIONS; i++) {
            //choose parents
            //===============================================================
            Child firstParent = record;
            Child secondParent = children[rand.nextInt(children.length)];
            while (secondParent == firstParent) {
                secondParent = children[rand.nextInt(children.length)];
            }

            //do child(three variations)
            //=================================================================
            Child child = new Child(new ArrayList<>());
            if (crossingVariation == 1){
                 child = crossingV1(firstParent, secondParent);
            } else if (crossingVariation == 2){
                 child = crossingV2(firstParent, secondParent);
            } else if (crossingVariation == 3){
                 child = crossingV3(firstParent, secondParent);
            }
            if (child.getGenes().get(child.getGenes().size() - 1) != lastGen) {
                System.out.println("Crossing error");
                System.out.println(firstParent.getGenes());
                System.out.println(secondParent.getGenes());
                System.out.println(child.getGenes());
                throw new IllegalArgumentException();
            }
            //mutation
            //===============================================================
            Child copy = child;
            if (mutationVersion == 1) {
                child = mutationV1(child);
            } else if (mutationVersion == 2) {
                child = mutationV2(child);
            }
            if (child.getGenes().get(child.getGenes().size() - 1) != lastGen || !child.canExist(distancesMatrix)) {
                System.out.println("Mutation error");
                System.out.println(copy.getGenes());
                throw new IllegalArgumentException();
            }
            //local improvement
            //================================================================
            copy = child;
            if (rand.nextDouble() > 0.4) {
                if (improvementVariation == 1) {
                    child = localImprovementV1(child);
                } else if (improvementVariation == 2) {
                    child = localImprovementV2(child);
                }

            }
            if (child.getGenes().get(child.getGenes().size() - 1) != lastGen) {
                System.out.println("Improvement error");
                System.out.println(copy.getGenes());
                throw new IllegalArgumentException();
            }
            //rewrite the longest path with new child
            //================================================================
            children[findMaxIndex(children)] = child;
            //change record
            record = findMinDistance(children);
        }
//        System.out.println(findMinDistance(children).getGenes());
//        System.out.println(findMinDistance(children).countDistance(distancesMatrix));
//        System.out.println("Crossings: " + crossings);
//        System.out.println("Mutations: " + mutations);
//        System.out.println("Improvements: " + improvements);
        System.out.print(".");
        return findMinDistance(children).countDistance(distancesMatrix);
    }

    private Child localImprovementV1(Child child) {
        ArrayList<Integer> genes = new ArrayList<>(child.getGenes());
        for (int i = 0; i < genes.size() - 1; i++) {
            for (int j = i + 1; j < genes.size() - 1; j++) {
                if (genes.get(i).intValue() == genes.get(j).intValue()) {
                    if (j > i) {
                        genes.subList(i, j).clear();
                        if (genes.get(genes.size() - 1) == lastGen && genes.get(0) == firstGen) {
                            improvements++;
                            return new Child(genes);
                        }
                        return new Child(new ArrayList<>(child.getGenes()));
                    }
                }
            }
        }
        return new Child(new ArrayList<>(child.getGenes()));
    }

    private Child localImprovementV2(Child child) {
        ArrayList<Integer> genes = new ArrayList<>(child.getGenes());
        for (int i = 0; i < genes.size() - 1; i++) {
            for (int j = i + 2; j < genes.size() - 1; j++) {
                if (distancesMatrix[genes.get(i)][genes.get(j)] != 0) {
                    if (j > i) {
                        genes.subList(i + 1, j).clear();
                        if (genes.get(genes.size() - 1) == lastGen && genes.get(0) == firstGen) {
                            improvements++;
                            return new Child(genes);
                        }
                        return new Child(new ArrayList<>(child.getGenes()));
                    }
                }
            }
        }
        return new Child(new ArrayList<>(child.getGenes()));
    }

    private Child mutationV1(Child child) {
        if (child.getGenes().size() <= 2) {
            return child;
        } else {
            Random rand = new Random();
            ArrayList<Integer> genes = new ArrayList<>(child.getGenes());
            int genForMutation = rand.nextInt(genes.size() - 1);
            if (genForMutation == 0) {
                genForMutation++;
            }
            int newGen = rand.nextInt(distancesMatrix.length - 2) + 1;
            genes.set(genForMutation, newGen);
            Child mutant = new Child(genes);
            if (mutant.canExist(distancesMatrix)) {
                mutations++;
                return mutant;
            } else return child;
        }
    }

    private Child mutationV2(Child child) {
        if (child.getGenes().size() <= 2) {
            return child;
        } else {
            ArrayList<Integer> genes = new ArrayList<>(child.getGenes());
            Random rand = new Random();
            for (int i = rand.nextInt(genes.size() - 2); i < genes.size() - 2; i++) {
                for (int j = 0; j < distancesMatrix.length; j++) {
                    if (distancesMatrix[j][genes.get(i)] != 0 && distancesMatrix[j][genes.get(i + 2)] != 0) {
                        if (j != genes.get(i + 1)) {
                            genes.set(i + 1, j);
                            mutations++;
                            return new Child(genes);
                        }
                    }
                }
            }
            return new Child(genes);
        }
    }


    private int findMaxIndex(Child[] children) {
        int maxIndex = 0;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < children.length; i++) {
            int currentDistance = children[i].countDistance(distancesMatrix);
            if (max < currentDistance) {
                max = currentDistance;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private Child crossingV1(Child firstParent, Child secondParent) {
        Random rand = new Random();
        if (firstParent.getGenes().size() > secondParent.getGenes().size()) {
            Child curr = firstParent;
            firstParent = secondParent;
            secondParent = curr;
        }
        int cutLine = rand.nextInt(firstParent.getGenes().size() / 2);
        ArrayList<Integer> genes = new ArrayList<>();
        for (int i = 0; i < secondParent.getGenes().size(); i++) {
            if (i < cutLine) {
                genes.add(firstParent.getGenes().get(i));
            } else genes.add(secondParent.getGenes().get(i));
        }

        Child child = new Child(genes);
        if (child.canExist(distancesMatrix)) {
            crossings++;
            return child;
        } else return new Child(new ArrayList<>(firstParent.getGenes()));
    }



    private Child crossingV2(Child firstParent, Child secondParent) {
        Random rand = new Random();
        if (firstParent.getGenes().size() > secondParent.getGenes().size()) {
            Child curr = firstParent;
            firstParent = secondParent;
            secondParent = curr;
        }
        int firstCutLine = rand.nextInt(firstParent.getGenes().size() / 2);
        int secondCutLine = rand.nextInt(firstParent.getGenes().size() -firstParent.getGenes().size() / 2)+firstParent.getGenes().size() / 2;
        ArrayList<Integer> genes = new ArrayList<>();
        for (int i = 0; i < firstParent.getGenes().size(); i++) {
            if (i < firstCutLine || i > secondCutLine-1 ) {
                genes.add(firstParent.getGenes().get(i));
            } else genes.add(secondParent.getGenes().get(i));
        }

        Child child = new Child(genes);
        if (child.canExist(distancesMatrix)) {
            crossings++;
            return child;
        } else return new Child(new ArrayList<>(firstParent.getGenes()));
    }


    private Child crossingV3(Child firstParent, Child secondParent) {
        Random rand = new Random();
        int indexOfCutGenInFirst = rand.nextInt(firstParent.getGenes().size());
        int cutGenInFirst = firstParent.getGenes().get(indexOfCutGenInFirst);
        int indexOfCutGenInSecond = secondParent.getGenes().indexOf(cutGenInFirst);
        if (indexOfCutGenInSecond == -1) return new Child(new ArrayList<>(firstParent.getGenes()));
        ArrayList<Integer> genes = new ArrayList<>();
        for (int i = 0; i < indexOfCutGenInFirst; i++) {
            genes.add(firstParent.getGenes().get(i));
        }
        for (int i = indexOfCutGenInSecond; i < secondParent.getGenes().size(); i++) {
            genes.add(secondParent.getGenes().get(i));
        }
        Child child = new Child(genes);
        if (child.canExist(distancesMatrix)) {
            crossings++;
            return child;
        } else return new Child(new ArrayList<>(firstParent.getGenes()));
    }

    public Child findMinDistance(Child[] children) {
        int minIndex = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < children.length; i++) {
            int currentDistance = children[i].countDistance(distancesMatrix);
            if (min > currentDistance) {
                min = currentDistance;
                minIndex = i;
            }
        }
        return children[minIndex];
    }
}
