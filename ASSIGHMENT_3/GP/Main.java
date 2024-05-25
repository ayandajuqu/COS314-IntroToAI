package ASSIGHMENT_3.GP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String trainFile = "ASSIGHMENT_3/mushroom_train.csv";
        String testFile = "ASSIGHMENT_3/mushroom_train.csv";

        List<Mushroom> trainMushrooms = loadMushrooms(trainFile);
        List<Mushroom> testMushrooms = loadMushrooms(testFile);

        long seed = 42;
        System.out.println("--------Seed val:" + seed);
        GPC gpc = new GPC(seed);
        gpc.run(trainMushrooms);

        evaluateModel(gpc, testMushrooms);
    }

    private static List<Mushroom> loadMushrooms(String fileName) {
        List<Mushroom> mushrooms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double capDiameter = Double.parseDouble(parts[0]);
                double capShape = Double.parseDouble(parts[1]);
                double gillAttachment = Double.parseDouble(parts[2]);
                double gillColor = Double.parseDouble(parts[3]);
                double stemHeight = Double.parseDouble(parts[4]);
                double stemWidth = Double.parseDouble(parts[5]);
                double stemColor = Double.parseDouble(parts[6]);
                double season = Double.parseDouble(parts[7]);
                double mushroomClass = Double.parseDouble(parts[8]);

                mushrooms.add(new Mushroom(capDiameter, capShape, gillAttachment, gillColor, stemHeight, stemWidth, stemColor, season, mushroomClass));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mushrooms;
    }

    private static void evaluateModel(GPC gpc, List<Mushroom> testMushrooms) {
        int correctPredictions = 0;
        int truePositive = 0;
        int trueNegative = 0;
        int falsePositive = 0;
        int falseNegative = 0;

        for (Mushroom mushroom : testMushrooms) {
            double prediction = gpc.predict(mushroom);
            int predictedClass = (prediction >= 0.5) ? 1 : 0;
            int actualClass = (int) mushroom.mushroomClass;

            if (predictedClass == actualClass) {
                correctPredictions++;
                if (predictedClass == 1) {
                    truePositive++;
                } else {
                    trueNegative++;
                }
            } else {
                if (predictedClass == 1) {
                    falsePositive++;
                } else {
                    falseNegative++;
                }
            }
        }

        double accuracy = (double) correctPredictions / testMushrooms.size();
        double specificity = (double) trueNegative / (trueNegative + falsePositive);
        double sensitivity = (double) truePositive / (truePositive + falseNegative);
        double precision = (double) truePositive / (truePositive + falsePositive);
        double fMeasure = 2 * ((precision * sensitivity) / (precision + sensitivity));
        System.out.println();
        System.out.println("Accuracy on test set: " + (accuracy * 100) + "%");
        System.out.println("Specificity: " + (specificity * 100) + "%");
        System.out.println("Sensitivity: " + (sensitivity * 100) + "%");
        System.out.println("F-measure: " + (fMeasure * 100) + "%");
    }
}
