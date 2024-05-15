package ASSIGHMENT_3;
import java.util.List;
import java.util.ArrayList;

public class Tree {
    private Node root;
    private double fitness;

    public Tree(Node root){
        this.root=root;
    }

    public void setFitness(double fintess){
        this.fitness=fintess;
    }
    
    public double fitness(){
        return fitness;
    }

    public Node getRoot(){
        return root;
    }

    public int evaluate(double[] feature){
        return 0;
    }
    public void setRoot(Node root){
        this.root=root;
    }
}

