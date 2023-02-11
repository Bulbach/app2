package com.alexIntervale1.app2.jdbc;

import com.alexIntervale1.app2.model.ResponseMessage;

import java.util.List;

public interface ResponseJdbcRepo {
    ResponseMessage save(ResponseMessage responseMessage);

    ResponseMessage update(ResponseMessage responseMessage);

    void deleteById(Long id);

    List<ResponseMessage> findAll();


    ResponseMessage findById(Long id);

    ResponseMessage findByPersonalNumber(Long aLong);
}
