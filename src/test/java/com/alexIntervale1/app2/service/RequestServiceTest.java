package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.exception.CustomAppException;
import com.alexIntervale1.app2.mapper.RequestMapper;
import com.alexIntervale1.app2.repository.RequestRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.jms.JMSException;
import javax.jms.Message;

class RequestServiceTest {

    @Mock
    RequestRepo repo;
    @Mock
    RequestMapper requestMapper;
    @Mock
    RequestService requestService;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Message createMessage() throws JMSException {
        Message mock = Mockito.mock(Message.class);
        mock.setStringProperty("name", "Veronica");
        mock.setStringProperty("surname", "Verezubova");
        mock.setStringProperty("personalNumber", "123456789456");
        Mockito.when(mock.getJMSCorrelationID()).thenReturn("1");
        return mock;
    }

    @Test
    void testReceiveMessageForCorrelationId() throws JMSException, CustomAppException {
        Message message = createMessage();
        requestService.receiveMessage(message);
        Assertions.assertEquals("1",message.getJMSCorrelationID());
    }
    @Test
    void testReceiveMessageForName() throws JMSException, CustomAppException {
        Message message = createMessage();
        requestService.receiveMessage(message);
        Mockito.when(message.getStringProperty("name")).thenReturn("Veronica");
    }
    @Test
    void testReceiveMessageForPersonalNumber() throws JMSException, CustomAppException {
        Message message = createMessage();
        requestService.receiveMessage(message);
        Mockito.when(message.getStringProperty("personalNumber")).thenReturn("123456789456");
    }

}
