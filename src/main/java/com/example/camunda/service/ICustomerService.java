package com.example.camunda.service;

import com.example.camunda.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    Optional<Customer> findById(String id);
    Optional<Customer> findByName(String name);
    List<Customer> findAll();
    Customer save(Customer customer);
    Boolean exists(String id);
}
