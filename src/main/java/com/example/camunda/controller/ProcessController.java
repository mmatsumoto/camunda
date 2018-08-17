package com.example.camunda.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ProcessController {

    private final RuntimeService runtimeService;

    public ProcessController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping(path = "/restart-process", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> restartProcess(@RequestBody @Valid ProcessRestartRequest request) {


        if (request.getVariables() != null) {
            runtimeService.setVariables(request.getExecutionId(), request.getVariables());
        }

        runtimeService.restartProcessInstances(request.getProcessDefinitionId() )
                .processInstanceIds(request.getExecutionId())
                .startBeforeActivity(request.getBeforeActivity())
                .execute();

        return new HashMap<String, Object>() {{
            put("executionId", request.getExecutionId());
            put("processDefinitionId", request.getProcessDefinitionId());
            put("variables", request.getVariables());
        }};
    }

    @NoArgsConstructor
    @Data
    public static class ProcessRestartRequest {
        @NotEmpty
        @NotNull
        private String executionId;

        @NotEmpty
        @NotNull
        private String processDefinitionId;

        @NotEmpty
        @NotNull
        private String beforeActivity;

        private Map<String, Object> variables;
    }
}
