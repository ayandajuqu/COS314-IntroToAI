import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
    
public class Main{
    
    public static void main(String[] args) throws FileNotFoundException {
        // Initialize GA
        GA ga = new GA();
        long seed = System.currentTimeMillis ();
        Random rand = new Random (seed);
        System.out.println( " Seed Value " + seed );
        ga.setRandom(rand);
    
        // Read the knapsack problem instance from a file
        String fileName = "f1_l-d_kp_10_269";
        //String fileName = "f2_l-d_kp_20_878";
        //String fileName = "f3_l-d_kp_4_20";
        //String fileName = "f4_l-d_kp_4_11";
        //String fileName = "f5_l-d_kp_15_375";
        //String fileName = "f6_l-d_kp_10_60";
        //String fileName = "f7_l-d_kp_7_50";
        //String fileName = "f8_l-d_kp_23_10000";
        //String fileName = "f9_l-d_kp_5_80";
        //String fileName = "f10_l-d_kp_20_879";
        //String fileName = "knapPI_1_100_1000_1";
        Scanner scanner = new Scanner(new File(fileName));
        int numItems = scanner.nextInt();
        double capacity = scanner.nextDouble();
    
        double[] values = new double[numItems];
        double[] weights = new double[numItems];
    
        for(int i = 0; i < numItems; i++) {
            values[i] = scanner.nextDouble();
            weights[i] = scanner.nextDouble();
        }
        

        scanner.close();
    
        // Generate initial population
        Chromosome[] population = ga.generateInitialPopulation(numItems);
    
        // Calculate fitness of all individuals in the population
        for (Chromosome individual : population) {
            individual.fitness = individual.calculateFitness(capacity, values, weights);
        }

        long startTime = System.currentTimeMillis();
        for (int generation = 0; generation < ga.MAX_GENERATIONS; generation++) {
            // Perform selection, crossover, and mutation to generate new population
            for (int i = 0; i < ga.POPULATION_SIZE; i++) {
                Chromosome parent1 = ga.selection(population, capacity, weights, values);
                Chromosome parent2 = ga.selection(population, capacity, weights, values);
                Chromosome offspring = ga.crossover(parent1, parent2);
                ga.mutate(offspring);
                
//---------------------------THIS IS THE LOCAL SEARCH-----------------
            //ga.hillCLimb(offspring, capacity, values, weights); 
//--------------------------------------------------------------------
                offspring.fitness = offspring.calculateFitness(capacity, values, weights);
                population[i] = offspring;
            }
        }
        long endTime = System.currentTimeMillis();
        double runtime = (endTime - startTime) / 1000.0;  // Convert milliseconds to seconds
        
        // Print the best solution
        Chromosome best = Arrays.stream(population).max(Comparator.comparing(c -> c.fitness)).orElse(null);
        System.out.println("Best: " + best.fitness);
        System.out.println("Known optimum: ");
        System.out.println("Runtime: "+ runtime);

    }
}    