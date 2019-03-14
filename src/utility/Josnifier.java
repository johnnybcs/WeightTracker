package utility;

import model.WeightRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Josnifier {

    public static JSONObject weightGoalToJson(String weightGoal) {
        JSONObject weightGoalJson = new JSONObject();
        weightGoalJson.put("weightGoal", weightGoal);
        return weightGoalJson;
    }

    public static JSONObject weightRecordsToJson(List<WeightRecord> weightRecords) {
        JSONObject weightRecordsJson = new JSONObject();
        for (int i = 0; i < weightRecords.size(); i++) {
            weightRecordsJson.put(Integer.toString(i + 1), weightRecords.get(i).getWeight());
        }
        return weightRecordsJson;
    }

    public static JSONArray dataToJSONArray(List<WeightRecord> weightRecords, String goalWeight) {
        JSONArray JSONArray = new JSONArray();
        JSONArray.put(weightRecordsToJson(weightRecords));
        JSONArray.put(weightGoalToJson(goalWeight));

        return JSONArray;
    }
}
