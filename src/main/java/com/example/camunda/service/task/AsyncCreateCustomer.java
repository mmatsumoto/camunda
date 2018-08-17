package com.example.camunda.service.task;

import com.example.camunda.domain.Customer;
import com.example.camunda.service.AsyncCustomerService;
import com.example.camunda.service.AsyncCustomerService.CustomerCreateCommand;
import com.example.camunda.service.IAsyncCustomerService;
import com.example.camunda.service.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class AsyncCreateCustomer implements JavaDelegate {

    private final IAsyncCustomerService asyncCustomerService;

    public AsyncCreateCustomer(IAsyncCustomerService asyncCustomerService) {
        this.asyncCustomerService = asyncCustomerService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        final String customerId = UUID.randomUUID().toString();

        CustomerCreateCommand command = new CustomerCreateCommand(customerId,
                                                                       (String) execution.getVariable("customerName"),
                                                                       execution.getBusinessKey(),
                                                                       execution.getId());
        log.info("Dispatching CustomerCreateCommand {}", command);
        asyncCustomerService.send(command);

        log.info("CustomerCreateCommand {} dispatched!", command);
    }

}
