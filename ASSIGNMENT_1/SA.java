package ASSIGNMENT_1;

import java.util.Random;

public class SA {
    int[][] disntanceCost;

    //A method to generate an initial solution.
    public int[] initialSolution()
    {
        //create an populate arr
        int[] currSolution= new int[5];
        for(int i=0; i<5; i++){
            currSolution[i]=i;
        }

        //randomise arr
        Random r=new Random();
        
        for (int i=4; i>0; i--)
        {
            int j=r.nextInt(i+1);
            int tmp=currSolution[i];
            currSolution[i]=currSolution[j];
            currSolution[j]=tmp;
        }
        return currSolution;
    }

    //A generation function to find neighbours 
    public int[] findNeighbours(int index)
    {
        return null;
    }

    //A cost function.
    public int cost(int[] arr)
    {
        return -1;
    }

    //An evaluation criterion.
    
}
