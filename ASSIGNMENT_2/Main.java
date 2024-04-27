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
        Random rand = new Random ();
        System.out.println( " Seed Value " + seed );
        ga.setRandom(rand);
    
        // Read the knapsack problem instance from a file
        String fileName = "ASSIGNMENT_2/f1_l-d_kp_10_269";
        Scanner scanner = new Scanner(new File(fileName));
        int numItems = scanner.nextInt();
        int capacity = scanner.nextInt();
    
        int[] values = new int[numItems];
        int[] weights = new int[numItems];
    
        for(int i = 0; i < numItems; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
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

                //------THIS IS THE GA+ LOCAL SEARCH----
                //ga.hillCLimb(offspring, capacity, values, weights); 
                //--------------------------------------
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