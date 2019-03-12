package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.WeightRecord;

public class Main extends Application {
    public static final String TAB_1_NAME = "History";
    public static final String TAB_2_NAME = "Summary";
    public static final String TAB_3_NAME = "Graph";
    public static final String COLUMN_1_NAME = "Week";
    public static final String COLUMN_2_NAME = "Weight (lbs)";



    @Override
    public void start(Stage primaryStage) throws Exception{

        TableView tableView = new TableView();

        TableColumn<String, WeightRecord> column1 = new TableColumn<>(COLUMN_1_NAME);
        column1.setCellValueFactory(new PropertyValueFactory<>("week"));
        column1.setStyle("-fx-alignment: CENTER");

        TableColumn<String, WeightRecord> column2 = new TableColumn<>(COLUMN_2_NAME);
        column2.setCellValueFactory(new PropertyValueFactory("weight"));
        column2.setStyle("-fx-alignment: CENTER");

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        tableView.getItems().add(new WeightRecord("1", "250"));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setEditable(true);
        column2.setEditable(true);

        Tab tab1 = new Tab(TAB_1_NAME, tableView);
        tab1.setClosable(false);

        Tab tab2 = new Tab(TAB_2_NAME);
        tab2.setClosable(false);

        Tab tab3 = new Tab(TAB_3_NAME);
        tab3.setClosable(false);

        TabPane tabPane = new TabPane(tab1, tab2, tab3);

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        primaryStage.setTitle("Weight Tracker");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
