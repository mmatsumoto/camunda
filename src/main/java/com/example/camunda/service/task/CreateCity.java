package com.example.camunda.service.task;

import com.example.camunda.domain.City;
import com.example.camunda.service.ICityService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CreateCity implements JavaDelegate {

    private final ICityService cityService;

    public CreateCity(ICityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final String cityName = (String) execution.getVariable("cityName");

        if ("invalid".equals(cityName.toLowerCase())) {
            throw new IllegalArgumentException("City name can't be equals to 'Invalid'");
        }

        try {

            final City city = cityService.save(new City(cityName));
            execution.setVariable("cityExists", true);
            execution.setVariable("cityId", city.getId());
            log.info("City created! {}", city);
        } catch (Exception e) {
            log.error("Error trying to save City {}", cityName, e);
            execution.setVariable("cityExists", false);
        }
    }
}
