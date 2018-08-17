package com.example.camunda.repository;

import com.example.camunda.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "customers")
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByName(@Param("name") String name);
}
