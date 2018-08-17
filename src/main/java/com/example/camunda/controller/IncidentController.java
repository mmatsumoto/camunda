package com.example.camunda.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.IncidentQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "incidents")
@Log4j2
public class IncidentController {

    private final RuntimeService runtimeService;

    public IncidentController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @GetMapping
    public Object incident() {
        return incident(null);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Object incident(@RequestBody(required = false) @Valid IncidentRequest request) {
        IncidentQuery query = runtimeService.createIncidentQuery();
        if (request != null) {
            query.executionId(request.getExecutionId())
                    .processInstanceId(request.getExecutionId())
                    .processDefinitionId(request.getProcessDefinitionId());
        }

        return query.list()
                .stream()
                .map(i -> new HashMap<String, Object>() {{
                    put("id", i.getId());
                    put("processDefinitionId", i.getProcessDefinitionId());
                    put("activityId", i.getActivityId());
                    put("executionId", i.getExecutionId());
                    put("incidentType", i.getIncidentType());
                    put("incidentTimestamp", i.getIncidentTimestamp());
                    put("incidentMessage", i.getIncidentMessage());
                    put("jobDefinitionId", i.getJobDefinitionId());
                }})
                .collect(Collectors.toList());
    }

    @PutMapping(path = "/{incidentId}", consumes = APPLICATION_JSON_VALUE)
    public String resolveIncident(@PathVariable String incidentId, @RequestBody @Valid ResolveIncidentRequest request) {

        if (request.getVariables() != null) {
            runtimeService.setVariables(request.getExecutionId(), request.getVariables());
        }
        runtimeService.resolveIncident(incidentId);

        log.info("runtimeService.resolveIncident({}); executionId={}, variables={}",
                 incidentId,
                 request.getExecutionId(),
                 request.getVariables());

        return incidentId;
    }

    @NoArgsConstructor
    @Data
    public static class IncidentRequest {
        private String executionId;
        private String processDefinitionId;
    }

    @NoArgsConstructor
    @Data
    public static class ResolveIncidentRequest {
        private String executionId;

        private Map<String, Object> variables;
    }
}
