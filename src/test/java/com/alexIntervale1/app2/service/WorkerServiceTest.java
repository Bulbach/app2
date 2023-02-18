package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.repository.impl.named.RequestNamedParameterJdbcImpl;
import com.alexIntervale1.app2.repository.impl.named.ResponseNamedJdbcRepoImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class WorkerServiceTest {
    @Autowired
    RequestNamedParameterJdbcImpl repo;
    @Autowired
    ResponseNamedJdbcRepoImpl responseRepo;

    @Test
    void testFindRequestObjectInDbBasedByProgressControlField() throws InterruptedException {
        RequestMessage requestMessage = getRequestMessage();
        repo.save(requestMessage);
        String progressControl = "in work";
        RequestMessage objectInDbBasedByProgressControlField = repo.findFirstByProgressControlIsLike(progressControl);
        Assertions.assertEquals(requestMessage, objectInDbBasedByProgressControlField);
    }

    @Test
    void notFoundPersonalNumber(){
        String progressControl = "in work";
        RequestMessage requestByLikeProgressControl = repo.findFirstByProgressControlIsLike(progressControl);
        Assertions.assertNull(requestByLikeProgressControl);
    }

    @Test
    void testFindResponseObjectInDbByPersonalNumber() {
        ResponseMessage responseMessage = getResponseMessage();
        Long personalNumber = 123456789123L;
        ResponseMessage byPersonalNumber = responseRepo.findByPersonalNumber(personalNumber);
        Assertions.assertEquals(responseMessage,byPersonalNumber);
    }
    RequestMessage getRequestMessage() {
        return RequestMessage.builder()
                .id(1L)
                .name("Veronica")
                .surname("Verezubova")
                .personalNumber(123456789123L)
                .correlationID("1")
                .progressControl("in work")
                .build();
    }

    private ResponseMessage getResponseMessage() {
        return new ResponseMessage(1L
                , 123456789123L
                , 123.45d
                , 125.03d
                , 211L
                , LocalDate.of(2020, Month.DECEMBER, 9)
                , 221d
                , null);
    }

}
