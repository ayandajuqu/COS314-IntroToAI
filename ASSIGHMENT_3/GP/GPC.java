package ASSIGHMENT_3.GP;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPC {
    private static final int POPULATION_SIZE = 100;
    private static final int NUM_GENERATIONS = 50;
    private static final int TOURNAMENT_SIZE = 5;
    private static final double CROSSOVER_RATE = 0.9;
    private static final double MUTATION_RATE = 0.1;
    private static final int MAX_DEPTH = 10;

    private List<Individual> population;
    private Random random;

    public GPC(long seed) {
        this.population = new ArrayList<>();
        this.random = new Random(seed);
    }

    public void run(List<Mushroom> mushrooms) {
        initializePopulation();
        for (int gen = 0; gen < NUM_GENERATIONS; gen++) {
            evolvePopulation(mushrooms, gen);
        }
    }

    public double predict(Mushroom mushroom) {
        Individual bestIndividual = selectBestIndividual();
        return bestIndividual.getRoot().evaluate(mushroom);
    }

    private void initializePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Individual(MAX_DEPTH, random));
        }
    }

    private void evolvePopulation(List<Mushroom> mushrooms, int generation) {
        List<Individual> newPopulation = new ArrayList<>();
        while (newPopulation.size() < POPULATION_SIZE) {
            Individual parent1 = tournamentSelection();
            Individual parent2 = tournamentSelection();
            Individual child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }
        population = newPopulation;
        evaluatePopulationFitness(mushrooms);
        printGenerationFitness(generation, mushrooms);
    }

    private Individual tournamentSelection() {
        Individual best = null;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            Individual individual = population.get(random.nextInt(POPULATION_SIZE));
            if (best == null || individual.getFitness() > best.getFitness()) {
                best = individual;
            }
        }
        return best;
    }

    private void evaluatePopulationFitness(List<Mushroom> mushrooms) {
        for (Individual individual : population) {
            individual.evaluate(mushrooms);
        }
    }

    private void mutate(Individual individual) {
        if (random.nextDouble() < MUTATION_RATE) {
            individual.mutate(random);
            //System.out.println("Individual mutated.");
        }
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        if (random.nextDouble() < CROSSOVER_RATE) {
            //System.out.println("Crossover applied.");
            return parent1.crossover(parent2, random);
        } else {
            return parent1.clone();
        }
    }

    private Individual selectBestIndividual() {
        Individual bestIndividual = null;
        double bestFitness = Double.MIN_VALUE;
        for (Individual individual : population) {
            double fitness = individual.getFitness();
            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestIndividual = individual;
            }
        }
        return bestIndividual;
    }

    private void printGenerationFitness(int generation, List<Mushroom> mushrooms) {
        double totalFitness = population.stream().mapToDouble(Individual::getFitness).sum();
        double accuracy = calculateAccuracy(mushrooms);
        System.out.println("Fitness of Generation " + (generation + 1) + ": " + ((totalFitness / POPULATION_SIZE) * 100) + "%");
        System.out.println("Training Accuracy of Generation " + (generation + 1) + ": " + (accuracy * 100) + "%");
    }

    private double calculateAccuracy(List<Mushroom> mushrooms) {
        int correctPredictions = 0;
        for (Mushroom mushroom : mushrooms) {
            double prediction = predict(mushroom);
            int predictedClass = (prediction >= 0.5) ? 1 : 0;
            if (predictedClass == mushroom.mushroomClass) {
                correctPredictions++;
            }
        }
        return (double) correctPredictions / mushrooms.size();
    }
}
