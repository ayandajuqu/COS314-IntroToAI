package ASSIGNMENT_1;
import java.util.Random;
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
        int[] randomArray= new int[5];
        for(int i=0; i<5; i++){
            randomArray[i]=i;
        }

        //randomise arr
        Random r=new Random();
        
        for (int i=4; i>0; i--){
            int j=r.nextInt(+1);
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
        int[] oldSolution=new int[5];
        oldSolution=randomArray.clone();
        int initialDistance=totalDistance(oldSolution);

        int[] newSolution=new int[5];
        newSolution=randomArray.clone();
        boolean optimum=false;

        while(!optimum)
        {
            optimum=true;
            for(int i=0; i<4; i++)
            {
                for(int k=i+1; k<5; k++)
                {
                    //swapping
                    int temp=newSolution[i];
                    newSolution[i]=newSolution[k];
                    newSolution[k]=temp;
                    
                    //get total distance
                    int newDistance=totalDistance(newSolution);
        
                    //get comparision
                    if (newDistance<initialDistance)
                    {
                        optimum=false;
                        oldSolution=newSolution;
                        initialDistance=newDistance;
                    }
                }    
            }
        }
        return oldSolution;
    }

    //fine
    private int totalDistance(int[] curr){
        int total=0;
        for(int i=0; i<4;i++){
            total+=distanceCost[curr[i]][curr[i+1]];
        }
        total+=distanceCost[curr[4]][curr[0]];
        return total;
    }
}
