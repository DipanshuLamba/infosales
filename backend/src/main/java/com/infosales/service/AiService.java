package com.infosales.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosales.model.AnalysisResult;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AiService {

    private static final String API_KEY = "AIzaSyDK9wc0qow4onE1EM4GkYdMqiWuXkcQKNw";
    private static final String MODEL = "gemini-2.5-flash";

    public String generateSummary(AnalysisResult result) {
        try {
            String prompt = """
                    Generate a professional executive sales summary from the following analysis.

                    Total Revenue: %.2f
                    Total Units Sold: %d
                    Top Region: %s
                    Top Category: %s

                    Keep it concise and business-focused.
                    Include:
                    1. Executive summary
                    2. Key insight
                    3. Recommendation
                    """.formatted(
                    result.getTotalRevenue(),
                    result.getTotalUnitsSold(),
                    result.getTopRegion(),
                    result.getTopCategory()
            );

            String url = "https://generativelanguage.googleapis.com/v1beta/models/"
                    + MODEL + ":generateContent";

            Map<String, Object> part = Map.of("text", prompt);
            Map<String, Object> content = Map.of("parts", List.of(part));
            Map<String, Object> body = Map.of("contents", List.of(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", API_KEY);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());

            JsonNode textNode = json.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text");

            if (textNode.isMissingNode() || textNode.isNull()) {
                return "Gemini returned no summary. Full response: " + response.getBody();
            }

            return textNode.asText();

        } catch (Exception e) {
            e.printStackTrace();
            return "AI summary generation failed: " + e.getMessage();
        }
    }
}