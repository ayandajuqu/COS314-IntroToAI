package ASSIGHMENT_3.GP;

public class Mushroom {
    public double capDiameter;
    public double capShape;
    public double gillAttachment;
    public double gillColor;
    public double stemHeight;
    public double stemWidth;
    public double stemColor;
    public double season;
    public double mushroomClass;
    public static final int NUM_FEATURES = 8;


    public Mushroom(double capDiameter, double capShape, double gillAttachment, 
        double gillColor, double stemHeight, double stemWidth, double stemColor, 
        double season, double mushroomClass) {
            this.capDiameter = capDiameter;
            this.capShape = capShape;
            this.gillAttachment = gillAttachment;
            this.gillColor = gillColor;
            this.stemHeight = stemHeight;
            this.stemWidth = stemWidth;
            this.stemColor = stemColor;
            this.season = season;
            this.mushroomClass = mushroomClass;
    }

    //override toString method
    @Override
    public String toString() {
        return "Mushroom{" +
                "capDiameter=" + capDiameter +
                ", capShape=" + capShape +
                ", gillAttachment=" + gillAttachment +
                ", gillColor=" + gillColor +
                ", stemHeight=" + stemHeight +
                ", stemWidth=" + stemWidth +
                ", stemColor=" + stemColor +
                ", season=" + season +
                ", mushroomClass=" + mushroomClass +
                '}';
    }

    public double getFeatureAtIndex(int index) {
        if (index == 0) {
            return capDiameter;
        } else if (index == 1) {
            return capShape;
        } else if (index == 2) {
            return gillAttachment;
        } else if (index == 3) {
            return gillColor;
        } else if (index == 4) {
            return stemHeight;
        } else if (index == 5) {
            return stemWidth;
        } else if (index == 6) {
            return stemColor;
        } else if (index == 7) {
            return season;
        } else {
            throw new IllegalArgumentException("Invalid feature index: " + index);
        }
    }
}

