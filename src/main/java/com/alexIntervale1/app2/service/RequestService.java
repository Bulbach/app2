package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.dto.RequestDto;
import com.alexIntervale1.app2.exception.CustomAppException;
import com.alexIntervale1.app2.jdbc.named.RequestNamedParameterJdbcImpl;
import com.alexIntervale1.app2.mapper.RequestMapper;
import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.worker.Worker;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
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
        String numberRequest = getNumberRequest(message);

        String json = getTextFromMessage((TextMessage) message);

        RequestDto requestDto = getRequestDto(json);

        RequestMessage requestMessage = getRequestMessage(numberRequest, requestDto);

        repo.save(requestMessage);
        log.info("Полученны объект сохранненый в базу " + requestMessage);

    }

    private String getNumberRequest(Message message) throws CustomAppException {
        String numberRequest = null;
        try {
            numberRequest = message.getJMSCorrelationID();
        } catch (JMSException e) {
            log.warn("Проблемы при получении порядкового номера сообщения , метод receiveMessage ", e);
            throw new CustomAppException(e);
        }
        log.info(numberRequest);
        return numberRequest;
    }

    private RequestMessage getRequestMessage(String numberRequest, RequestDto requestDto) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCorrelationID(numberRequest);
        log.info("Созданіе нового об'екта" + requestMessage);

        requestMapper.updateRequestMessageFromDto(requestDto, requestMessage);
        log.info("Об'ект после конвертацыі " + requestMessage);
        return requestMessage;
    }

    private RequestDto getRequestDto(String json) {
        RequestDto requestDto = gson.fromJson(json, RequestDto.class);
        log.info(String.valueOf(requestDto));
        return requestDto;
    }

    private String getTextFromMessage(TextMessage message) throws CustomAppException {
        String json = "";
        TextMessage textMessage = message;
        try {
            json = textMessage.getText();
        } catch (JMSException e) {
            log.warn("Проблемы при получении текста ответа, метод receiveMessage ", e);
            throw new CustomAppException(e);
        }
        return json;
    }
}
