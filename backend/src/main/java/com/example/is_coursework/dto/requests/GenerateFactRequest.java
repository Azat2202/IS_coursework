package com.example.is_coursework.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenerateFactRequest {
    @NotNull
    private Long roomId;
}
