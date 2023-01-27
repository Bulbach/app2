package com.alexIntervale1.app2;

import com.alexIntervale1.app2.worker.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@Slf4j
public class App2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App2Application.class, args);

        Runnable worker = context.getBean("worker", Worker.class);
        Thread thread = new Thread(worker, "Worker");
        log.info("Старт потока "+ worker);
        thread.start();

    }

}
