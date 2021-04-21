package restApi;

import models.AIBusDataModel;

import java.util.ArrayList;
import java.util.List;

public class BusDataResponse {
    private final String status;
    private final Integer code;

    //get
    private List<AIBusDataModel> allBusData = new ArrayList<>();

    //get constructor
    public BusDataResponse(String status, Integer code, List<AIBusDataModel> allBusData) {
        this.status = status;
        this.code = code;
        this.allBusData = allBusData;
    }


    //getter
    public List<AIBusDataModel> getAllBusData() {
        return allBusData;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }
}
