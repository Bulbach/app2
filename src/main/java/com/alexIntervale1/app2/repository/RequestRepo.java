package com.alexIntervale1.app2.repository;

import com.alexIntervale1.app2.model.RequestMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<RequestMessage,Long> {
    RequestMessage findFirstByProgressControlIsLike(String str);
}
