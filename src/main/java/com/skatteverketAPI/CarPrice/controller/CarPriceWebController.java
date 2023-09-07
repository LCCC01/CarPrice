package com.skatteverketAPI.CarPrice.controller;

import com.skatteverketAPI.CarPrice.service.CarPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

// View the mean data visually
@Controller
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarPriceWebController {

    private final CarPriceService carPriceService;

    @GetMapping("/view/{fuelType}")
    public String viewCarPrice(@PathVariable String fuelType, Model model) {
        fuelType = fuelType.substring(0, 1).toUpperCase() + fuelType.substring(1).toLowerCase();
        try {
            var response = carPriceService.getMeanCarPriceData(fuelType);

            model.addAttribute("fuelType", response.getFuelType());

            // Format the mean value so it is more readable
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
            ((DecimalFormat) formatter).applyPattern("#,###"); // Use pattern for thousands separator

            String formattedMeanValue = formatter.format(response.getTotalMean());
            model.addAttribute("totalMeanPrice", formattedMeanValue);

            // Sort the yearlyMeanPrices map by year
            Map<Integer, Double> sortedYearlyMeanPrice = response.getYearlyMean()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            model.addAttribute("yearlyMeanPrice", sortedYearlyMeanPrice);

            return "meanCarPrice"; // This matches the name of your Thymeleaf template (MeanCarPrice.html)
        } catch (Exception e) {
            return "error";
        }
    }
}
