import java.util.Random;


public class GA {
    private Random rand;
    final int POPULATION_MULTIPLIER = 8;
    final int POPULATION_SIZE=100;
    final double CROSSOVER_RATE = 0.3;
    final double MUTATION_RATE = 0.4;
    final int MAX_GENERATIONS = 500;
    final int STOPPING_ITERATIONS = 250;
    final double TOURNAMENT_SIZE = 5;

public GA(){
        rand = new Random();
}
// 1: Create initial population
public Chromosome[] generateInitialPopulation(int numItems){
   Chromosome[] newPopulation= new Chromosome[POPULATION_SIZE];
    for (int i=0; i<POPULATION_SIZE; i++){
        newPopulation[i]=new Chromosome(numItems);
    }
    
    return newPopulation;
}
  
// 2: Calculate fitness of all individuals
// 3: while termination condition not met do
// 4:   Select fitter individuals for reproduction

//this uses tournament selecetion
public Chromosome selection(Chromosome[] population,int capacity, int[] weight, int[ ]values){
    //boolean found=false;//this states if a better solution is found
    Chromosome current=null;
    rand= new Random();
    for(int i=0; i<TOURNAMENT_SIZE; i++){
        int index=rand.nextInt(population.length);
        if(population[index].fitness> current.fitness){
            //found =true;
            current=population[index];
        }
    }
    return current;
}
// 5:   Recombine individuals
public Chromosome crossover(Chromosome mother, Chromosome father){
    int offspringLength= mother.genes.length;
    boolean choose;
    //rand=new Random();
    Chromosome offspring = new Chromosome(offspringLength);
    for(int i=0; i<offspringLength; i++){
        choose=rand.nextBoolean();
        if(choose)
            offspring.genes[i]=father.genes[i];
        offspring.genes[i]=mother.genes[i];
    }
    return offspring;
}
// 6:   Mutate individuals

public void mutate(Chromosome individual){
    //rand= new Random();
    int size=individual.genes.length;
    for(int i=0; i<size; i++){
        if(rand.nextDouble()< MUTATION_RATE)
            individual.genes[i]=1-individual.genes[i];
    }
}
// 7:   Evaluate fitness of all individuals
// 8:   Generate a new population
// 10: end while
// 11: return best individual 
}