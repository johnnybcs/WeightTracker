package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WeightRecordBACKUP {
    private StringProperty weight;
    private StringProperty week;

    public WeightRecordBACKUP(StringProperty weight, StringProperty week) {
        this.weight = weight;
        this.week = week;
    }

    public String getWeight() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight.set(weight);
    }

    public String getWeek() {
        return week.get();
    }

    public StringProperty weekProperty() {
        return week;
    }

    public void setWeek(String week) {
        this.week.set(week);
    }
}
