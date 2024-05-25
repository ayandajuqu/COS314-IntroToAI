package ASSIGHMENT_3.ANN;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Load training data
        List<double[]> inputTrainingDataset = new ArrayList<>();
        List<double[]> targetTrainingDataset = new ArrayList<>();
        loadDataset("ASSIGHMENT_3/mushroom_train.csv", inputTrainingDataset, targetTrainingDataset);

        // Load test data
        List<double[]> inputTestDataset = new ArrayList<>();
        List<double[]> targetTestDataset = new ArrayList<>();
        loadDataset("ASSIGHMENT_3/mushroom_test.csv", inputTestDataset, targetTestDataset);

        // Convert lists to arrays for the neural network
        double[][] inputTrainingArray = inputTrainingDataset.toArray(new double[0][0]);
        double[][] targetTrainingArray = targetTrainingDataset.toArray(new double[0][0]);
        double[][] inputTestArray = inputTestDataset.toArray(new double[0][0]);
        double[][] targetTestArray = targetTestDataset.toArray(new double[0][0]);

        // Create and train the neural network
        NeuralNetwork nn = new NeuralNetwork(8, 10, 1);  
        nn.initialiseWeight();
        nn.fit(inputTrainingArray, targetTrainingArray, 600);  // Train for 600 epochs

        // Evaluate the model on the test dataset
        evaluateModel(nn, inputTestArray, targetTestArray);
    }

    private static void loadDataset(String filePath, List<double[]> inputDataset, List<double[]> targetDataset) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            scanner.nextLine();  // Skip the header line

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");

                double[] inputs = new double[8];  // Adjust based on the number of input features
                for (int i = 0; i < 8; i++) {
                    inputs[i] = Double.parseDouble(fields[i]);
                }
                inputDataset.add(inputs);

                double[] target = {Double.parseDouble(fields[8])};  // Adjust based on the target column
                targetDataset.add(target);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void evaluateModel(NeuralNetwork nn, double[][] inputArray, double[][] targetArray) {
        int TP = 0;  // True Positives
        int TN = 0;  // True Negatives
        int FP = 0;  // False Positives
        int FN = 0;  // False Negatives

        for (int i = 0; i < inputArray.length; i++) {
            double[] prediction = nn.predict(inputArray[i]);
            // Since this is a binary classification problem, we'll say that a prediction > 0.5 is a 1, and otherwise it's a 0
            if ((prediction[0] > 0.5 && targetArray[i][0] == 1)) {
                TP++;
            } else if ((prediction[0] <= 0.5 && targetArray[i][0] == 0)) {
                TN++;
            } else if ((prediction[0] > 0.5 && targetArray[i][0] == 0)) {
                FP++;
            } else if ((prediction[0] <= 0.5 && targetArray[i][0] == 1)) {
                FN++;
            }
        }

        double accuracy = (double) (TP + TN) / (TP + TN + FP + FN);
        double specificity = (double) TN / (TN + FP);
        double sensitivity = (double) TP / (TP + FN);
        double fMeasure = (2 * sensitivity * specificity) / (sensitivity + specificity);

        System.out.println("Accuracy: " + accuracy);
        System.out.println("Specificity: " + specificity);
        System.out.println("Sensitivity: " + sensitivity);
        System.out.println("F-Measure: " + fMeasure);
    }
}
