package models;

import java.util.List;

public class AIBusDataModel {
    private final String TsCode;
    private final List<AIBusDataAtMinuteModel> busDataAtMinutes ;

    //constructor
    public AIBusDataModel(String tsCode, List<AIBusDataAtMinuteModel> newBusDataAtMinutes) {
        TsCode = tsCode;
        busDataAtMinutes = newBusDataAtMinutes;
    }

    //getter
    public String getTsCode() {
        return TsCode;
    }

    public List<AIBusDataAtMinuteModel> getBusDataAtMinutes() {
        return busDataAtMinutes;
    }
}
