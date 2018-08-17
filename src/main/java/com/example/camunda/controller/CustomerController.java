package com.example.camunda.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class CustomerController {

    private final RuntimeService runtimeService;

    public CustomerController(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @PostMapping(path="create-customer", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Map<String, String> createCustomer(@RequestBody @Valid CustomerRequest request) {

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("CreateCustomerProcess",
                                                                                         UUID.randomUUID().toString(),
                                                                                         new HashMap<String, Object>() {{
                                                                                             put("customerName", request.getCustomerName());
                                                                                             put("cityName", request.getCityName());
                                                                                         }});

        return new HashMap<String, String>() {{
            put("executionId", processInstance.getId());
            put("businessKey", processInstance.getBusinessKey());
            put("processDefinitionId", processInstance.getProcessDefinitionId());
        }};
    }


    @PostMapping(path="async-create-customer", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Map<String, String> asyncCreateCustomer(@RequestBody @Valid CustomerRequest request) {

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("AsyncCreateCustomerProcess",
                                                                                         UUID.randomUUID().toString(),
                                                                                         new HashMap<String, Object>() {{
                                                                                             put("customerName", request.getCustomerName());
                                                                                             put("cityName", request.getCityName());
                                                                                         }});

        return new HashMap<String, String>() {{
            put("executionId", processInstance.getId());
            put("businessKey", processInstance.getBusinessKey());
            put("processDefinitionId", processInstance.getProcessDefinitionId());
        }};
    }




    @NoArgsConstructor
    @Data
    public static class CustomerRequest {
        @NotEmpty
        @NotNull
        private String customerName;

        @NotEmpty
        @NotNull
        private String cityName;
    }


}
