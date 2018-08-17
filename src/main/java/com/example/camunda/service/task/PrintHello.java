package com.example.camunda.service.task;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Log4j2
public class PrintHello implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        log.info("Starting id: {}, instanceId: {}, processBusinessKey: {}, execution: {}",
                 execution.getId(),
                 execution.getProcessInstanceId(),
                 execution.getProcessBusinessKey(),
                 execution);

        String name = (String) execution.getVariable("name");
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Please, inform a name variable!");
        }

        log.info("HELLO {} !!! @ {} ", name, Instant.now());

        log.info("Done id: {}, instanceId: {}, processBusinessKey: {}",
                 execution.getId(),
                 execution.getProcessInstanceId(),
                 execution.getProcessBusinessKey());
    }
}
