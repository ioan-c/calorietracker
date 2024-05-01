package com.mj.calorietracker.repository.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food_units")
public class FoodUnitEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Integer foodId;
    @ManyToOne
    @JoinColumn(name = "unit")
    private UnitEntity unit;
    private Double weightInGrams;
}
