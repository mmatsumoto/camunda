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
public class City {

    public City(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    @Id
    private String id;

    @Column
    private String name;
}
