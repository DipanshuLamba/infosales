package com.infosales.controller;

import com.infosales.model.AnalysisResult;
import com.infosales.model.SalesSummaryResponse;
import com.infosales.service.AiService;
import com.infosales.service.AnalysisService;
import com.infosales.service.EmailService;
import com.infosales.service.FileProcessingService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
public class AnalysisController {

    private final FileProcessingService fileProcessingService;
    private final AnalysisService analysisService;
    private final AiService aiService;
    private final EmailService emailService;

    public AnalysisController(
            FileProcessingService fileProcessingService,
            AnalysisService analysisService,
            AiService aiService,
            EmailService emailService
    ) {
        this.fileProcessingService = fileProcessingService;
        this.analysisService = analysisService;
        this.aiService = aiService;
        this.emailService = emailService;
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SalesSummaryResponse analyzeFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") @NotBlank @Email String email
    ) throws Exception {

        List<Map<String, String>> rows = fileProcessingService.readFile(file);
        AnalysisResult result = analysisService.analyzeSalesData(rows);
        String summary = aiService.generateSummary(result);

        String finalMessage = summary;

        try {
            emailService.sendSummaryEmail(email, summary);
        } catch (Exception e) {
            finalMessage = summary + "\n\nNote: Email delivery is unavailable on the current cloud free tier. The summary was generated successfully.";
        }

        return new SalesSummaryResponse(finalMessage, result);
    }
}