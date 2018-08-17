package com.example.camunda.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "jobs")
@Log4j2
public class JobController {

    private final RuntimeService runtimeService;
    private final ManagementService managementService;

    public JobController(RuntimeService runtimeService, ManagementService managementService) {
        this.runtimeService = runtimeService;
        this.managementService = managementService;
    }

    @PutMapping(path = "/{jobDefinition}/retry/{times}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void retry(@PathVariable String jobDefinition,
                      @PathVariable(required = false) Integer times,
                      @RequestBody JobRetryRequest request) {
        if (request.getVariables() != null) {
            runtimeService.setVariables(request.getExecutionId(), request.getVariables());
        }

        managementService.setJobRetriesByJobDefinitionId(jobDefinition, (times == null) ? 1 : times);
        log.info("Retrying jobDefinition={}", jobDefinition);
    }

    @NoArgsConstructor
    @Data
    public static class JobRetryRequest {
        private String executionId;

        private Map<String, Object> variables;
    }
}
