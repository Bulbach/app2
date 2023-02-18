package com.alexIntervale1.app2.repository.impl.jdbc;

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
class RequestJdbcRepoImplTest {

    @Autowired
    RequestJdbcRepoImpl requestJdbcRepoImpl;


    @Test
    void testSave() {
        RequestMessage requestMessage = RequestMessage.builder()
                .name("Veronica")
                .surname("Verezubova")
                .personalNumber(123456789123L)
                .correlationID("1")
                .progressControl("in work")
                .build();
        RequestMessage saveResult = requestJdbcRepoImpl.save(requestMessage);
        Assertions.assertEquals(requestMessage.getName(), saveResult.getName());
    }

    @Test
    void testUpdate() {
        RequestMessage requestMessage = getRequestMessage();
        RequestMessage save = requestJdbcRepoImpl.save(requestMessage);
        save.setName("VeronicaNew");
        RequestMessage update = requestJdbcRepoImpl.update(save);
        Assertions.assertEquals("VeronicaNew", update.getName());
    }

    @Test
    void testDeleteById() {
        RequestMessage requestMessage = RequestMessage.builder()
                .name("Margarita")
                .surname("Verezubova")
                .personalNumber(123456789456L)
                .correlationID("2")
                .progressControl("in work")
                .build();
        RequestMessage save = requestJdbcRepoImpl.save(requestMessage);
        requestJdbcRepoImpl.deleteById(save.getId());
        List<RequestMessage> parameterJdbcAll = requestJdbcRepoImpl.findAll();
        Assertions.assertEquals(0, parameterJdbcAll.size());
    }

    @Test
    void testFindAll() {
        RequestMessage requestMessage = getRequestMessage();
        requestJdbcRepoImpl.save(requestMessage);
        List<RequestMessage> parameterJdbcAll = requestJdbcRepoImpl.findAll();
        Assertions.assertEquals(1, parameterJdbcAll.size());
    }

    @Test
    void testFindById() {
        RequestMessage requestMessage = getRequestMessage();
        RequestMessage save = requestJdbcRepoImpl.save(requestMessage);
        RequestMessage byId = requestJdbcRepoImpl.findById(save.getId());
        Assertions.assertEquals(save, byId);
    }

    @Test
    void testCountByProgressControlIsLike() {
        RequestMessage requestMessage = getRequestMessage();
        requestJdbcRepoImpl.save(requestMessage);
        String progressControl = "in work";
        int countIsLike = requestJdbcRepoImpl.countByProgressControlIsLike(progressControl);
        Assertions.assertEquals(1, countIsLike);
    }

    @Test
    void testFindFirstByProgressControlIsLike() {
        RequestMessage requestMessage = getRequestMessage();
        requestJdbcRepoImpl.save(requestMessage);
        String progressControl = "in work";
        RequestMessage requestByLikeProgressControl = requestJdbcRepoImpl.findFirstByProgressControlIsLike(progressControl);
        Assertions.assertEquals(requestMessage, requestByLikeProgressControl);
    }

    RequestMessage getRequestMessage() {
        return RequestMessage.builder()
                .name("Veronica")
                .surname("Verezubova")
                .personalNumber(123456789123L)
                .correlationID("1")
                .progressControl("in work")
                .build();
    }
}

