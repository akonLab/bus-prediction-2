package controllers;

import models.AIBusDataAtMinuteModel;
import models.AIBusDataModel;
import org.springframework.stereotype.Controller;
import services.BusService;

import java.security.KeyStore;
import java.util.*;

@Controller
public class TimeController implements Runnable {
    private final BusService busService = new BusService();
    private final List<AIBusDataModel> allBusData = new ArrayList<>();

    private List<AIBusDataAtMinuteModel> busDataAtMinuteLis = new ArrayList<>();
//    String tscode = "A266";

    HashMap<String, List<AIBusDataAtMinuteModel>> data;

    public TimeController() {
    }

    @Override
    public void run() {
        addNewBusData();
        convertHashMapIntoList();
        writeData();

        System.out.println();
    }

    void writeData() {
        busService.saveAIBusModelToAIFile(allBusData);
    }

    //adding new data from not AI API
    void addNewBusData() {
        HashMap<String, AIBusDataAtMinuteModel> newData = busService.getDataFromApi();
        data = new HashMap<>();

        if (busService.isFileEmpty()) {
            for (Map.Entry<String, AIBusDataAtMinuteModel> model : newData.entrySet()) {
                data.put(model.getKey(), Collections.singletonList(model.getValue()));
            }
        } else {
            data = busService.getDataFromFile();
            System.out.println("48 !"+data.get("A409").get(0).toString());
            try {
             for (String code : newData.keySet()) {
                 System.out.println("new data "+data.get(code).size());
                 data.get(code).add(newData.get(code));
             }
                System.out.println("53 ! "+data.get("A409").toString());
         }catch (NullPointerException e){
             e.getMessage();
         }


        }
    }

    void convertHashMapIntoList() {
        for (String code : data.keySet()) {
            allBusData.add(new AIBusDataModel(
                    code,
                    data.get(code)
            ));
        }
        System.out.println("TimeController 61 allBusData size " + allBusData.size());

    }

    //getter
    public List<AIBusDataModel> getAllBusData() {

        return allBusData;
    }
}
