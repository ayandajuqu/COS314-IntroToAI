package ASSIGNMENT_1;
//import java.util.Arrays;
import java.util.Random;
import java.io.PrintWriter;
import java.io.IOException;

public class ILS 
{
    int[][] distanceCost;

    public ILS(int[][]distanceCost)
    {
        this.distanceCost=distanceCost;
    }

    //fine
    @SuppressWarnings("unused")
    public int[] initialSolution()
    {
        //create an populate arr
        int[] randomArray= new int[6];
        for(int i=0; i<5; i++){
            randomArray[i]=i;
        }
        randomArray[0]=0;
        randomArray[5]=0;

        //randomise arr
        Random r=new Random();
        
        for (int i=4; i>1; i--)
        {
            int j=r.nextInt(i+1);
            int tmp=randomArray[i];
            randomArray[i]=randomArray[j];
            randomArray[j]=tmp;
        }
        return randomArray;
    }

    //fine (I hope)
    @SuppressWarnings("unused")
    public int[] hillClimbAlgo(int[] randomArray)
    {
        
        int[] oldSolution=new int[6];
        oldSolution=randomArray.clone();
        int initialDistance=totalDistance(oldSolution);

        int[] newSolution=new int[6];
        newSolution=randomArray.clone();
        boolean optimum=false;
        int iterations=0;

        try (PrintWriter writer = new PrintWriter("ILS_txt.txt", "UTF-8"))
        {

            while(!optimum)
            {
                optimum=true;
                for(int i=1; i<4; i++)
                {
                    for(int k=i+1; k<5; k++)
                    {
                        //swapping
                        int temp=newSolution[i];
                        newSolution[i]=newSolution[k];
                        newSolution[k]=temp;
                        
                        //get total distance
                        int newDistance=totalDistance(newSolution);
                        
                        System.out.println("Iteration: " + iterations);
                        writer.println(iterations + "\t" + initialDistance);
                        System.out.println("Current Solution: ");
                        for (int j = 0; j < newSolution.length; j++) 
                        {
                            System.out.print(newSolution[j] + " ");
                        }
                        System.out.println("Initial Distance: " + initialDistance);
                        System.out.println("Current Distance: " + newDistance);
                        //get comparision
                        if (newDistance<initialDistance)
                        {
                            optimum=false;
                            oldSolution=newSolution;
                            initialDistance=newDistance;
                            System.out.println("---Found a better solution!---");
                        }
                        iterations++;
                    }    
                }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        System.out.println("The optimum distance is: " + initialDistance);
        
        return oldSolution;
    }

    //fine
    private int totalDistance(int[] curr)
    {
        int total=0;
        for(int i=0; i<5;i++){
            total+=distanceCost[curr[i]][curr[i+1]];
        }
        total+=distanceCost[curr[5]][curr[0]];
        return total;
    }
}
