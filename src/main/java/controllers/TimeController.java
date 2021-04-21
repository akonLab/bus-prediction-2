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
    private final List<AIBusDataAtMinuteModel> busDataAtMinuteLis = new ArrayList<>();
    private HashMap<String, List<AIBusDataAtMinuteModel>> data;

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
            try {
                for (String code : newData.keySet()) {
                    //10 min case checker
                    if (data.get(code).size() >= 10) {
                        data.get(code).remove(0);
                    }
                    //
                    data.get(code).add(newData.get(code));
                }
            } catch (NullPointerException e) {
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
    }

    //getter
    public List<AIBusDataModel> getAllBusData() {
        return allBusData;
    }
}
