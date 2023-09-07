package com.skatteverketAPI.CarPrice.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

// Handle the response fetched from the API

public class CarPriceAPIResponse {
    @JsonProperty("results")
    public ArrayList<CarPriceData> prices;

    @JsonProperty("next")
    public String next;

    public ArrayList<CarPriceData> getPrices() {
        return prices;
    }

    public String getNext(){
        return next;
    }


    public static class CarPriceData{

        @JsonProperty("nybilspris")
        public double price;


        @JsonProperty("tillverkningsar")
        public int year;

        public double getPrice() {
            return price;
        }
        public int getYear() {
            return year;
        }


    }

}
