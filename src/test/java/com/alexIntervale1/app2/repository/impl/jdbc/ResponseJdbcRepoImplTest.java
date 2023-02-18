package com.alexIntervale1.app2.repository.impl.jdbc;

import com.alexIntervale1.app2.model.ResponseMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ResponseJdbcRepoImplTest {

    @Autowired
    ResponseJdbcRepoImpl responseJdbcRepoImpl;

    @Test
    void testSave() {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .personalNumber(123456789321L)
                .accrualAmount(123.45d)
                .payableAmount(125.03d)
                .ordinanceNumber(211L)
                .dateOfTheDecree(LocalDate.of(2020, Month.DECEMBER, 9))
                .codeArticle(221d)
                .build();
        ResponseMessage result = responseJdbcRepoImpl.save(responseMessage);
        Assertions.assertEquals(responseMessage.getPersonalNumber(), result.getPersonalNumber());
    }

    @Test
    void testUpdate() {
        ResponseMessage responseMessage = ResponseMessage.builder()
                .personalNumber(987654321123L)
                .accrualAmount(323.45d)
                .payableAmount(325.03d)
                .ordinanceNumber(211L)
                .dateOfTheDecree(LocalDate.of(2020, Month.NOVEMBER, 15))
                .codeArticle(221d)
                .build();
        ResponseMessage save = responseJdbcRepoImpl.save(responseMessage);
        save.setCodeArticle(111);
        ResponseMessage result = responseJdbcRepoImpl.update(save);
        Assertions.assertEquals(111,result.getCodeArticle());
    }

    @Test
    void testDeleteById() {
        responseJdbcRepoImpl.deleteById(1L);
    }

    @Test
    void testFindAll() {
        List<ResponseMessage> result = responseJdbcRepoImpl.findAll();
        Assertions.assertEquals(5, result.size());
    }

    @Test
    void testFindById() {
        ResponseMessage result = responseJdbcRepoImpl.findById(1L);
        ResponseMessage message = getResponseMessage();
        Assertions.assertEquals(message, result);
    }

    @Test
    void testFindByPersonalNumber() {
        ResponseMessage result = responseJdbcRepoImpl.findByPersonalNumber(123456789123L);
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
