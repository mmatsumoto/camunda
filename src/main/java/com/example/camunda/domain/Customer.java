package com.example.camunda.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {

    public Customer(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.cityId = null;
    }

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String cityId;

    public Customer updateCity(String cityId) {
        this.cityId = cityId;
        return this;
    }
}
