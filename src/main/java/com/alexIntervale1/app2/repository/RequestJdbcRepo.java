package com.alexIntervale1.app2.repository;

import com.alexIntervale1.app2.model.RequestMessage;

import java.util.List;

public interface RequestJdbcRepo {

    RequestMessage save(RequestMessage requestMessage);

    RequestMessage update(RequestMessage requestMessage);

    void deleteById(Long id);

    List<RequestMessage> findAll();


    RequestMessage findById(Long id);

    int countByProgressControlIsLike(String str);

    RequestMessage findFirstByProgressControlIsLike(String str);
}
