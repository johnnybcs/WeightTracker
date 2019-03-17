package utility;

import model.WeightRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

import static ui.Main.weightGoal;
import static ui.Main.weightRecords;

public class JsonFileIO {

    private static final File jsonDataFile = new File("savedData.json");

    public void read() {
        String jsonString;
        try {
            Scanner scanner = new Scanner(jsonDataFile);
            jsonString = scanner.useDelimiter("\\Z").next();

            JSONArray jsonArray = new JSONArray(jsonString);

            JSONObject weightGoalJson = (JSONObject) jsonArray.get(1);
            weightGoal = (String) weightGoalJson.get("weightGoal");

            JSONObject weightRecordsJson = (JSONObject) jsonArray.get(0);

            for (int i = 0; i < 12; i++) {
                WeightRecord weightRecord =
                        new WeightRecord(Integer.toString(i + 1), weightRecordsJson.getString(Integer.toString(i + 1)));
                weightRecords.set(i, weightRecord);
                if (!weightRecords.get(i).getWeight().matches("\\d*.*\\d.*\\d*")) {
//                    System.out.println(weightRecords.get(i).getWeight());
                    weightRecords.get(i).setWeight("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void write(List<WeightRecord> weightRecords, String goalWeight) {
        try {
            Jsonifier jsonifier = new Jsonifier();
            JSONArray jsonArray = jsonifier.dataToJSONArray(weightRecords, goalWeight);

            FileWriter fileWriter = new FileWriter(jsonDataFile);
            fileWriter.write(jsonArray.toString());
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
