package com.mj.calorietracker.repository.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredientEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private Integer recipeId;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food;
    @ManyToOne
    @JoinColumn(name = "food_unit_id")
    private FoodUnitEntity unit;
    private Double quantity;
}
