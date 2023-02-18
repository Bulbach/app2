package com.alexIntervale1.app2.worker;

import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.service.ActiveMQBrokerService;
import com.alexIntervale1.app2.service.WorkerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@RequiredArgsConstructor
@Component
public class Worker implements Runnable {

    private final WorkerService workerService;
    private final ActiveMQBrokerService activeMQBrokerService;
    private boolean flowCondition = true;

    @Override
    public void run() {
        while (flowCondition) {
            try {
                RequestMessage objectInDbBasedByProgressControlField
                        = this.workerService.findRequestObjectInDbBasedByProgressControlField();

                ResponseMessage responseObjectInDbByPersonalNumber
                        = workerService.findResponseObjectInDbByPersonalNumber(objectInDbBasedByProgressControlField);

                ActiveMQTextMessage messageForBrokerActiveMqQueue
                        = activeMQBrokerService.createMessageForBrokerActiveMqQueue(responseObjectInDbByPersonalNumber);

                activeMQBrokerService.sendPreparedMessage(messageForBrokerActiveMqQueue);

            } catch (InterruptedException e) {
                log.warn("Ошибка при вычитке данных из таблицы request", e);
                flowCondition=false;
            }
        }

    }
}
