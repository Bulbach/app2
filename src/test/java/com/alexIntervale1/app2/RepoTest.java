package com.alexIntervale1.app2;

import com.alexIntervale1.app2.model.RequestMessage;
import com.alexIntervale1.app2.repository.RequestRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepoTest {

    @Autowired
    private RequestRepo repo;

    @Test
    public void test() {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setName("test");
        requestMessage.setProgressControl("test");
        requestMessage.setSurname("test");
        requestMessage.setPersonalNumber(12L);

        RequestMessage save = repo.save(requestMessage);

        Assertions.assertThat(save.getName()).isEqualTo("test");
    }
}
