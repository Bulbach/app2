package com.alexIntervale1.app2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor

public class RequestDto {

    private String name;
    private String surname;
    @NotBlank
    @Size(min = 12,max = 12)
    private Long personalNumber;
    private String progressControl;
}
