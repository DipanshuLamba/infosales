package com.infosales.model;

public class AnalysisResult {

    private double totalRevenue;
    private int totalUnitsSold;
    private String topRegion;
    private String topCategory;

    public AnalysisResult() {
    }

    public AnalysisResult(double totalRevenue, int totalUnitsSold, String topRegion, String topCategory) {
        this.totalRevenue = totalRevenue;
        this.totalUnitsSold = totalUnitsSold;
        this.topRegion = topRegion;
        this.topCategory = topCategory;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalUnitsSold() {
        return totalUnitsSold;
    }

    public void setTotalUnitsSold(int totalUnitsSold) {
        this.totalUnitsSold = totalUnitsSold;
    }

    public String getTopRegion() {
        return topRegion;
    }

    public void setTopRegion(String topRegion) {
        this.topRegion = topRegion;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }
}