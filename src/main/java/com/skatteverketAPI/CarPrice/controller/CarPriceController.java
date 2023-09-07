package com.skatteverketAPI.CarPrice.controller;

import com.skatteverketAPI.CarPrice.service.CarPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Rest API to just get the mean price data
@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarPriceController {

    private final CarPriceService carPriceService;
    @GetMapping("get-mean/{fuelType}")
    public ResponseEntity<?> meanCarPrice(@PathVariable String fuelType){
            fuelType = fuelType.substring(0, 1).toUpperCase() + fuelType.substring(1).toLowerCase();
            var response = carPriceService.getMeanCarPriceData(fuelType);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cars with that fueltype found");
            }
            return ResponseEntity.ok(response);
    }
}
