package com.ayurveda.diet.dto;

import java.util.List;


import java.util.List;

public class ChatResponseDTO {
    private String response;
    private List<String> suggestions;
    private String responseType; // "answer", "reminder", "question"
    private boolean requiresFollowUp;

    // Constructors
    public ChatResponseDTO() {}

    public ChatResponseDTO(String response) {
        this.response = response;
        this.responseType = "answer";
        this.requiresFollowUp = false;
    }

    public ChatResponseDTO(String response, List<String> suggestions) {
        this.response = response;
        this.suggestions = suggestions;
        this.responseType = "answer";
        this.requiresFollowUp = false;
    }

    // Getters and Setters
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public List<String> getSuggestions() { return suggestions; }
    public void setSuggestions(List<String> suggestions) { this.suggestions = suggestions; }

    public String getResponseType() { return responseType; }
    public void setResponseType(String responseType) { this.responseType = responseType; }

    public boolean isRequiresFollowUp() { return requiresFollowUp; }
    public void setRequiresFollowUp(boolean requiresFollowUp) { this.requiresFollowUp = requiresFollowUp; }
}
