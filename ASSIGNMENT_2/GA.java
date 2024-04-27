import java.util.Random;
public class GA {

    private int populationSize;
    private int []weight;
    private int []value;
    private int crossoverRate;
    private int mutationRate;
    private Random rand;

    public GA(int populationSize, int[] weight, int[] value, int crossoverRate, int mutationRate)
    {
        this.crossoverRate=crossoverRate;
        this.weight=weight;
        this.value=value;
        this.mutationRate=mutationRate;
        this.rand=new Random();
    }

    //initial population
    public int[][] generateInitialPopulation(){
        int[][]initialPopulation= new int[populationSize][weight.length];
        for(int i=0; i<populationSize; i++){
            for(int j=0; j<weight.length; j++){
                initialPopulation[i][j]=rand.nextInt(2);
            }
        }
        return initialPopulation;
    }

    private int calculateFitness(){
        
        return -1;
    }
    
    /*1: Create initial population
2: Calculate fitness of all individuals
3: while termination condition not met do
4:   Select fitter individuals for reproduction
5:   Recombine individuals
6:   Mutate individuals
7:   Evaluate fitness of all individuals
8:   Generate a new population

10: end while
11: return best individual */

//create initial popularion
    
}