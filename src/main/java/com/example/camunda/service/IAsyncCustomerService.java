package com.example.camunda.service;

public interface IAsyncCustomerService {
    String send(AsyncCustomerService.CustomerCreateCommand command);

    void on(AsyncCustomerService.CustomerCreatedEvent event);
}
