package com.mj.calorietracker.repository.dao;

import com.mj.calorietracker.enums.Meal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "diary_entries")
public class DiaryEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    private String name;
    private String brand;
    private LocalDate entryDate;
    private UUID userId;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;
    private Double servingQuantity;
    @Enumerated(EnumType.STRING)
    @ColumnTransformer(write="?::meal")
    private Meal meal;
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
