package com.infosales.model;

public class SalesSummaryResponse {

    private String message;
    private AnalysisResult analysis;

    public SalesSummaryResponse() {
    }

    public SalesSummaryResponse(String message, AnalysisResult analysis) {
        this.message = message;
        this.analysis = analysis;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AnalysisResult getAnalysis() {
        return analysis;
    }

    public void setAnalysis(AnalysisResult analysis) {
        this.analysis = analysis;
    }
}