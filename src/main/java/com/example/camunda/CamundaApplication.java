package com.example.camunda;

import com.example.camunda.domain.City;
import com.example.camunda.domain.Customer;
import com.example.camunda.service.CustomerService;
import com.example.camunda.service.ICityService;
import com.example.camunda.service.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
@Log4j2
public class CamundaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaApplication.class, args);
    }

    @Bean
    CommandLineRunner initDbCustomers(ICustomerService customerService) {
        return (args) -> Stream.of("Mark", "John", "Bob", "Mary", "Lisa")
                .map(Customer::new)
                .map(customerService::save)
                .forEach(c -> log.info("Customer: {}", c));
    }

    @Bean
    CommandLineRunner initDbCities(ICityService cityService) {
        return (args) -> Stream.of("São Paulo")
                .map(City::new)
                .map(cityService::save)
                .forEach(c -> log.info("City: {}", c));
    }


    @Bean
    @DependsOn("initDbCustomers")
    CommandLineRunner startPrintHelloProcess(ICustomerService customerService,
                                             RuntimeService runtimeService) {
        return (args) ->
                customerService
                        .findAll()
                        .forEach(customer -> {
                            runtimeService.startProcessInstanceByKey("PrintHelloProcess",
                                                                     customer.getId(),
                                                                     new HashMap<String, Object>() {{
                                                                         put("id", customer.getId());
                                                                         put("name", customer.getName());
                                                                     }});
                        });

    }

    @Bean
    CommandLineRunner startCheckWeatherProcess(RuntimeService runtimeService) {
        return (args) ->
                IntStream.rangeClosed(1, 10)
                        .forEach(i -> runtimeService.startProcessInstanceByKey("CheckWeatherProcess", Integer.toString(i)));
    }


    @Bean
    @DependsOn("initDbCities")
    CommandLineRunner startCheckWeatherCityProcess(RuntimeService runtimeService) {
        return (args) ->
                Stream.of("New York", "New York", "Rio de Janeiro", "Rio de Janeiro", "Uberlândia", "Uberlândia", "São Paulo", "São Paulo", "São Paulo")
                        .peek(cityName -> log.info("Checking if Weather is ok for "+cityName))
                        .forEach(cityName -> runtimeService.startProcessInstanceByKey("CreateCitySubProcess",
                                                                               cityName.replaceAll("\\s",""),
                                                                               new HashMap<String, Object>() {{
                                                                                   put("cityName", cityName);
                                                                               }}));
    }
}
