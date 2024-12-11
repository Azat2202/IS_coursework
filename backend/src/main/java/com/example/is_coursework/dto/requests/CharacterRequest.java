package com.example.is_coursework.dto.requests;

import com.example.is_coursework.models.enums.SexType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CharacterRequest {
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private SexType sex;

    @NotNull
    private String notes;

    @NotNull
    private Boolean isActive;

    @NotNull
    private Long userId;

    @NotNull
    private Long bodyTypeId;

    @NotNull
    private Long healthId;

    @NotNull
    private Long traitId;

    @NotNull
    private Long hobbyId;

    @NotNull
    private Long professionId;

    @NotNull
    private Long phobiaId;

    @NotNull
    private Long equipmentId;

    @NotNull
    private Long bagId;
}
