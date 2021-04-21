package base;

import com.google.gson.*;
import models.AIBusDataAtMinuteModel;
import models.AIBusDataModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AIAPIFile {
    private static final File file = new File("/Users/adaada/IdeaProjects/bus2/src/main/java/base/AIBusDataFile");
    private ArrayList<JsonObject> AIBusDataModels = new ArrayList<>();
    private JsonObject jsonObject;
    private final HashMap<String, AIBusDataModel> AIBusModelHashMap = new HashMap<>();
    boolean isEmpty = false;
    JsonArray array;

    //constructor
    public AIAPIFile() {
        if (refactorFileTextIntoString()) {
            structureTimeList();
//            convertJsonDataIntoAIBusModelHashMap();
        }
    }

    //getter
    public HashMap<String, AIBusDataModel> getAIBusModelHashMap() {
        return AIBusModelHashMap;
    }

    //rewrite file method
    public void rewriteAIFile(List<AIBusDataModel> aiBusDataModels) {
        try {
            FileWriter myWriter = new FileWriter(file);
            System.out.println("AIAPIFile 41, size " + aiBusDataModels.size());

            String content = new Gson().toJson(aiBusDataModels);
            System.out.println("AIAPIFile 42, content " + content);
            myWriter.write("{\"allBusData\": " + content + "}");
            myWriter.close();
            System.out.println("Successfully rewrite(), AIAPIFile 45");
        } catch (IOException e) {
            System.out.println("exeption rewrite(), AIAPIFile 47");
            e.printStackTrace();
        }
    }

    //get text from file methods
    public boolean refactorFileTextIntoString() {
        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

            JsonParser parser = new JsonParser();
            jsonObject = (JsonObject) parser.parse(content);
        } catch (NullPointerException | IOException | ClassCastException e) {
            System.out.println("AIAPIFile 58, bad exeption");
            isEmpty = true;
            return false;
        }
        System.out.println("AIAPIFile 61, good");
        return true;
    }

    public void structureTimeList() {
        ArrayList<JsonObject> list = new ArrayList<>();

        if (jsonObject != null) {
            array = jsonObject.getAsJsonArray("allBusData");
            System.out.println("AIAPIFile 76, js arr " + array);
            for (JsonElement jsonObject : array) {
                try {
                    list.add(jsonObject.getAsJsonObject());
                } catch (NumberFormatException | IllegalStateException exception) {
                    exception.getCause();
                }
            }
        } else {
            System.out.println("AIAPIFILE 79, nope, json is empty");
        }
        System.out.println("AIAPIFILE 88, get jsonobj list size = " + list);
        AIBusDataModels = list;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

//    public void convertJsonDataIntoAIBusModelHashMap() {
//        System.out.println(AIBusDataModels);
//        try {
//            for (JsonObject object : Objects.requireNonNull(AIBusDataModels)) {//O(n)
//                AIBusModelHashMap.put(
//                        object.get("TsCode").getAsString(),
//                        new AIBusDataModel(
//                                object.get("TsCode").getAsString(),
//                                getAIBusDataAtMinuteModelList(array,object.get("TsCode").getAsString())));
//            }
//        } catch (NullPointerException e) {
//            System.out.println("null exeption convertJsonDataIntoAIBusModelHashMap(), AIAPIFile 94");
//        }
//    }

    public HashMap<String, List<AIBusDataAtMinuteModel>> getFromFileData() {
        HashMap<String, List<AIBusDataAtMinuteModel>> data = new HashMap<>();
        for (JsonObject object : Objects.requireNonNull(AIBusDataModels)) {//O(n)
            data.put(
                    object.get("TsCode").getAsString(),
                    getAIBusDataAtMinuteModelList(object.getAsJsonArray("busDataAtMinutes"), object.get("TsCode").getAsString()));
//            System.out.println("AIAPIFile 117, arr " + object.getAsJsonArray("busDataAtMinutes"));
        }
        return data;
    }

    public List<AIBusDataAtMinuteModel> getAIBusDataAtMinuteModelList(JsonArray Jsonarr, String TSCode) {
        ArrayList<AIBusDataAtMinuteModel> busDataAtMinutes = new ArrayList<>();

        try {
            //loop for jsonArray("allBusData)
            for (JsonElement arr : Jsonarr.getAsJsonArray()) {
                busDataAtMinutes.add(
                        new AIBusDataAtMinuteModel(
                                arr.getAsJsonObject().get("date").getAsString(),
                                arr.getAsJsonObject().get("Longitude").getAsDouble(),
                                arr.getAsJsonObject().get("Latitude").getAsDouble()
                        ));


            } 
//            for (JsonElement arr : jsonObject.getAsJsonArray()) {// loop for jsonArray("busDataAtMinutes)
//                System.out.println("127 ");
//                busDataAtMinutes.add(
//                        new AIBusDataAtMinuteModel(
//                                arr.getAsJsonObject().get("date").getAsString(),
//                                arr.getAsJsonObject().get("Longitude").getAsDouble(),
//                                arr.getAsJsonObject().get("Latitude").getAsDouble()
//                        ));
//                System.out.println("AI 135 " + arr.getAsJsonObject().get("date").getAsString());
//            }
        } catch (NumberFormatException | NullPointerException exception) {
            exception.getCause();
        }
//        System.out.println("AIAPIFile 140, busdata size " + busDataAtMinutes.size());
        return busDataAtMinutes;
    }

}
