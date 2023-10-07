package com.mj.calorietracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Unit {

    private UUID id;
    private String name;
    private Double weightInGrams;
}
