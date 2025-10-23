package com.ayurveda.diet.dto;

import java.time.LocalDateTime;



import java.time.LocalDateTime;

public class ChatMessageDTO {
    private String message;
    private String sender; // "user" or "bot"
    private LocalDateTime timestamp;
    private String messageType; // "text", "reminder", "query", "suggestion"

    // Constructors
    public ChatMessageDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessageDTO(String message, String sender, String messageType) {
        this();
        this.message = message;
        this.sender = sender;
        this.messageType = messageType;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }
}
