package com.alexIntervale1.app2.worker;

import com.alexIntervale1.app2.dto.ResponseDto;
import com.alexIntervale1.app2.mapper.ResponseMapper;
import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.repository.RequestRepo;
import com.alexIntervale1.app2.repository.ResponseRepo;
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

    private final RequestRepo repo;
    private final ResponseRepo responseRepo;
    private final Gson gson;
    private final ResponseMapper mapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void run() {
        try {
            while (true) {
                this.findIndividualNumber();
            }
        } catch (MessageNotWriteableException | InterruptedException e) {
            log.warn("Ошибка при вычитке данных из таблицы запросов, метод run() " + e.getMessage());
        }
    }

    private void findIndividualNumber() throws InterruptedException, MessageNotWriteableException {
        RequestMessage message = repo.findFirstByProgressControlIsLike("in work");
        if (message != null) {
            message.setProgressControl("completed");
            log.info("Сообщение воркер после чтения запроса " + message);
            findStuff(message.getPersonalNumber(), message.getCorrelationID());
            repo.save(message);
        }
        Thread.sleep(1000);
    }

    private void findStuff(long number, String correlationId) throws MessageNotWriteableException {
        ResponseMessage responseMessage = responseRepo.findByIndividualNumber(number);
        if (responseMessage != null) {
            log.info("Сообщение ответа, по индивидуальному номеру, из базы данных  " + responseMessage);
            ResponseDto dto = mapper.toDto(responseMessage);
            String json = gson.toJson(dto);
            try {
                ActiveMQTextMessage textMessage = getMessage(correlationId, json);
                jmsTemplate.convertAndSend("outindividual.queue", textMessage);
            } catch (MessageNotWriteableException e) {
                log.warn("Проблема при отправке сообщения в очередь, метод findStuff ", e);
                throw new MessageNotWriteableException("Проблема при отправки сообщения", e.getMessage());
            }
        }
    }

    private ActiveMQTextMessage getMessage(String correlationId, String json) throws MessageNotWriteableException {
        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setCorrelationId(correlationId);
        textMessage.setText(json);
        return textMessage;
    }
}
