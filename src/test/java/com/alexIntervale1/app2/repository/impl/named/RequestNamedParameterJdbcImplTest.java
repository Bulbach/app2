package com.alexIntervale1.app2.repository.impl.named;

import com.alexIntervale1.app2.model.RequestMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RequestNamedParameterJdbcImplTest {
    @Autowired
    RequestNamedParameterJdbcImpl parameterJdbc;

    @Test
    void save() {
        RequestMessage requestMessage = RequestMessage.builder()
                .name("Veronica")
                .surname("Verezubova")
                .personalNumber(123456789123L)
                .correlationID("1")
                .progressControl("in work")
                .build();
        RequestMessage saveResult = parameterJdbc.save(requestMessage);
        Assertions.assertEquals(requestMessage.getName(),saveResult.getName());
    }

    @Test
    void update() {
        RequestMessage requestMessage = getRequestMessage();
        RequestMessage save = parameterJdbc.save(requestMessage);
        save.setName("VeronicaNew");
        RequestMessage update = parameterJdbc.update(save);
        Assertions.assertEquals("VeronicaNew",update.getName());
    }

    @Test
    void deleteById() {
        RequestMessage requestMessage = RequestMessage.builder()
                .id(3L)
                .name("Margarita")
                .surname("Verezubova")
                .personalNumber(123456789456L)
                .correlationID("2")
                .progressControl("in work")
                .build();
        parameterJdbc.save(requestMessage);
        parameterJdbc.deleteById(requestMessage.getId());
        List<RequestMessage> parameterJdbcAll = parameterJdbc.findAll();
        Assertions.assertEquals(1,parameterJdbcAll.size());
    }

    @Test
    void findAll() {
        RequestMessage requestMessage = getRequestMessage();
        parameterJdbc.save(requestMessage);
        List<RequestMessage> parameterJdbcAll = parameterJdbc.findAll();
        Assertions.assertEquals(1,parameterJdbcAll.size());
    }

    @Test
    void findById() {
        RequestMessage requestMessage = getRequestMessage();
        RequestMessage save = parameterJdbc.save(requestMessage);
        RequestMessage byId = parameterJdbc.findById(save.getId());
        Assertions.assertEquals(save,byId);
    }

    @Test
    void countByProgressControlIsLike() {
        RequestMessage requestMessage = getRequestMessage();
        parameterJdbc.save(requestMessage);
        String progressControl = "in work";
        int countIsLike = parameterJdbc.countByProgressControlIsLike(progressControl);
        Assertions.assertEquals(1,countIsLike);
    }

    @Test
    void findFirstByProgressControlIsLike() {
        RequestMessage requestMessage = getRequestMessage();
        parameterJdbc.save(requestMessage);
        String progressControl = "in work";
        RequestMessage requestByLikeProgressControl = parameterJdbc.findFirstByProgressControlIsLike(progressControl);
        Assertions.assertEquals(requestMessage,requestByLikeProgressControl);
    }


    RequestMessage getRequestMessage(){
        return   RequestMessage.builder()
                .name("Veronica")
                .surname("Verezubova")
                .personalNumber(123456789123L)
                .correlationID("1")
                .progressControl("in work")
                .build();
    }
}