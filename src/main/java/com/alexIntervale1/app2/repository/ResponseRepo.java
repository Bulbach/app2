package com.alexIntervale1.app2.repository;

import com.alexIntervale1.app2.model.ResponseMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepo extends JpaRepository<ResponseMessage,Long> {

    ResponseMessage findByPersonalNumber (Long aLong);
}
