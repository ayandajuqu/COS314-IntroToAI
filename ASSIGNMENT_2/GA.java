import java.util.Random;


public class GA {
    private Random rand;
    final int POPULATION_SIZE=100;
    final double MUTATION_RATE = 0.1;
    final int MAX_GENERATIONS = 500;
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
public Chromosome selection(Chromosome[] population,double capacity, double[] weight, double[ ]values){
    //boolean found=false;//this states if a better solution is found
    Chromosome current=null;
    rand= new Random();
    for(int i=0; i<TOURNAMENT_SIZE; i++){
        int index=rand.nextInt(population.length); //
        if(current==null || population[index].fitness> current.fitness){
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

///-------------------THIS IS THE HILLCLIMBING ALGORITHM--------------
public void hillCLimb(Chromosome individual, double capacity, double[] values, double[] weights){
    boolean improvement = true;
    while (improvement) {
        improvement = false;
        for (int i = 0; i < individual.genes.length; i++) {
            if (individual.genes[i] == 0) {
                individual.genes[i] = 1;
                individual.fitness = individual.calculateFitness(capacity, values, weights);
                if (individual.fitness == 0) {
                    // Adding the item exceeded the capacity, so remove it
                    individual.genes[i] = 0;
                } else {
                    // Adding the item improved the solution
                    improvement = true;
                }
            }
        }
    } 
}

public void setRandom(Random rand){
    this.rand = rand;
}
// 7:   Evaluate fitness of all individuals
// 8:   Generate a new population
// 10: end while
// 11: return best individual 
}