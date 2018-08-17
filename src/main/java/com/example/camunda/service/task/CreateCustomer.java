package com.example.camunda.service.task;

import com.example.camunda.domain.Customer;
import com.example.camunda.service.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreateCustomer implements JavaDelegate {

    private final ICustomerService customerService;

    public CreateCustomer(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final String customerName = (String) execution.getVariable("customerName");

        try {
            if ("invalid".equals(customerName.toLowerCase())) {
                throw new IllegalArgumentException("Customer name can't be equals to 'Invalid'");
            }

            final Customer customer = customerService.save(new Customer(customerName));
            execution.setVariable("customerExists", true);
            execution.setVariable("customerId", customer.getId());
            log.info("Customer created! {}", customer);

        } catch (Exception e) {
            log.error("Error trying to save Customer {}", customerName, e);
            execution.setVariable("customerExists", false);
        }
    }
}
