package com.example.camunda.service;

import com.example.camunda.domain.City;

import java.util.List;
import java.util.Optional;

public interface ICityService {
    Optional<City> findById(String id);
    Optional<City> findByName(String name);
    List<City> findAll();
    City save(City customer);
    Boolean exists(String id);
}
