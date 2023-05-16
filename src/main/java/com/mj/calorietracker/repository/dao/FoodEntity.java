package com.mj.calorietracker.repository.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "foods")
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    private String barcode;
    private String name;
    private String brand;
    private LocalDate createdDate;
    private Integer calories;
    private Double fat;
    private Double fatSaturated;
    private Double fatTrans;
    private Double fatPolyunsaturated;
    private Double fatMonounsaturated;
    private Integer cholesterol;
    private Double carbohydrates;
    private Double fiber;
    private Double sugar;
    private Double protein;
    private Integer sodium;
    private Integer potassium;
    private Integer calcium;
    private Integer iron;
    private Integer vitaminA;
    private Integer vitaminC;
    private Integer vitaminD;
}
