package com.example.camunda.service;

import com.example.camunda.domain.Customer;
import com.example.camunda.repository.CustomerRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return repository.findOne(Example.of(new Customer(name),
                                                               ExampleMatcher.matchingAny()
                                                                       .withIgnoreCase()
                                                                       .withIgnoreNullValues()));
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Boolean exists(String id) {
        return repository.existsById(id);
    }
}
