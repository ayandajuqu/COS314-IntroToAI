import ASSIGNMENT_1.ILS;

public class Main {
    public static void main(String[] args) {
        int[][] distanceMatrix = {
            {0, 15, 20, 22, 30},
            {15, 0, 10, 12, 25},
            {20, 10, 0, 8, 22},
            {22, 12, 8, 0, 18},
            {30, 25, 22, 18, 0}
        };

        ILS algo=new ILS(distanceMatrix);
        int[] initialSolution=algo.initialSolution();

        System.out.println("The Initial Solution: ");
        System.out.print("[");
        for(int i=0; i<initialSolution.length-1; i++)
        {
            System.out.print(initialSolution[i]+ " ");
        }
        System.out.println(initialSolution[4]+"]");

        algo.hillClimbAlgo(initialSolution);
    }
}
