package model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;

import static java.lang.Double.parseDouble;
import static ui.Main.*;

public class Tab3 {
    private static final String X_AXIS_NAME = "Month";
    private static final String Y_AXIS_NAME = "Weight (lb)";

    private String name;


    public Tab3(String name) {
        this.name = name;
    }

    public Tab createTab3() {
        Tab tab3 = new Tab(name);
        tab3.setClosable(false);

        tab3.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tab3.isSelected()) {
                    LineChart lineChart = createLineChart();
                    tab3.setContent(lineChart);
                }
            }
        });
        return tab3;
    }

    private LineChart createLineChart() {
        NumberAxis xAxis = new NumberAxis(1, 12, 1);
        xAxis.setLabel(X_AXIS_NAME);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(Y_AXIS_NAME);

        LineChart lineChart = new LineChart(xAxis, yAxis);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Your Weight");

        for (WeightRecord weightRecord : weightRecords) {
            if (weightRecord.getWeight() != "") {
                double x = parseDouble(weightRecord.getMonth());
                double y = parseDouble(weightRecord.getWeight());
                dataSeries1.getData().add(new XYChart.Data(x, y));
            }
        }

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("Goal");
        if (weightRecords.get(0).getWeight() != "" && weightGoal != "") {
            dataSeries2.getData().add(new XYChart.Data(1,
                    parseDouble(weightRecords.get(0).getWeight())));

            dataSeries2.getData().add(new XYChart.Data(NUMBER_OF_MONTHS_IN_YEAR,
                    parseDouble(weightGoal)));
        }

        lineChart.getData().add(dataSeries1);
        lineChart.getData().add(dataSeries2);

        return lineChart;
    }
}
