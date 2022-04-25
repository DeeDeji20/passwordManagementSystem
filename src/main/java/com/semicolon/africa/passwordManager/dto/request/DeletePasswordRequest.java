package com.semicolon.africa.passwordManager.dto.request;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class DeletePasswordRequest {
    @Getter @Setter @NonNull
    private String email;
}
