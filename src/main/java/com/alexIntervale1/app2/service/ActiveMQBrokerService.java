package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.dto.ResponseDto;
import com.alexIntervale1.app2.mapper.ResponseMapper;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.MessageNotWriteableException;

@Slf4j
@Data
@RequiredArgsConstructor

@Component
public class ActiveMQBrokerService {

    private final Gson gson;
    private final ResponseMapper responseMapper;
    private final JmsTemplate broker;
    @Autowired
    private final Environment environment;
    private String OUTBOUND_QUEUE_NAME ="outindividual.queue";

    public ActiveMQTextMessage createMessageForBrokerActiveMqQueue(ResponseMessage responseMessage) {
        ResponseDto responseDto = responseMapper.toDto(responseMessage);
        String json = gson.toJson(responseDto);

        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        try {
            textMessage.setCorrelationId(responseMessage.getCorrelationID());
            textMessage.setText(json);
        } catch (MessageNotWriteableException e) {
            log.warn("Проблема при добавлении сообщения в textMessage = {}", json, e);
        }
        return textMessage;
    }

    public void sendPreparedMessage(ActiveMQTextMessage activeMQTextMessage) {
        broker.convertAndSend(OUTBOUND_QUEUE_NAME, activeMQTextMessage);
    }
}
