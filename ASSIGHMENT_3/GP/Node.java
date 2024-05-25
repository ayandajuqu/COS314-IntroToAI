package ASSIGHMENT_3.GP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class Node {
    abstract double evaluate(Mushroom mushroom);

    abstract Node mutate(Random random);

    abstract Node replaceSubtree(Node replacement);

    abstract void collectNodes(List<Node> nodes);
}

class GPTerminal extends Node {
    public int featureIndex;

    public GPTerminal(int featureIndex) {
        this.featureIndex = featureIndex;
    }

    @Override
    double evaluate(Mushroom mushroom) {
        // Evaluate the terminal node using the feature value of the Mushroom instance
        return mushroom.getFeatureAtIndex(featureIndex);
    }

    @Override
    Node mutate(Random random) {
        // Terminal nodes are immutable, so no mutation is possible
        return this;
    }

    @Override
    Node replaceSubtree(Node replacement) {
        // Terminal nodes cannot be replaced, so return the original node
        return this;
    }

    @Override
    void collectNodes(List<Node> nodes) {
        // Add this node to the list of nodes
        nodes.add(this);
    }
}
class GPConstant extends Node {
    public double value;

    public GPConstant(double value) {
        this.value = value;
    }

    @Override
    double evaluate(Mushroom mushroom) {
        // Evaluate the constant node by returning its stored value
        return value;
    }

    @Override
    Node mutate(Random random) {
        // Constants are immutable, so no mutation is possible
        return this;
    }

    @Override
    Node replaceSubtree(Node replacement) {
        // Constant nodes cannot be replaced, so return the original node
        return this;
    }

    @Override
    void collectNodes(List<Node> nodes) {
        // Add this node to the list of nodes
        nodes.add(this);
    }
}

class GPFunction extends Node {
    public Node left;
    public Node right;
    public int operation;
    // 0 = add, 1 = subtract, 2 = multiply, 3 = divide

    public GPFunction(Node left, Node right, int operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    double evaluate(Mushroom mushroom) {
        double leftValue = left.evaluate(mushroom);
        double rightValue = right.evaluate(mushroom);
        switch (operation) {
            case 0:
                return leftValue + rightValue;
            case 1:
                return leftValue - rightValue;
            case 2:
                return leftValue * rightValue;
            case 3:
                return rightValue == 0 ? 0 : leftValue / rightValue;
            default:
                throw new IllegalStateException("Unexpected operation value: " + operation);
        }
    }

    @Override
    Node mutate(Random random) {
        // Perform mutation by selecting one of the child nodes and mutating it
        if (random.nextBoolean()) {
            left = left.mutate(random);
        } else {
            right = right.mutate(random);
        }
        return this;
    }

    @Override
    Node replaceSubtree(Node replacement) {
        // Replace the entire subtree rooted at this node with the replacement node
        return replacement;
    }

    @Override
    void collectNodes(List<Node> nodes) {
        // Add this node and its children to the list of nodes
        nodes.add(this);
        left.collectNodes(nodes);
        right.collectNodes(nodes);
    }
}