package com.example.camunda.service;

import com.example.camunda.domain.City;
import com.example.camunda.repository.CityRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService implements ICityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<City> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<City> findByName(String name) {
        return repository.findOne(Example.of(new City(name),
                                             ExampleMatcher.matchingAny()
                                                     .withIgnoreCase()
                                                     .withIgnoreNullValues()));
    }

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public City save(City city) {
        return repository.save(
                findByName(city.getName())
                        .orElseGet(() -> city));
    }

    @Override
    public Boolean exists(String id) {
        return repository.existsById(id);
    }
}
