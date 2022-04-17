package com.semicolon.africa.passwordManager.dto.request;

import lombok.Data;

@Data
public class RetrievePasswordRequest {
    private String url;
    private String email;
}
