package com.alexIntervale1.app2.service;

import com.alexIntervale1.app2.mapper.ResponseMapper;
import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.model.ResponseMessage;
import com.alexIntervale1.app2.repository.impl.named.RequestNamedParameterJdbcImpl;
import com.alexIntervale1.app2.repository.impl.named.ResponseNamedJdbcRepoImpl;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@RequiredArgsConstructor
@Component
public class WorkerService {
    private final RequestNamedParameterJdbcImpl repo;
    private final ResponseNamedJdbcRepoImpl responseRepo;
    private final Gson gson;
    private final ResponseMapper mapper;

    private final String PROGRESS_CONTROL_WORK_STATUS = "in work";
    private final String PROGRESS_CONTROL_COMPLETED_STATUS = "completed";

    public RequestMessage findRequestObjectInDbBasedByProgressControlField() throws InterruptedException {
        RequestMessage requestMessageInWork = repo.findFirstByProgressControlIsLike(PROGRESS_CONTROL_WORK_STATUS);
        if (requestMessageInWork != null) {
            requestMessageInWork.setProgressControl(PROGRESS_CONTROL_COMPLETED_STATUS);
            return repo.update(requestMessageInWork);
        }
        Thread.sleep(1000);
        return findRequestObjectInDbBasedByProgressControlField();
    }

    public ResponseMessage findResponseObjectInDbByPersonalNumber(RequestMessage requestMessage) {
        ResponseMessage responseMessageByPersonalNumber = new ResponseMessage();
        try {
            responseMessageByPersonalNumber = responseRepo.findByPersonalNumber(requestMessage.getPersonalNumber());
            responseMessageByPersonalNumber.setCorrelationID(requestMessage.getCorrelationID());
            log.info("Сообщение ответа, по индивидуальному номеру, из базы данных {} ", responseMessageByPersonalNumber);
        } catch (Exception e) {
            log.warn("Штраф в базе данных по индивидуальному номеру {} не найден", requestMessage.getPersonalNumber(), e);
        }
        return responseMessageByPersonalNumber;
    }

}
