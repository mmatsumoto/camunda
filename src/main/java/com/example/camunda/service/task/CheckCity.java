package com.example.camunda.service.task;

import com.example.camunda.domain.City;
import com.example.camunda.service.ICityService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class CheckCity implements JavaDelegate {

    private final ICityService cityService;

    public CheckCity(ICityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        final String cityName = (String) execution.getVariable("cityName");
        final Optional<City> oCity = cityService.findByName(cityName);

        if (oCity.isPresent()) {
            log.info("City already exists {} ", cityName);
            execution.setVariable("cityExists", true);
            execution.setVariable("cityId", oCity.get().getId());
        } else {
            log.info("City does not exists {} ", cityName);
            execution.setVariable("cityExists", false);
        }

    }
}
