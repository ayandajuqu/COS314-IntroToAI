package ASSIGHMENT_3;
import java.util.List;

public class Dataset {
    private List<double[]> features;
    private List<Integer> labels;

    public Dataset(List<double[]> features, List<Integer> labels) {
        this.features = features;
        this.labels = labels;
    }

    public List<double[]> getFeatures() {
        return features;
    }

    public List<Integer> getLabels() {
        return labels;
    }
}