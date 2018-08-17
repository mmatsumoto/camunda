package com.example.camunda.service.task;

import com.example.camunda.domain.Customer;
import com.example.camunda.repository.CustomerRepository;
import com.example.camunda.service.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Log4j2
public class CheckCustomer implements JavaDelegate {

    private final ICustomerService customerService;

    public CheckCustomer(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        final String customerName = (String) execution.getVariable("customerName");
        final Optional<Customer> oCustomer = customerService.findByName(customerName);

        if (oCustomer.isPresent()) {
            log.info("Customer already exists {} ", customerName);
            execution.setVariable("customerExists", true);
            execution.setVariable("customerId", oCustomer.get().getId());
        } else {
            log.info("Customer does not exists {} ", customerName);
            execution.setVariable("customerExists", false);
        }

    }
}
