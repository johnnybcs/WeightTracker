package model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import static ui.Main.*;

public class Tab1 {
    private static final String COLUMN_1_NAME = "Month";
    private static final String COLUMN_2_NAME = "Weight (lb)";
    private static final int ENTER_WEIGHT_BUTTON_WIDTH = 124;
    private static final int SPINNER_WIDTH = 124;
    private static final int ENTER_WEIGHT_BUTTON_HEIGHT = 34;
    private static final String ERROR_MESSAGE = "Please enter a valid number";

    private String spinnerText;
    private String name;
    private Label setWeightGoalValue;
    private Spinner spinner;
    private Label errorMessage;

    public Tab1(String name) {
        this.name = name;
    }

    public Tab createTab1() {
        TableView tableView = createTableView();

        BorderPane tab1BorderPane = new BorderPane();
        tab1BorderPane.setLeft(tableView);

        errorMessage = new Label();
        errorMessage.setFont(new Font("Arial italic", 14));

        Label setWeightGoalHeading = new Label("WEIGHT GOAL");
        setWeightGoalValue = new Label();

        setWeightGoalHeading.setFont(new Font("Arial bold", 15));
        setWeightGoalHeading.setUnderline(true);

        setWeightGoalValue.setFont(new Font("Arial bold", 30));
        setWeightGoalValue.setText(weightGoal + " lb");

        Slider slider = createSlider();

        VBox setWeightGoalVBox = new VBox(setWeightGoalHeading, setWeightGoalValue, slider, errorMessage);
        setWeightGoalVBox.setAlignment(Pos.TOP_CENTER);
        setWeightGoalVBox.setMargin(setWeightGoalHeading, new Insets(25, 0, 0, 0));
        setWeightGoalVBox.setMargin(setWeightGoalValue, new Insets(15, 0, 15, 0));
        setWeightGoalVBox.setMargin(errorMessage, new Insets(70, 0, 0, 0));

        tab1BorderPane.setCenter(setWeightGoalVBox);

        spinner = new Spinner(0, 1000, 0, 1);
        spinner.setEditable(true);
        spinner.setPrefSize(SPINNER_WIDTH, ENTER_WEIGHT_BUTTON_HEIGHT);

        Button enterWeightButton = createEnterWeightButton();
        Button deleteButton = createDeleteButton();

        HBox enterWeightHBox = new HBox(enterWeightButton, spinner, deleteButton);
        enterWeightHBox.setMargin(deleteButton, new Insets(0, 0, 10, 10));

        tab1BorderPane.setBottom(enterWeightHBox);

        Tab tab1 = new Tab(this.name, tab1BorderPane);
        tab1.setClosable(false);

        return tab1;
    }

    private TableView createTableView() {
        TableView<WeightRecord> tableView = new TableView<>();

        TableColumn<WeightRecord, String> column1 = new TableColumn<>(COLUMN_1_NAME);
        column1.setCellValueFactory(new PropertyValueFactory<>("month"));
        column1.setStyle("-fx-alignment: CENTER");
        column1.setSortable(false);

        TableColumn<WeightRecord, String> column2 = new TableColumn<>(COLUMN_2_NAME);
        column2.setCellValueFactory(new PropertyValueFactory<>("weight"));
        column2.setStyle("-fx-alignment: CENTER");
        column2.setSortable(false);

        tableView.setItems(weightRecords);
        tableView.getColumns().setAll(column1, column2);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }

    private Slider createSlider() {
        Slider slider = new Slider();
        slider.setMaxWidth(230);
        slider.setMin(50);
        slider.setMax(250);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);
        slider.setMinorTickCount(5);
        slider.setSnapToTicks(true);
        try {
            slider.setValue(Double.parseDouble(weightGoal));
        } catch (Exception e) {
            e.printStackTrace();
        }

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                weightGoal = Integer.toString((int) (newValue.doubleValue()));
                setWeightGoalValue.setText(weightGoal + " lb");
            }
        });
        return slider;
    }

    private Button createEnterWeightButton() {
        Button enterWeightButton = new Button("Enter Weight");
        enterWeightButton.setPrefWidth(ENTER_WEIGHT_BUTTON_WIDTH);
        enterWeightButton.setPrefHeight(ENTER_WEIGHT_BUTTON_HEIGHT);

        enterWeightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                errorMessage.setText("");
                int i = 0;
                while (!(weightRecords.get(i).getWeight().equals("")) && i < NUMBER_OF_MONTHS_IN_YEAR - 1) {
                    i++;
                }
                if (i < NUMBER_OF_MONTHS_IN_YEAR) {
                    try {
                        spinnerText = spinner.getEditor().getText();
                        Double.parseDouble(spinnerText);
                    } catch (Exception e) {
                        errorMessage.setText(ERROR_MESSAGE);
                        return;
                    }
                    weightRecords.set(i, new WeightRecord(Integer.toString(i + 1), spinnerText));
                }
            }
        });
        return enterWeightButton;
    }

    private Button createDeleteButton() {
        Image image = new Image("toolbarButtonGraphics/general/Delete24.gif");
        Button deleteButton = new Button();
        ImageView imageView = new ImageView(image);
        deleteButton.setGraphic(imageView);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = 0;
                while (!(weightRecords.get(i).getWeight().equals("")) && i < NUMBER_OF_MONTHS_IN_YEAR - 1) {
                    i++;
                }
                if (i > 0) {
                    if (i == 11 && (!weightRecords.get(11).getWeight().equals(""))) {
                        weightRecords.set(i, new WeightRecord(Integer.toString(i + 1), ""));
                    } else {
                        weightRecords.set(i - 1, new WeightRecord(Integer.toString(i), ""));
                    }
                }
            }
        });
        return deleteButton;
    }
}