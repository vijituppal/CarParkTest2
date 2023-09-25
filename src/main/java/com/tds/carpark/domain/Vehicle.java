package com.tds.carpark.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Vehicle {
    private String registration;
}
