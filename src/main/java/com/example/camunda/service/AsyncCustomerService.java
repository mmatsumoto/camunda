package com.example.camunda.service;

import com.example.camunda.domain.Customer;
import com.example.camunda.service.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Log4j2
public class AsyncCustomerService implements IAsyncCustomerService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final ICustomerService customerService;
    private final RuntimeService runtimeService;

    public AsyncCustomerService(ICustomerService customerService, RuntimeService runtimeService) {
        this.customerService = customerService;
        this.runtimeService = runtimeService;
    }

    @Override
    public String send(final CustomerCreateCommand command) {

        if ("invalid".equals(command.getName().toLowerCase())) {
            log.error("Customer name can't be equals to 'Invalid'");
        } else {
            executorService.submit(asyncCreateCustomer(command));
        }

        return command.getId();
    }

    private Runnable asyncCreateCustomer(CustomerCreateCommand command) {
        return () -> {
            try {
                log.info("Waiting 10s to process create CustomerCreateCommand {}", command);
                Thread.sleep(10000);

                final Customer customer = customerService.save(
                        new Customer(command.getId(), command.getName(), null));


                on(new CustomerCreatedEvent(customer.getId(),
                                            customer.getName(),
                                            Instant.now(),
                                            command.getBusinessKey(),
                                            command.getExecutionId()));

                log.info("Customer {} created!", customer);
            } catch (InterruptedException e) {
                log.info("Error creating processing CustomerCreateCommand {} ", command);
            }
        };
    }


    @Override
    public void on(final CustomerCreatedEvent event) {

        log.info("CustomerCreatedEvent event received!");

        final Map<String, Object> variables = new HashMap<String, Object>() {{
            put("customerExists", true);
            put("customerId", event.getId());
        }};

        try {
            runtimeService.correlateMessage("CustomerCreatedEvent", event.getBusinessKey(), variables);

            log.info("correlateMessage dispatched! businessKey={}, variables={}", event.getBusinessKey(), variables);
        } catch (Exception e) {
            log.info("Error dispatching correlateMessage! businessKey={}, variables={}", event.getBusinessKey(), variables);
            runtimeService.removeVariables(event.getExecutionId(), variables.keySet());
        }
    }

    @PreDestroy
    public void onDestroy() {
        executorService.shutdown();
    }


    @Data
    @AllArgsConstructor
    public static class CustomerCreateCommand {
        private String id;
        private String name;
        private String businessKey;
        private String executionId;
    }

    @Data
    @AllArgsConstructor
    public static class CustomerCreatedEvent {
        private String id;
        private String name;
        private Instant time;
        private String businessKey;
        private String executionId;
    }

}
