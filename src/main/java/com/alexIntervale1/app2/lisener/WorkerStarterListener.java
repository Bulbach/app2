package com.alexIntervale1.app2.lisener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorkerStarterListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Runnable worker;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Thread thread = new Thread(worker, "Worker");
        log.info("Старт потока "+ worker);
        thread.start();
    }
}
