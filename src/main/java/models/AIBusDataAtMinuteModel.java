package models;

public class AIBusDataAtMinuteModel {
    private final String date;
    private final Double Longitude;
    private final Double Latitude;

    public AIBusDataAtMinuteModel(String date, Double longitude, Double latitude) {

        this.date = date;
        Longitude = longitude;
        Latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }
}
