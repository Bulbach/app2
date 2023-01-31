package com.alexIntervale1.app2.jdbc;

import com.alexIntervale1.app2.model.ResponseMessage;

import java.util.List;

public interface ResponseJdbcRepo {
    int save(ResponseMessage responseMessage);

    int update(ResponseMessage responseMessage);

    void deleteById(Long id);

    List<ResponseMessage> findAll();


    ResponseMessage findById(Long id);

    ResponseMessage findByPersonalNumber(Long aLong);
}
