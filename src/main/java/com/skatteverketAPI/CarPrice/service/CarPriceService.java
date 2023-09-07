package com.skatteverketAPI.CarPrice.service;
import com.skatteverketAPI.CarPrice.Model.CarPriceAPIResponse;
import com.skatteverketAPI.CarPrice.Model.MeanCarPriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarPriceService {

    private static final String baseURL = "https://skatteverket.entryscape.net/rowstore/dataset/fad86bf9-67e3-4d68-829c-7b9a23bc5e42";

    @Autowired
    private RestTemplate restTemplate;

    public ArrayList<CarPriceAPIResponse.CarPriceData> getCarPriceData(String fuelType) {
        try {
            String nextUrl = baseURL + String.format("/json?_offset=0&_limit=500&bransletyp=%s", fuelType);

            ArrayList<CarPriceAPIResponse.CarPriceData> totalResult = new ArrayList<>();

            // Due to pagination, several requests need to be done in case of large amount of data.
            while (nextUrl != null) {
                CarPriceAPIResponse result = restTemplate.getForObject(nextUrl, CarPriceAPIResponse.class);

                // Add the results from the current API call to totalResult
                if (result != null && result.getPrices() != null) {
                    totalResult.addAll(result.getPrices());
                }

                nextUrl = result.getNext();
            }


            return totalResult;

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception while calling endpoint of API for New Car Prices",
                    e
            );
        }
    }
    public MeanCarPriceResponse getMeanCarPriceData(String fuelType){
        var prices = getCarPriceData(fuelType);
        if (prices.isEmpty()) {
            return null;
        }

        Map<Integer, Double> meanPricesByYear = new HashMap<>();
        Map<Integer, Double> yearToSum = new HashMap<>();
        Map<Integer, Integer> yearToCount = new HashMap<>();

        // for total mean
        double totalSum = 0;

        for (CarPriceAPIResponse.CarPriceData car : prices) {
            int year = car.getYear();
            double price = car.getPrice();

            //for total mean
            totalSum += price;

            //for yearly mean
            yearToSum.put(year, yearToSum.getOrDefault(year, 0.0) + price);
            yearToCount.put(year, yearToCount.getOrDefault(year, 0) + 1);


        }
        // Calculate the yearly mean
        for (Map.Entry<Integer, Double> entry : yearToSum.entrySet()) {
            int year = entry.getKey();
            double sum = entry.getValue();
            int count = yearToCount.get(year);

            double meanPrice = sum / count;
            meanPricesByYear.put(year, meanPrice);
        }
        var response = new MeanCarPriceResponse(fuelType, totalSum/prices.size(), meanPricesByYear);
        return response;
    }
}
