package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.dto.RequestDto;
import com.alexIntervale1.app2.exception.CustomAppException;
import com.alexIntervale1.app2.mapper.RequestMapper;
import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.jdbc.named.RequestNamedParameterJdbcImpl;
import com.alexIntervale1.app2.worker.Worker;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class RequestService {

//    private final RequestRepo repo;
//    private final RequestJdbcRepoImpl repo;
    private final RequestNamedParameterJdbcImpl repo;
    private final Gson gson;

    private final RequestMapper requestMapper;

    private final Worker worker;

    @JmsListener(destination = "individual.queue")
    public void receiveMessage(final Message message) throws CustomAppException {

        log.info("Received message " + message);
        String numberRequest = null;
        try {
            numberRequest = message.getJMSCorrelationID();
        } catch (JMSException e) {
            log.warn("Проблемы при получении порядкового номера сообщения , метод receiveMessage ", e);
            throw new CustomAppException(e);
        }
        log.info(numberRequest);

        String json = "";
        TextMessage textMessage = (TextMessage) message;
        try {
            json = textMessage.getText();
        } catch (JMSException e) {
            log.warn("Проблемы при получении текста ответа, метод receiveMessage ", e);
            throw new CustomAppException(e);
        }

        RequestDto requestDto = gson.fromJson(json, RequestDto.class);
        log.info(String.valueOf(requestDto));

        RequestMessage message1 = new RequestMessage();
        message1.setCorrelationID(numberRequest);
        log.info("Созданіе нового об'екта" + message1);

        requestMapper.updateRequestMessageFromDto(requestDto, message1);
        log.info("Об'ект после конвертацыі " + message1);

        repo.save(message1);
        log.info(String.valueOf(message1));

    }
}
