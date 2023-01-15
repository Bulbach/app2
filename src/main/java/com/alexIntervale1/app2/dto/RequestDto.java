package com.alexIntervale1.app2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class RequestDto {

    private String name;
    private String surname;
    private Long personalNumber;
    private String progressControl;
}
