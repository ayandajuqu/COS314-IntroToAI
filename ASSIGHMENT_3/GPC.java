package ASSIGHMENT_3;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GPC {
    final int POPULATION_SIZE=100;
    final int GENERATIONS=50;
    final int MAX_TREE_DEPTH=5;
    final double PROBABILITY=0.5;

    private List<Tree> initializePopulation() {
        List<Tree> population = new ArrayList<>();
        // Generate random trees for the initial population
        for (int i = 0; i < POPULATION_SIZE; i++) {
            
            Tree individual = generateRandomTree();
            population.add(individual);
        }
        return population;
    }

    private Tree generateRandomTree(){
        if(Math.random()<PROBABILITY)
            return new Tree(generateFullTree(0));
        return new Tree(growTree(0));
    }

    private Node generateFullTree(int currentDepth){
        if(currentDepth== MAX_TREE_DEPTH-1)
        {
            return new Node("terminal", null );
        }

        Node newNode= new Node("function", null);
        for(int i=0; i<MAX_TREE_DEPTH; i++){
                newNode.addChild(generateFullTree(currentDepth+1));
        }
        return newNode;
    }

    private Node growTree(int currentDepth){
        if(currentDepth== MAX_TREE_DEPTH-1)
        {
            return new Node("terminal", null );
        }
        if (Math.random() < PROBABILITY) {
            Node newNode = new Node("function", null);
            for (int i = 0; i < 2; i++) {
                newNode.addChild(growTree(currentDepth + 1));
            }
            return newNode;
        } else {
            return new Node("terminal", null);
        }
    }

    private void evaluateFiteness(List<Tree> population, Dataset dataset){
        int featureSize=dataset.getFeatures().size();
        int correctLabels=0;
        for (Tree p: population){
            for(int i=0; i<featureSize; i++){
                double[] feat=dataset.getFeatures().get(i);
                int givenLab=dataset.getLabels().get(i);
                int testedLabel=p.evaluate(feat);
                if(testedLabel==givenLab)
                correctLabels++;
            }
            double accuracy=correctLabels/featureSize;
            p.setFitness(accuracy);
        }
    }

    private List<Tree> selection(List<Tree> population1, List<Tree> population2){
        return null;
    }

    private List<Tree> reproduction(){
        return null;
    }

    private List<Tree> replacement(){
        return null;
    }
}

