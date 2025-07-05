package org.fealtyx.sms_ai.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fealtyx.sms_ai.model.OllamaRequest;
import org.fealtyx.sms_ai.model.OllamaResponse;
import org.fealtyx.sms_ai.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class OllamaService {
    private static final Logger logger = LoggerFactory.getLogger(OllamaService.class);

    @Value("${ollama.api.url:http://localhost:11434/api/generate}")
    private String ollamaApiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OllamaService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    private static OllamaRequest getOllamaRequest(Student student) {
        String prompt = String.format(
                "Summarize the student %s, age %d, email %s in 50 words. with context of Student Life",
                student.getName(), student.getAge(), student.getEmail()
        );
        return new OllamaRequest("llama3", prompt, false);
    }

    private String processOllamaResponse(String responseBody) {
        try {
            StringBuilder formattedResponse = new StringBuilder();
            String[] chunks = responseBody.split("\n");

            for (String chunk : chunks) {
                if (!chunk.trim().isEmpty()) {
                    OllamaResponse partialResponse = objectMapper.readValue(chunk, OllamaResponse.class);
                    if (partialResponse != null && partialResponse.getResponse() != null) {
                        // Format the response with proper line breaks
                        String formattedChunk = partialResponse.getResponse()
                                .replace("\\n", "\n")  // Convert escaped newlines to actual newlines
                                .replace("\"", "");    // Remove any remaining quotes

                        formattedResponse.append(formattedChunk);
                    }
                }
            }

            return formattedResponse.toString().trim();
        } catch (Exception e) {
            logger.error("Error processing Ollama response: {}", e.getMessage(), e);
            return "Error processing response: " + e.getMessage();
        }
    }

    public String generateStudentSummary(Student student) {
        OllamaRequest request = getOllamaRequest(student);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<OllamaRequest> entity = new HttpEntity<>(request, headers);

            logger.info("Sending request to Ollama API: {}", request);

            ResponseEntity<String> response = restTemplate.exchange(
                    ollamaApiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String formattedResponse = processOllamaResponse(response.getBody());
                logger.info("Received response from Ollama: {}", formattedResponse);
                return formattedResponse;
            }

            return "No valid response received from Ollama";
        } catch (Exception e) {
            logger.error("Error generating summary: {}", e.getMessage(), e);
            return "Error generating summary: " + e.getMessage();
        }
    }
}