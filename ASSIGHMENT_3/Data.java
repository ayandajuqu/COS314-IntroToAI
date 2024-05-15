package ASSIGHMENT_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    String file="mushroom_train";
    public static Dataset readData(String file) throws IOException {
        List<double[]> features = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                double[] featureValues = new double[parts.length - 1];
                for (int i = 0; i < featureValues.length; i++) {
                    featureValues[i] = Double.parseDouble(parts[i]);
                }
                features.add(featureValues);
                int label = Integer.parseInt(parts[parts.length - 1]);
                labels.add(label);
            }
        }

        return new Dataset(features, labels);
    }
}
