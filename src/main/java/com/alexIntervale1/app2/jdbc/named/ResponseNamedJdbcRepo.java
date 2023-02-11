package com.alexIntervale1.app2.jdbc.named;

import com.alexIntervale1.app2.model.ResponseMessage;

import java.util.List;

public interface ResponseNamedJdbcRepo {
    ResponseMessage save(ResponseMessage responseMessage);

    ResponseMessage update(ResponseMessage responseMessage);

    void deleteById(Long id);

    List<ResponseMessage> findAll();


    ResponseMessage findById(Long id);

    ResponseMessage findByPersonalNumber(Long aLong);
}
