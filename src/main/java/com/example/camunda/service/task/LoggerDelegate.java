package com.example.camunda.service.task;

import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Log4j2
public class LoggerDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("\n\n LoggerDelegate invoked by " +
                         "processDefinitionId=" + execution.getProcessDefinitionId() +
                         ", activityId=" + execution.getCurrentActivityId() +
                         ", activityName=" + execution.getCurrentActivityName() +
                         ", processInstanceId="+ execution.getProcessInstanceId() +
                         ", businessKey=" + execution.getBusinessKey() +
                         ", executionId=" + execution.getId() + "\n\n");
    }
}
