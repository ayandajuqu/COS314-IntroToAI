package ASSIGNMENT_1;

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
        total+=total+=disntanceCost[arr[5]][arr[0]];
        return total;
    }

    public int[] simulatedAnnealing(int[] initialSolution){

        int[] currentSolution= initialSolution.clone();
        int currentCost=cost(currentSolution);
        int temperature=1000;
        double cooling=0.003;

        while(temperature>1){
            //find neigbours
            int[] newSolution=findNeighbours(currentSolution);
            int newCost= cost(newSolution);
            if(acceptNewSol(currentCost, newCost, temperature)){
                currentSolution=newSolution;
                currentCost=newCost;
            }

            temperature*=1-cooling;
        }
        return currentSolution;
    }

    
    
}
