package com.alexIntervale1.app2.worker;

import com.alexIntervale1.app2.dto.ResponseDto;
import com.alexIntervale1.app2.exception.CustomAppException;
import com.alexIntervale1.app2.jdbc.named.RequestNamedParameterJdbcImpl;
import com.alexIntervale1.app2.jdbc.named.ResponseNamedJdbcRepoImpl;
import com.alexIntervale1.app2.mapper.ResponseMapper;
import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.MessageNotWriteableException;

@Slf4j
@Data
@RequiredArgsConstructor
@Component
public class Worker implements Runnable {

//    private final RequestRepo repo;
//    private final ResponseRepo responseRepo;
//    private final RequestJdbcRepoImpl repo;
    private final RequestNamedParameterJdbcImpl repo;
//    private final ResponseJdbcRepoImpl responseRepo;
    private final ResponseNamedJdbcRepoImpl responseRepo;
    private final Gson gson;
    private final ResponseMapper mapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void run() {
        try {
            while (true) {
                this.findPersonalNumber();
            }
        } catch (CustomAppException | InterruptedException e) {
            log.warn("Ошибка при вычитке данных из таблицы запросов, метод run() " + e);
        }
    }

    private void findPersonalNumber() throws InterruptedException, CustomAppException {
        RequestMessage message = repo.findFirstByProgressControlIsLike("in work");
        if (message != null) {
            message.setProgressControl("completed");
            log.info("Сообщение воркер после чтения запроса " + message);
            findStuff(message.getPersonalNumber(), message.getCorrelationID());
            repo.update(message);
        }
        Thread.sleep(1000);
    }

    private void findStuff(long number, String correlationId) throws CustomAppException {
        ResponseMessage responseMessage = responseRepo.findByPersonalNumber(number);
        if (responseMessage != null) {
            log.info("Сообщение ответа, по индивидуальному номеру, из базы данных  " + responseMessage);
            ResponseDto dto = mapper.toDto(responseMessage);
            String json = gson.toJson(dto);
            ActiveMQTextMessage textMessage = getMessage(correlationId, json);
            jmsTemplate.convertAndSend("outindividual.queue", textMessage);
        }
    }

    private ActiveMQTextMessage getMessage(String correlationId, String json) throws CustomAppException {
        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setCorrelationId(correlationId);
        try {
            textMessage.setText(json);
        } catch (MessageNotWriteableException e) {
            log.warn("Проблема при добавлении сообщения в textMessage" + json);
            throw new CustomAppException(e);
        }
        return textMessage;
    }
}