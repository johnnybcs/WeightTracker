package model;

public class WeightRecord {
    private String month;
    private String weight;

    public WeightRecord(String month, String weight) {
        this.month = month;
        this.weight = weight;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
