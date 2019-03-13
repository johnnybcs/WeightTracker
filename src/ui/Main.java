package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.WeightRecord;

public class Main extends Application {
    public static ObservableList<WeightRecord> weightRecords;

    private static final String TAB_1_NAME = "History";
    private static final String TAB_2_NAME = "Summary";
    private static final String TAB_3_NAME = "Graph";
    private static final String COLUMN_1_NAME = "Month";
    private static final String COLUMN_2_NAME = "Weight (lbs)";
    private static final String X_AXIS_NAME = "Month";
    private static final String Y_AXIS_NAME = "Weight (lbs)";
    private static final int SCENE_WIDTH = 500;
    private static final int SCENE_HEIGHT = 405;
    private static final int ENTER_WEIGHT_BUTTON_WIDTH = 124;
    private static final int ENTER_WEIGHT_TEXTFIELD_WIDTH = 124;
    private static final int NUMBER_OF_MONTHS_IN_YEAR = 12;
    public static final int ENTER_WEIGHT_BUTTON_HEIGHT = 34;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // ********************************************************************

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

        TextField enterWeightTextField = new TextField();
        enterWeightTextField.setPrefWidth(ENTER_WEIGHT_TEXTFIELD_WIDTH);
        enterWeightTextField.setPrefHeight(ENTER_WEIGHT_BUTTON_HEIGHT);

        Button enterWeightButton = new Button("Enter Weight");
        enterWeightButton.setPrefWidth(ENTER_WEIGHT_BUTTON_WIDTH);
        enterWeightButton.setPrefHeight(ENTER_WEIGHT_BUTTON_HEIGHT);

        enterWeightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = 0;
                while (weightRecords.get(i).getWeight() != "" && i < NUMBER_OF_MONTHS_IN_YEAR - 1) {
                    i++;
                }
                if (i < NUMBER_OF_MONTHS_IN_YEAR) {
                    try {
                        Double.parseDouble(enterWeightTextField.getText());
                    } catch (Exception e) {
                        System.out.println("EXCEPTION THROWN!!!");
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

        HBox hBox = new HBox(enterWeightButton, enterWeightTextField, deleteButton);
        hBox.setMargin(deleteButton, new Insets(0, 0, 10, 10));
        tab1BorderPane.setBottom(hBox);

        Tab tab1 = new Tab(TAB_1_NAME, tab1BorderPane);
        tab1.setClosable(false);

        Tab tab2 = new Tab(TAB_2_NAME);
        tab2.setClosable(false);

        Tab tab3 = new Tab(TAB_3_NAME);
        tab3.setClosable(false);

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
                            double x = Double.parseDouble(weightRecord.getMonth());
                            double y = Double.parseDouble(weightRecord.getWeight());
                            dataSeries1.getData().add(new XYChart.Data(x, y));
                        }
                    }

                    lineChart.getData().add(dataSeries1);

                    tab3.setContent(lineChart);
                }
            }
        });


        TabPane tabPane = new TabPane(tab1, tab2, tab3);

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        primaryStage.setTitle("Weight Tracker");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
