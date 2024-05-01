package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.RecipeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends AppRepository<RecipeEntity, Integer> {
    List<RecipeEntity> findAllByNameLikeIgnoreCase(String name);
    List<RecipeEntity> findAllByNameContainsIgnoreCase(String name);
    Optional<RecipeEntity> findByNameEqualsIgnoreCase(String name);
    @Modifying
    @Query("UPDATE RecipeEntity f SET f.name = ?2 WHERE f.id = ?1")
    void updateRecipeName(Integer id, String newName);
    @Modifying
    @Query("UPDATE RecipeEntity f SET f.description = ?2 WHERE f.id = ?1")
    void updateRecipeDescription(Integer id, String newDescription);
}
