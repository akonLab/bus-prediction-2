package base;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class APIConn {
    private static final String authHost = "https://lrtportal.transcard.kz/tsm/Token"; //Авторизация POST //Body x-www-form-urlencoded
    private static final String busHost = "https://lrtportal.transcard.kz/tsm/api/buses/states";  //Онлайн передвижение автобусов GET
    private String token;
    private JsonObject allBusDataJson;

    public APIConn() {
        token = generateToken();
        getAllBusData();
    }

    public String generateToken() {
        HttpURLConnection connection = null;
        String urlParameters = "username=astinn" +
                "&Password=NJq8XmTFRm" +
                "&grant_type=password";

        try {
            connection = (HttpURLConnection) new URL(authHost).openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line).append("\n");
                response.append('\r');
            }
            rd.close();

            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(response.toString());
            return json.get("access_token").toString().replace('"', ' ').trim();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public void getAllBusData() {
        JsonObject buses = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(busHost).openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
//
//            if (conn.getResponseCode() == 401) {
//                token = generateToken().replace('"', ' ').trim();
//                getAllBusData();
//            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            String output;

            StringBuilder response = new StringBuilder();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();

            JsonParser parser = new JsonParser();
            buses = (JsonObject) parser.parse(response.toString());

        } catch (IOException e) {
            System.out.println(e.getCause().toString());
        }
        allBusDataJson = buses;
    }

    public ArrayList<JsonObject> getBusesByLineCode(Integer lineCode) {//work
        ArrayList<JsonObject> list = new ArrayList<>();
        if (allBusDataJson != null) {
            JsonArray array = allBusDataJson.getAsJsonArray("Activities");

            for (JsonElement jsonObject : array) {
                try {
                    if (jsonObject.getAsJsonObject().get("LineCode").getAsInt() == lineCode) {
                        list.add(jsonObject.getAsJsonObject());
                    }
                } catch (NumberFormatException exception) {
                    exception.getCause();
                }
            }
        } else {
            System.out.println("nope, json is empty");
        }
        return list;
    }

}

