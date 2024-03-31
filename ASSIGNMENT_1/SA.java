package ASSIGNMENT_1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class SA {
    int[][] disntanceCost;

    public SA(int[][] disntanceCost)
    {
        this.disntanceCost=disntanceCost;
    }
    
    //A method to generate an initial solution.
    public int[] initialSolution()
    {
        //create an populate arr
        int[] currSolution= new int[6];
        for(int i=0; i<5; i++){
            currSolution[i]=i;
        }
        currSolution[5]=0;//set Hatfield
        //randomise arr
        Random r=new Random();
        
        for (int i=4; i>1; i--)
        {
            int j=r.nextInt(i+1);
            int tmp=currSolution[i];
            currSolution[i]=currSolution[j];
            currSolution[j]=tmp;
        }
        return currSolution;
    }

    //A generation function to find neighbours 
    public int[] findNeighbours(int[] currSolution)
    {
        int[] neighbours=currSolution.clone();
        Random r=new Random();
        int a=r.nextInt(4) +1;
        int b=r.nextInt(4) +1;
        swap(neighbours, a, b);

        return neighbours;
    }

    private void swap(int[]arr, int a, int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }
    //A cost function.
    public int cost(int[] arr)
    {
        int total=0;
        for(int i=0; i<5; i++){
            total+=disntanceCost[arr[i]][arr[i+1]];
        }
        total+=disntanceCost[arr[5]][arr[0]];
        return total;
    }

    public int[] simulatedAnnealing(int[] initialSolution){
        int iterations=0;
        int[] currentSolution= initialSolution.clone();
        int currentCost=cost(currentSolution);
        double temperature=1000.000;
        double cooling=0.003;


        //System.out.println("\n Initial Temperature: " + temperature);
        try (PrintWriter writer = new PrintWriter("SA_txt.txt", "UTF-8")){
            // This part of the code is implementing the simulated annealing algorithm. Here's a
            // breakdown of what's happening inside the `while` loop:
            while(temperature>1){
                //find neigbours
                int[] newSolution=findNeighbours(currentSolution);
                int newCost= cost(newSolution);
                //testing
                System.out.println("Temperature: " + temperature);
                System.out.println("New solution: " + Arrays.toString(newSolution));
                System.out.println("New cost: " + newCost);
                
                if(acceptNewSol(currentCost, newCost, temperature)){
                    currentSolution=newSolution;
                    currentCost=newCost;
                    System.out.println("New solution is accepted");
                }
                else
                System.out.println("New solution is rejected");
                writer.println(iterations + "\t" + currentCost);
                
                temperature*=1-cooling;
                iterations++;
            }

        }
        catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        System.out.println("\nFinal solution: " + Arrays.toString(currentSolution));
        System.out.println("Final cost: " + currentCost);
        return currentSolution;
    }

    private boolean acceptNewSol(int currentCost, int newCost, double temp){
        if(newCost< currentCost)
            return true;// always a better solution
        double prob=Math.exp((currentCost - newCost) / temp);
        return Math.random()<prob;
    }
    
}
