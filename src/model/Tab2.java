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
import static ui.Main.*;

import static java.lang.Double.parseDouble;
import static ui.Main.NUMBER_OF_MONTHS_IN_YEAR;

public class Tab2 {
    private static final Font SUMMARY_LABEL_HEADING_FONT = new Font("Arial", 15);
    private static final Font SUMMARY_LABEL_VALUE_FONT = new Font("Arial bold", 25);

    private String currentWeight;
    private int lostWeight;
    private int remainingWeight;
    private int progress;
    private double weightGoalAsNumber;
    
    private String name;

    public Tab2(String name) {
        this.name = name;
    }

    public Tab createTab2() {
        Tab tab2 = new Tab(name);
        tab2.setClosable(false);

        tab2.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Label startHeading = new Label("Start");
                startHeading.setFont(SUMMARY_LABEL_HEADING_FONT);
                startHeading.setMaxWidth(Double.MAX_VALUE);
                startHeading.setAlignment(Pos.CENTER);
                startHeading.setUnderline(true);

                Label currentHeading = new Label("Current");
                currentHeading.setFont(SUMMARY_LABEL_HEADING_FONT);
                currentHeading.setMaxWidth(Double.MAX_VALUE);
                currentHeading.setAlignment(Pos.CENTER);
                currentHeading.setUnderline(true);

                Label goalHeading = new Label("Goal");
                goalHeading.setFont(SUMMARY_LABEL_HEADING_FONT);
                goalHeading.setMaxWidth(Double.MAX_VALUE);
                goalHeading.setAlignment(Pos.CENTER);
                goalHeading.setUnderline(true);

                Label startValue = new Label();
                startValue.setFont(SUMMARY_LABEL_VALUE_FONT);
                startValue.setMaxWidth(Double.MAX_VALUE);
                startValue.setAlignment(Pos.CENTER);
                if (weightRecords.get(0).getWeight() != "") {
                    startValue.setText(weightRecords.get(0).getWeight() + " lb");
                }

                Label currentValue = new Label("");
                currentValue.setFont(SUMMARY_LABEL_VALUE_FONT);
                currentValue.setMaxWidth(Double.MAX_VALUE);
                currentValue.setAlignment(Pos.CENTER);

                int j = 0;
                while (weightRecords.get(j).getWeight() != "" && j < NUMBER_OF_MONTHS_IN_YEAR - 1) {
                    j++;
                }

                if (j > 0) {
                    if (j == 11 && weightRecords.get(11).getWeight() != "") {
                        currentWeight = weightRecords.get(j).getWeight();
                    } else {
                        currentWeight = weightRecords.get(j - 1).getWeight();
                    }
                    currentValue.setText(currentWeight + " lb");

                }


                Label goalValue = new Label("");
                goalValue.setFont(SUMMARY_LABEL_VALUE_FONT);
                goalValue.setMaxWidth(Double.MAX_VALUE);
                goalValue.setAlignment(Pos.CENTER);

                try {
                    weightGoalAsNumber = Double.parseDouble(weightGoal);
                    goalValue.setText(weightGoal + " lb");
                } catch (Exception e) {
                }


                VBox startVBox = new VBox(10, startHeading, startValue);
                VBox currentVBox = new VBox(10, currentHeading, currentValue);
                VBox goalVBox = new VBox(10, goalHeading, goalValue);

                // ****************************************************************************************************


                Label lossHeading = new Label("You lost");
                lossHeading.setFont(SUMMARY_LABEL_HEADING_FONT);
                lossHeading.setMaxWidth(Double.MAX_VALUE);
                lossHeading.setAlignment(Pos.BOTTOM_CENTER);
                lossHeading.setUnderline(true);

                Label remainingHeading = new Label("Remaining");
                remainingHeading.setFont(SUMMARY_LABEL_HEADING_FONT);
                remainingHeading.setMaxWidth(Double.MAX_VALUE);
                remainingHeading.setAlignment(Pos.CENTER);
                remainingHeading.setUnderline(true);

                Label lossValue = new Label();
                lossValue.setFont(SUMMARY_LABEL_VALUE_FONT);
                lossValue.setMaxWidth(Double.MAX_VALUE);
                lossValue.setAlignment(Pos.CENTER);
                try {
                    lostWeight = (int) (parseDouble(weightRecords.get(0).getWeight())
                            - parseDouble(currentWeight));
                    if (lostWeight < 0) {
                        lostWeight = 0;
                    }

                    lossValue.setText(Integer.toString(lostWeight) + " lb");
                } catch (
                        Exception e) {

                }


                Label remainingValue = new Label();
                remainingValue.setFont(SUMMARY_LABEL_VALUE_FONT);
                remainingValue.setMaxWidth(Double.MAX_VALUE);
                remainingValue.setAlignment(Pos.CENTER);
                try {
                    remainingWeight = (int) (Double.parseDouble(currentWeight) - weightGoalAsNumber);
                    if (remainingWeight < 0) {
                        remainingWeight = 0;
                    }
                    remainingValue.setText(Integer.toString(remainingWeight) + " lb");
                } catch (
                        Exception e) {

                }

                VBox lossVBox = new VBox(10, lossHeading, lossValue);
                VBox remainingVBox = new VBox(10, remainingHeading, remainingValue);


                // ****************************************************************************************************


                Label progressHeading = new Label("       Progress");
                progressHeading.setFont(SUMMARY_LABEL_HEADING_FONT);
                progressHeading.setMaxWidth(Double.MAX_VALUE);
                progressHeading.setAlignment(Pos.BOTTOM_CENTER);

                ProgressBar progressBar = new ProgressBar();
                progressBar.setProgress(0);
                progressBar.setMinWidth(200);

                Label progressValue = new Label();
                progressValue.setFont(new

                        Font("Arial bold", 18));
                progressValue.setMaxHeight(Double.MAX_VALUE);

                try {
                    int totalWeightToLose = (int) (Double.parseDouble(weightRecords.get(0).getWeight()) - weightGoalAsNumber);
                    progress = (int) ((Double.parseDouble(weightRecords.get(0).getWeight())
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
                }

                HBox progressHBox = new HBox(10, progressHeading, progressBar, progressValue);

                // ****************************************************************************************************

                GridPane summaryGridPane = new GridPane();
                summaryGridPane.setAlignment(Pos.TOP_CENTER);
                summaryGridPane.setHgap(40);
                summaryGridPane.setVgap(60);
                summaryGridPane.setPadding(new

                        Insets(30, 15, 15, 15));

                summaryGridPane.add(startVBox, 0, 0);
                summaryGridPane.add(currentVBox, 1, 0);
                summaryGridPane.add(goalVBox, 2, 0);

                summaryGridPane.add(progressHBox, 0, 1, 3, 1);

                summaryGridPane.add(lossVBox, 0, 2);
                summaryGridPane.add(remainingVBox, 2, 2);

                for (
                        int i = 0;
                        i < 3; i++) {
                    ColumnConstraints columnConstraint = new ColumnConstraints(100);
                    summaryGridPane.getColumnConstraints().add(columnConstraint);
                }


                tab2.setContent(summaryGridPane);
            }
        });
        return tab2;
    }
}

