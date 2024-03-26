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
    private int[] initialSolution()
    {
        //create an populate arr
        int[] randomArray= new int[5];
        for(int i=0; i<5; i++){
            randomArray[i]=i;
        }

        //randomise arr
        Random r=new Random();
        
        for (int i=5; i>0; i--){
            int j=r.nextInt(+1);
            int tmp=randomArray[i];
            randomArray[i]=randomArray[j];
            randomArray[j]=tmp;
        }
        return randomArray;
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
