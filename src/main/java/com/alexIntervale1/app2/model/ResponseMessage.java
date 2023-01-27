package com.alexIntervale1.app2.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "response")
public class ResponseMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "personalNumber")
    private Long personalNumber;
    @Column(name = "accrualAmount")
    private double accrualAmount;
    @Column(name = "payableAmount")
    private double payableAmount;
    @Column(name = "ordinanceNumber")
    private long ordinanceNumber;
    @Column(name = "dateOfTheDecree")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfTheDecree;
    @Column(name = "codeArticle")
    private double codeArticle;
    @Column(name = "correlationID")
    private String correlationID;



    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
