package model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Observable;
import java.util.Observer;

import static java.lang.Double.parseDouble;
import static ui.Main.*;

public class Tab2 implements Observer {
    private static final Font SUMMARY_LABEL_HEADING_FONT = new Font("Arial", 15);
    private static final Font SUMMARY_LABEL_VALUE_FONT = new Font("Arial bold", 25);

    private  String startingWeight;
    private  String currentWeight;
    private  String remainingWeight;
    private  String lostWeight;
    private Label progressValue;

    private String name;

    public Tab2(String name) {
        this.name = name;
        updateWeights();
    }

    public Tab createTab2() {
        Tab tab2 = new Tab(name);
        tab2.setClosable(false);

        tab2.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Label startHeading = createLabel("Start", SUMMARY_LABEL_HEADING_FONT, Pos.CENTER, true);
                Label currentHeading = createLabel("Current", SUMMARY_LABEL_HEADING_FONT, Pos.CENTER, true);
                Label goalHeading = createLabel("Goal", SUMMARY_LABEL_HEADING_FONT, Pos.CENTER, true);
                Label lossHeading = createLabel("You lost", SUMMARY_LABEL_HEADING_FONT, Pos.BOTTOM_CENTER, true);
                Label remainingHeading = createLabel("Remaining", SUMMARY_LABEL_HEADING_FONT, Pos.CENTER, true);
                Label progressHeading =
                        createLabel("       Progress", SUMMARY_LABEL_HEADING_FONT, Pos.BOTTOM_CENTER, false);

                Label startValue = createLabel("", SUMMARY_LABEL_VALUE_FONT, Pos.CENTER, false);

                startingWeight = getStartingWeight();
                startValue.setText(startingWeight + " lb");

                Label currentValue = createLabel("", SUMMARY_LABEL_VALUE_FONT, Pos.CENTER, false);

                currentWeight = getCurrentWeight();
                currentValue.setText(currentWeight + " lb");

                Label goalValue = createLabel("", SUMMARY_LABEL_VALUE_FONT, Pos.CENTER, false);
                try {
                    goalValue.setText(Double.parseDouble(weightGoal) + " lb");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Label lossValue = createLabel("", SUMMARY_LABEL_VALUE_FONT, Pos.CENTER, false);
                lostWeight = getLostWeight();
                lossValue.setText(lostWeight + " lb");

                Label remainingValue = createLabel("", SUMMARY_LABEL_VALUE_FONT, Pos.CENTER, false);
                remainingWeight = getRemainingWeight();
                remainingValue.setText(remainingWeight + " lb");

                VBox startVBox = new VBox(10, startHeading, startValue);
                VBox currentVBox = new VBox(10, currentHeading, currentValue);
                VBox goalVBox = new VBox(10, goalHeading, goalValue);
                VBox lossVBox = new VBox(10, lossHeading, lossValue);
                VBox remainingVBox = new VBox(10, remainingHeading, remainingValue);

                ProgressBar progressBar = createProgressBar();

                HBox progressHBox = new HBox(10, progressHeading, progressBar, progressValue);

                GridPane summaryGridPane = new GridPane();
                summaryGridPane.setAlignment(Pos.TOP_CENTER);
                summaryGridPane.setHgap(40);
                summaryGridPane.setVgap(60);
                summaryGridPane.setPadding(new Insets(30, 15, 15, 15));

                summaryGridPane.add(startVBox, 0, 0);
                summaryGridPane.add(currentVBox, 1, 0);
                summaryGridPane.add(goalVBox, 2, 0);
                summaryGridPane.add(progressHBox, 0, 1, 3, 1);
                summaryGridPane.add(lossVBox, 0, 2);
                summaryGridPane.add(remainingVBox, 2, 2);

                for (int i = 0; i < 3; i++) {
                    ColumnConstraints columnConstraint = new ColumnConstraints(100);
                    summaryGridPane.getColumnConstraints().add(columnConstraint);
                }

                tab2.setContent(summaryGridPane);

            }
        });
        return tab2;
    }

    public void updateWeights() {
        startingWeight = getStartingWeight();
        currentWeight = getCurrentWeight();
        remainingWeight = getRemainingWeight();
        lostWeight = getLostWeight();
    }

    private Label createLabel(String labelName, Font font, Pos position, Boolean underline) {
        Label label = new Label(labelName);
        label.setFont(font);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(position);
        label.setUnderline(underline);

        return label;
    }

    private ProgressBar createProgressBar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.setMinWidth(200);

        progressValue = createLabel("", new Font("Arial bold", 18), Pos.CENTER, false);
        try {
            int totalWeightToLose = (int) (Double.parseDouble(weightRecords.get(0).getWeight()) - Double.parseDouble(weightGoal));
            int progress = (int) ((Double.parseDouble(weightRecords.get(0).getWeight())
                    - Double.parseDouble(currentWeight)) / totalWeightToLose * 100);
            if (progress > 100) {
                progress = 100;
            } else if (progress < 0) {
                progress = 0;
            }
            progressBar.setProgress(((double) progress) / 100);
            progressValue.setText(Integer.toString(progress) + "%");

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return progressBar;
    }

    public String getStartingWeight() {
        if (!(weightRecords.get(0).getWeight().equals(""))) {
            return weightRecords.get(0).getWeight();
        }
        return "";
    }

    public String getCurrentWeight() {
        int j = 0;
        while (!(weightRecords.get(j).getWeight().equals("")) && j < NUMBER_OF_MONTHS_IN_YEAR - 1) {
            j++;
        }
        if (j > 0) {
            if (j == NUMBER_OF_MONTHS_IN_YEAR - 1
                    && !(weightRecords.get((int) NUMBER_OF_MONTHS_IN_YEAR - 1).getWeight().equals(""))) {
                return weightRecords.get(j).getWeight();
            } else {
                return weightRecords.get(j - 1).getWeight();
            }
        }
        return "";
    }

    public String getRemainingWeight() {
        try {
            remainingWeight = Integer.toString((int) (Double.parseDouble(currentWeight) - Double.parseDouble(weightGoal)));
            if (Integer.parseInt(remainingWeight) < 0) {
                remainingWeight = "0";
            }
            return remainingWeight;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getLostWeight() {
        try {
            lostWeight = Integer.toString((int) (parseDouble(weightRecords.get(0).getWeight())
                    - parseDouble(currentWeight)));
            if (Integer.parseInt(lostWeight) < 0) {
                lostWeight = "0";
            }
            return lostWeight;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void update(Observable o, Object arg) {
        updateWeights();
    }
}

