package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.RecipeIngredientEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends AppRepository<RecipeIngredientEntity, Integer> {
    List<RecipeIngredientEntity> findAllByRecipeId(Integer recipeId);
    void deleteAllByRecipeId(Integer recipeId);
}
