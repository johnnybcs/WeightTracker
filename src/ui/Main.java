package ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.WeightRecord;

import static java.lang.Double.parseDouble;

public class Main extends Application {
    public static ObservableList<WeightRecord> weightRecords;
    public static String weightGoal;

    private static final String TAB_1_NAME = "History";
    private static final String TAB_2_NAME = "Summary";
    private static final String TAB_3_NAME = "Graph";
    private static final String COLUMN_1_NAME = "Month";
    private static final String COLUMN_2_NAME = "Weight (lb)";
    private static final String X_AXIS_NAME = "Month";
    private static final String Y_AXIS_NAME = "Weight (lb)";
    private static final int SCENE_WIDTH = 500;
    private static final int SCENE_HEIGHT = 405;
    private static final int ENTER_WEIGHT_BUTTON_WIDTH = 124;
    private static final int ENTER_WEIGHT_TEXTFIELD_WIDTH = 124;
    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    public static final int ENTER_WEIGHT_BUTTON_HEIGHT = 34;
    public static final String ERROR_MESSAGE = "Please enter a valid number";
    public static final Font SUMMARY_LABEL_HEADING_FONT = new Font("Arial", 15);
    public static final Font SUMMARY_LABEL_VALUE_FONT = new Font("Arial bold", 25);
    public String currentWeight;
    public int lostWeight;
    public int remainingWeight;
    public int progress;
    public double weightGoalAsNumber;

    @Override
    public void start(Stage primaryStage) throws Exception {

        weightGoal = "";

        weightRecords = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            weightRecords.add(new WeightRecord(Integer.toString(i), ""));
        }

        TableView tableView = new TableView();

        TableColumn<String, WeightRecord> column1 = new TableColumn<>(COLUMN_1_NAME);
        column1.setCellValueFactory(new PropertyValueFactory<>("month"));
        column1.setStyle("-fx-alignment: CENTER");
        column1.setSortable(false);

        TableColumn column2 = new TableColumn<>(COLUMN_2_NAME);
        column2.setCellValueFactory(new PropertyValueFactory("weight"));
        column2.setStyle("-fx-alignment: CENTER");
        column2.setSortable(false);

        tableView.setItems(weightRecords);
        tableView.getColumns().setAll(column1, column2);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        BorderPane tab1BorderPane = new BorderPane();
        tab1BorderPane.setLeft(tableView);

        Label errorMessage = new Label();
        errorMessage.setFont(new Font("Arial italic", 14));

        Label setWeightGoalHeading = new Label("WEIGHT GOAL");
        Label setWeightGoalValue = new Label();

        setWeightGoalHeading.setFont(new Font("Arial bold", 15));
        setWeightGoalHeading.setUnderline(true);

        setWeightGoalValue.setFont(new Font("Arial bold", 30));


        Slider slider = new Slider();
        slider.setMaxWidth(230);
        slider.setMin(50);
        slider.setMax(250);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);
        slider.setMinorTickCount(5);
        slider.setSnapToTicks(true);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                weightGoal = Integer.toString((int) (newValue.doubleValue()));
                setWeightGoalValue.setText(weightGoal + " lb");
            }
        });

        VBox setWeightGoalVBox = new VBox(setWeightGoalHeading, setWeightGoalValue, slider, errorMessage);
        setWeightGoalVBox.setAlignment(Pos.TOP_CENTER);
        setWeightGoalVBox.setMargin(setWeightGoalHeading, new Insets(25, 0, 0, 0));
        setWeightGoalVBox.setMargin(setWeightGoalValue, new Insets(15, 0, 15, 0));
        setWeightGoalVBox.setMargin(errorMessage, new Insets(70, 0, 0, 0));

        tab1BorderPane.setCenter(setWeightGoalVBox);

        TextField enterWeightTextField = new TextField();
        enterWeightTextField.setPrefWidth(ENTER_WEIGHT_TEXTFIELD_WIDTH);
        enterWeightTextField.setPrefHeight(ENTER_WEIGHT_BUTTON_HEIGHT);

        Button enterWeightButton = new Button("Enter Weight");
        enterWeightButton.setPrefWidth(ENTER_WEIGHT_BUTTON_WIDTH);
        enterWeightButton.setPrefHeight(ENTER_WEIGHT_BUTTON_HEIGHT);

        enterWeightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errorMessage.setText("");
                int i = 0;
                while (weightRecords.get(i).getWeight() != "" && i < NUMBER_OF_MONTHS_IN_YEAR - 1) {
                    i++;
                }
                if (i < NUMBER_OF_MONTHS_IN_YEAR) {
                    try {
                        parseDouble(enterWeightTextField.getText());
                    } catch (Exception e) {
                        errorMessage.setText(ERROR_MESSAGE);
                        return;
                    }
                    weightRecords.set(i, new WeightRecord(Integer.toString(i + 1), enterWeightTextField.getText()));
                }
            }
        });

        Image image = new Image("toolbarButtonGraphics/general/Delete24.gif");
        Button deleteButton = new Button();
        ImageView imageView = new ImageView(image);
        deleteButton.setGraphic(imageView);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = 0;
                while (weightRecords.get(i).getWeight() != "" && i < NUMBER_OF_MONTHS_IN_YEAR - 1) {
                    i++;
                }
                if (i > 0) {
                    if (i == 11 && weightRecords.get(11).getWeight() != "") {
                        weightRecords.set(i, new WeightRecord(Integer.toString(i + 1), ""));
                    } else {
                        weightRecords.set(i - 1, new WeightRecord(Integer.toString(i), ""));
                    }
                }
                System.out.println("Deleting " + i);
            }
        });

        HBox enterWeightHBox = new HBox(enterWeightButton, enterWeightTextField, deleteButton);
        enterWeightHBox.setMargin(deleteButton, new Insets(0, 0, 10, 10));
        tab1BorderPane.setBottom(enterWeightHBox);

        // ****************************************************************************************************

        Tab tab1 = new Tab(TAB_1_NAME, tab1BorderPane);
        tab1.setClosable(false);

        Tab tab2 = new Tab(TAB_2_NAME);
        tab2.setClosable(false);

        Tab tab3 = new Tab(TAB_3_NAME);
        tab3.setClosable(false);

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
                    currentWeight = weightRecords.get(j - 1).getWeight();
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


        tab3.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (tab3.isSelected()) {
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

                    tab3.setContent(lineChart);
                }
            }
        });


        TabPane tabPane = new TabPane(tab1, tab2, tab3);

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        primaryStage.setTitle("Weight Tracker");
        primaryStage.setScene(new

                Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
