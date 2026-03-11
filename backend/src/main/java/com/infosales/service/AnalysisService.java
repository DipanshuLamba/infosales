package com.infosales.service;

import com.infosales.model.AnalysisResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalysisService {

    public AnalysisResult analyzeSalesData(List<Map<String, String>> rows) {
        double totalRevenue = 0.0;
        int totalUnitsSold = 0;

        Map<String, Double> regionRevenue = new HashMap<>();
        Map<String, Double> categoryRevenue = new HashMap<>();

        for (Map<String, String> row : rows) {
            double revenue = parseDouble(row.get("Revenue"));
            int unitsSold = parseInt(row.get("Units_Sold"));
            String region = row.getOrDefault("Region", "Unknown");
            String category = row.getOrDefault("Product_Category", "Unknown");

            totalRevenue += revenue;
            totalUnitsSold += unitsSold;

            regionRevenue.put(region, regionRevenue.getOrDefault(region, 0.0) + revenue);
            categoryRevenue.put(category, categoryRevenue.getOrDefault(category, 0.0) + revenue);
        }

        String topRegion = getTopKey(regionRevenue);
        String topCategory = getTopKey(categoryRevenue);

        return new AnalysisResult(totalRevenue, totalUnitsSold, topRegion, topCategory);
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private int parseInt(String value) {
        try {
            return (int) Double.parseDouble(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private String getTopKey(Map<String, Double> map) {
        String topKey = "N/A";
        double max = Double.MIN_VALUE;

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topKey = entry.getKey();
            }
        }

        return topKey;
    }
}