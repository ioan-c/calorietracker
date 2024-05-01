package com.mj.calorietracker.repository.dao;

import com.mj.calorietracker.enums.Meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "diary_entries")
public class DiaryEntryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private LocalDate entryDate;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "food_unit_id")
    private FoodUnitEntity unit;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food;
    private Double servingQuantity;
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::meal")
    private Meal meal;
}
