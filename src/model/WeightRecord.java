package model;

public class WeightRecord {
    private String week;
    private String weight;

    public WeightRecord(String week, String weight) {
        this.week = week;
        this.weight = weight;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
