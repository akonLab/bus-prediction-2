package services;

import base.AIAPIFile;
import base.APIConn;
import com.google.gson.JsonObject;
import models.AIBusDataAtMinuteModel;
import models.AIBusDataModel;

import java.util.HashMap;
import java.util.List;

public class BusService {
    //old
    private final APIConn conn = new APIConn();
    boolean isEmpty = false;
    //from file
    private final AIAPIFile aiapiFile = new AIAPIFile();

    public BusService() {
    }

    //from file
    public HashMap<String, List<AIBusDataAtMinuteModel>> getDataFromFile() {
        return aiapiFile.getFromFileData();
    }

    public HashMap<String, AIBusDataAtMinuteModel> getDataFromApi() {
        HashMap<String, AIBusDataAtMinuteModel> data = new HashMap<>();

        for (JsonObject object : conn.getBusesByLineCode(15)) {//O(n)
            data.put(
                    object.get("TSCode").getAsString(),
                    new AIBusDataAtMinuteModel(
                            object.get("RecordedTime").getAsString(),
                            object.get("Longitude").getAsDouble(),
                            object.get("Latitude").getAsDouble()
                    ));

        }
        return data;
    }

    public boolean isFileEmpty() {
        return aiapiFile.isEmpty();
    }

    public void saveAIBusModelToAIFile(List<AIBusDataModel> list) {
        aiapiFile.rewriteAIFile(list);
    }
}