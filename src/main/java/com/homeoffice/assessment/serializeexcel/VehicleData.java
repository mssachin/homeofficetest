package com.homeoffice.assessment.serializeexcel;

import com.google.gson.annotations.SerializedName;

public class VehicleData {

    @SerializedName("vehicleRegistration")
    private String vehicleRegistration;

    @SerializedName("make")
    private String make;

    @SerializedName("colour")
    private String colour;


    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
