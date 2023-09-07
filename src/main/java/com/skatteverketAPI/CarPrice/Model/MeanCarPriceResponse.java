package com.skatteverketAPI.CarPrice.Model;

import java.util.Map;

// Save the mean data in a class object
public class MeanCarPriceResponse {
    String fuelType;
    double totalMean;
    Map<Integer, Double> yearlyMean;

    public MeanCarPriceResponse(String fuelType, double totalMean, Map<Integer, Double> yearlyMean){
        this.fuelType = fuelType;
        this.totalMean = totalMean;
        this.yearlyMean = yearlyMean;
    }
    public String getFuelType() {
        return fuelType;
    }

    public double getTotalMean() {
        return totalMean;
    }

    public Map<Integer, Double> getYearlyMean() {
        return yearlyMean;
    }



}
