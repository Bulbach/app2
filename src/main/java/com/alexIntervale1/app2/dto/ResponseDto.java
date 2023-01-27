package com.alexIntervale1.app2.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class ResponseDto {

    private long personalNumber;
    private double accrualAmount;
    private double payableAmount;
    private long ordinanceNumber;
    private String dateOfTheDecree;
    private double codeArticle;
}
