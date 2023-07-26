package com.mj.calorietracker.repository.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "foods")
@DynamicInsert
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    private String barcode;
    private String name;
    private String brand;
    private LocalDate createdDate;
    private Boolean isCurrent;
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
    @Column(name = "vitamin_a")
    private Integer vitaminA;
    @Column(name = "vitamin_c")
    private Integer vitaminC;
    @Column(name = "vitamin_d")
    private Integer vitaminD;
}
