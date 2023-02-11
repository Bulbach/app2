package com.alexIntervale1.app2.jdbc.named;

import com.alexIntervale1.app2.model.ResponseMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class ResponseNamedJdbcRepoImplTest {

    @Autowired
    ResponseNamedJdbcRepoImpl responseNamedJdbcRepoImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .id(6L)
                .personalNumber(123456789321L)
                .accrualAmount(123.45d)
                .payableAmount(125.03d)
                .ordinanceNumber(211L)
                .dateOfTheDecree(LocalDate.of(2020, Month.DECEMBER, 9))
                .codeArticle(221d)
                .build();
        ResponseMessage result = responseNamedJdbcRepoImpl.save(responseMessage);
        Assertions.assertEquals(responseMessage, result);
    }

    @Test
    void testUpdate() {
        ResponseMessage responseMessage = getResponseMessage();
        responseMessage.setCodeArticle(111);
        ResponseMessage result = responseNamedJdbcRepoImpl.update(responseMessage);
        Assertions.assertEquals(responseMessage, result);
        Assertions.assertEquals(111,result.getCodeArticle());
    }

    @Test
    void testDeleteById() {
        responseNamedJdbcRepoImpl.deleteById(1L);
    }

    @Test
    void testFindAll() {
        List<ResponseMessage> result = responseNamedJdbcRepoImpl.findAll();
        Assertions.assertEquals(5, result.size());
    }

    @Test
    void testFindById() {
        ResponseMessage result = responseNamedJdbcRepoImpl.findById(1L);
        ResponseMessage message = getResponseMessage();
        Assertions.assertEquals(message, result);
    }


    @Test
    void testFindByPersonalNumber() {
        ResponseMessage result = responseNamedJdbcRepoImpl.findByPersonalNumber(123456789123L);
        Assertions.assertEquals(getResponseMessage(), result);
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

