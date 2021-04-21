package base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AIBusAPIConn {
    private static final String host = "http://localhost:8080/buses";
    private Boolean isRunning = false;
    private JsonObject allBusDataJson;

    public AIBusAPIConn() {

        getAllBusData();
    }

    public Boolean getConnection() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public void getAllBusData() {
        JsonObject buses = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line).append("\n");
                response.append('\r');
            }
            rd.close();

            JsonParser parser = new JsonParser();
            buses = (JsonObject) parser.parse(response.toString());
        } catch (IOException e) {
            System.out.println(e.getCause().toString());
        } catch (NullPointerException exception) {
            exception.getCause();
        }
        allBusDataJson = buses;
    }


    public ArrayList<JsonObject> getBusDataArrayList() {
        ArrayList<JsonObject> list = new ArrayList<>();

        if (allBusDataJson != null) {
            JsonArray array = allBusDataJson.getAsJsonArray("allBusData");

            for (JsonElement jsonObject : array) {
                try {
                    list.add(jsonObject.getAsJsonObject());
                } catch (NumberFormatException exception) {
                    exception.getCause();
                }
            }
        } else {
            System.out.println("nope, json is empty");
        }
        System.out.println("88 AI Api " + list.size());
        return list;
    }

    //getter
    public JsonObject getAllBusDataJson() {
        return allBusDataJson;
    }

    public Boolean getRunning() {
        return isRunning;
    }
}
