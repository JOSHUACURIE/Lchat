package com.Lchat.Lchat.dto;

import jakarta.validation.constraints.NotNull;

public class ContactRequestDTO {
    
    @NotNull(message = "Contact ID is required")
    private Long contactId;
    
    public Long getContactId() {
        return contactId;
    }
    
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}