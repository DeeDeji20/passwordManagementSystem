package com.semicolon.africa.passwordManager.dto.response;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateResponse {
    @NonNull
    private String message;
}
