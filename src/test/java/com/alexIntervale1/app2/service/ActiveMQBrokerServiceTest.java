package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.dto.ResponseDto;
import com.alexIntervale1.app2.mapper.ResponseMapper;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.repository.impl.named.ResponseNamedJdbcRepoImpl;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.MessageNotWriteableException;
import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ActiveMQBrokerServiceTest {
    @Autowired
     ResponseMapper responseMapper;
    @Autowired
    ActiveMQBrokerService amQService;
    @Autowired
    ResponseNamedJdbcRepoImpl jdbcRepo;
    @InjectMocks
     Gson gson;
    @Mock
    JmsTemplate broker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    void createMessageForBrokerActiveMqQueue() throws MessageNotWriteableException {
        ResponseMessage responseMessage = jdbcRepo.findByPersonalNumber(123456789123L);
        responseMessage.setCorrelationID("1");
        ActiveMQTextMessage message1 = amQService.createMessageForBrokerActiveMqQueue(responseMessage);
/*
        если использовать AssertJ
        String json = "{personalNumber:123456789123,accrualAmount:123.45,payableAmount:125.03,ordinanceNumber:211,dateOfTheDecree:2020-12-09,codeArticle:221.0}";
 */
        ResponseMessage localResponseMessage = getResponseMessage();
        ResponseDto responseDto = responseMapper.toDto(localResponseMessage) ;
        String json = gson.toJson(responseDto);
        ActiveMQTextMessage message2 = new ActiveMQTextMessage();
        message2.setCorrelationId(localResponseMessage.getCorrelationID());
        message2.setText(json);
/*
        если использовать AssertJ
        Assertions.assertThat(message1.getText()).isEqualTo(message2.getText());
        Assertions.assertThat(message1.getCorrelationId()).isEqualTo(message2.getCorrelationId());
 */
        Assertions.assertAll(
                () -> Assertions.assertEquals(message1.getText(), message2.getText()),
                () -> Assertions.assertEquals(message1.getCorrelationId(), message2.getCorrelationId())
        );
    }

    private ResponseMessage getResponseMessage() {
        return new ResponseMessage(1L
                , 123456789123L
                , 123.45d
                , 125.03d
                , 211L
                , LocalDate.of(2020, Month.DECEMBER, 9)
                , 221d
                , "1");
    }
}