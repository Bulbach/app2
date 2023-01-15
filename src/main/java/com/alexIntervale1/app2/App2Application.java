package com.alexIntervale1.app2;

import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.repository.ResponseRepo;
import com.alexIntervale1.app2.worker.Worker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import java.time.LocalDate;

@SpringBootApplication
@EnableJms
public class App2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App2Application.class, args);

        Runnable worker = context.getBean("worker", Worker.class);
        Thread thread = new Thread(worker, "Worker");
        thread.start();

    }

    @Bean
    public CommandLineRunner testAppBook(ResponseRepo responseRepo) {
        return args -> {
            responseRepo.save(
                    ResponseMessage.builder()
                            .id(1L)
                            .individualNumber(123456789123L)
                            .accrualAmount(123.45d)
                            .payableAmount(125.03d)
                            .ordinanceNumber(211L)
                            .dateOfTheDecree(LocalDate.parse("2020-12-09"))
                            .codeArticle(221)
                            .build());
            responseRepo.save(ResponseMessage.builder()
                    .id(2L).individualNumber(123456789456L).accrualAmount(223.45d).payableAmount(225.03d)
                    .ordinanceNumber(215L).dateOfTheDecree(LocalDate.parse("2020-11-09")).codeArticle(223)
                    .build());
            responseRepo.save(ResponseMessage.builder()
                    .id(3L).individualNumber(123456789789L).accrualAmount(323.45d).payableAmount(325.03d)
                    .ordinanceNumber(217L).dateOfTheDecree(LocalDate.parse("2020-05-09")).codeArticle(100.1)
                    .build());
            responseRepo.save(ResponseMessage.builder()
                    .id(4L).individualNumber(234567891234L).accrualAmount(423.45d).payableAmount(425.03d)
                    .ordinanceNumber(219L).dateOfTheDecree(LocalDate.parse("2021-12-09")).codeArticle(300.2)
                    .build());
            responseRepo.save(ResponseMessage.builder()
                    .id(5L).individualNumber(234567895678L).accrualAmount(523.45d).payableAmount(525.03d)
                    .ordinanceNumber(221L).dateOfTheDecree(LocalDate.parse("2022-12-09")).codeArticle(435.1)
                    .build());
        };
    }
}
