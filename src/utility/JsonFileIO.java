package utility;

import model.WeightRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Scanner;

import static ui.Main.weightGoal;
import static ui.Main.weightRecords;

public class JsonFileIO {

    public static final File jsonDataFile = new File("data.json");

    public static void read() {
        String jsonString;
        try {
            Scanner scanner = new Scanner(jsonDataFile);
            jsonString = scanner.useDelimiter("\\Z").next();

            JSONArray jsonArray = new JSONArray(jsonString);

            JSONObject weightRecordsJson = (JSONObject) jsonArray.get(0);
            for (int i = 0; i < jsonArray.length(); i++) {
                WeightRecord weightRecord =
                        new WeightRecord(Integer.toString(i + 1), weightRecordsJson.getString(Integer.toString(i + 1)));
                weightRecords.add(weightRecord);
            }
            JSONObject weightGoalJson = (JSONObject) jsonArray.get(1);
            weightGoal = (String) weightGoalJson.get("weightGoal");
        } catch (Exception e) {
        }
    }
}
