package ASSIGHMENT_3.GP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Individual {
    private Node root;
    private double fitness;

    public Individual(int maxDepth, Random random) {
        this.root = generateRandomTree(maxDepth, random);
    }

    public Node getRoot() {
        return root;
    }

    public double getFitness() {
        return fitness;
    }

    public void evaluate(List<Mushroom> mushrooms) {
        double correctPredictions = 0;
        for (Mushroom mushroom : mushrooms) {
            double prediction = root.evaluate(mushroom);
            if ((prediction >= 0.5 ? 1 : 0) == mushroom.mushroomClass) {
                correctPredictions++;
            }
        }
        fitness = correctPredictions / mushrooms.size();
    }

    public Individual crossover(Individual other, Random random) {
        Node crossoverPoint1 = getRandomNode(root, random);
        Node crossoverPoint2 = getRandomNode(other.root, random);
        Node newRoot = root.replaceSubtree(crossoverPoint2);
        return new Individual(newRoot);
    }

    public void mutate(Random random) {
        root = root.mutate(random);
    }

    public Individual clone() {
        Node clonedRoot = cloneTree(root);
        return new Individual(clonedRoot);
    }

    private Node getRandomNode(Node node, Random random) {
        List<Node> nodes = new ArrayList<>();
        node.collectNodes(nodes);
        return nodes.get(random.nextInt(nodes.size()));
    }

    private Node generateRandomTree(int maxDepth, Random random) {
        if (maxDepth == 0 || random.nextBoolean()) {
            if (random.nextBoolean()) {
                return new GPTerminal(random.nextInt(Mushroom.NUM_FEATURES));
            } else {
                return new GPConstant(random.nextDouble() * 2 - 1);
            }
        } else {
            Node left = generateRandomTree(maxDepth - 1, random);
            Node right = generateRandomTree(maxDepth - 1, random);
            int operation = random.nextInt(4);
            return new GPFunction(left, right, operation);
        }
    }

    private Node cloneTree(Node node) {
        if (node instanceof GPTerminal) {
            GPTerminal terminal = (GPTerminal) node;
            return new GPTerminal(terminal.featureIndex);
        } else if (node instanceof GPConstant) {
            GPConstant constant = (GPConstant) node;
            return new GPConstant(constant.value);
        } else if (node instanceof GPFunction) {
            GPFunction function = (GPFunction) node;
            Node leftClone = cloneTree(function.left);
            Node rightClone = cloneTree(function.right);
            return new GPFunction(leftClone, rightClone, function.operation);
        }
        throw new IllegalArgumentException("Unknown node type");
    }

    private Individual(Node root) {
        this.root = root;
    }
}
