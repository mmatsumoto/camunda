package com.example.camunda.service.task;

import com.example.camunda.domain.City;
import com.example.camunda.domain.Customer;
import com.example.camunda.service.ICityService;
import com.example.camunda.service.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UpdateCustomerWithCityId implements JavaDelegate {

    private final ICustomerService customerService;
    private final ICityService cityService;

    public UpdateCustomerWithCityId(ICustomerService customerService, ICityService cityService) {
        this.customerService = customerService;
        this.cityService = cityService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        final String customerId = (String) execution.getVariable("customerId");
        final String cityId = (String) execution.getVariable("cityId");

        Optional<Customer> oCustomer = customerService.findById(customerId);
        if (!oCustomer.isPresent()) {
            throw new IllegalArgumentException("Invalid Customer Id " + customerId);
        }

        Optional<City> oCity = cityService.findById(cityId);
        if (!oCity.isPresent()) {
            throw new IllegalArgumentException("Invalid City Id " + customerId);
        }

        oCustomer.map(c -> c.updateCity(cityId))
                .map(customerService::save)
                .ifPresent(customer -> log.info("Customer {} updated with City {}", customer, oCity.get()));

    }
}
