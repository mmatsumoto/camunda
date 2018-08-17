package com.example.camunda.repository;

import com.example.camunda.domain.City;
import com.example.camunda.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "cities")
public interface CityRepository extends JpaRepository<City, String> {
    List<City> findByName(@Param("name") String name);
}
