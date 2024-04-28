import java.util.Random;
public class Chromosome {
    int genes[];
    double fitness;
    private Random rand;

    public Chromosome(int numGenes)
    {
        fitness=0;
        genes=new int[numGenes];
        rand= new Random();
        for(int i=0; i<numGenes; i++)
        {
            genes[i]=rand.nextInt(2);
        }
    }

    //claculate fitness of chromosome
    public double calculateFitness(double capacity, double[] value, double []weight){
        double sumWeight=0;
        double sumValues=0;
        for(int i=0; i<genes.length; i++){
            if(genes[i]==1){ //if item is present in knapsack
                sumValues+=value[i];
                sumWeight+=weight[i];
            }
        }
        if(sumWeight<=capacity)
            return sumValues;
        return 0;
    }

}
