package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Tab1;
import model.Tab2;
import model.Tab3;
import model.WeightRecord;
import utility.JsonFileIO;


public class Main extends Application {
    public static final int NUMBER_OF_MONTHS_IN_YEAR = 12;

    public static ObservableList<WeightRecord> weightRecords;
    public static String weightGoal;

    private static final String TAB_1_NAME = "History";
    private static final String TAB_2_NAME = "Summary";
    private static final String TAB_3_NAME = "Graph";


    private static final int SCENE_WIDTH = 500;
    private static final int SCENE_HEIGHT = 405;


    @Override
    public void start(Stage primaryStage) throws Exception {

        weightRecords = FXCollections.observableArrayList();
        for (int i = 1; i <= NUMBER_OF_MONTHS_IN_YEAR; i++) {
            weightRecords.add(new WeightRecord(Integer.toString(i), ""));
        }

        JsonFileIO jsonFileIO = new JsonFileIO();
        jsonFileIO.read();

        Tab tab1 = new Tab1(TAB_1_NAME).createTab1();
        Tab tab2 = new Tab2(TAB_2_NAME).createTab2();
        Tab tab3 = new Tab3(TAB_3_NAME).createTab3();

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

    @Override
    public void stop() throws Exception {
        JsonFileIO jsonFileIO = new JsonFileIO();
        jsonFileIO.write(weightRecords, weightGoal);
        super.stop();
    }
}
