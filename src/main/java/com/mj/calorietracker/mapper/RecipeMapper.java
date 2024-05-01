package com.mj.calorietracker.mapper;

import com.mj.calorietracker.dto.Recipe;
import com.mj.calorietracker.dto.RecipeIngredient;
import com.mj.calorietracker.dto.add.AddRecipe;
import com.mj.calorietracker.dto.add.AddRecipeIngredient;
import com.mj.calorietracker.dto.update.UpdateRecipeIngredient;
import com.mj.calorietracker.repository.dao.FoodUnitEntity;
import com.mj.calorietracker.repository.dao.RecipeEntity;
import com.mj.calorietracker.repository.dao.RecipeIngredientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {FoodMapper.class, UnitMapper.class})
public interface RecipeMapper {
    RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);

    Recipe toRecipe(RecipeEntity recipeEntity);
    RecipeEntity toRecipeEntity(AddRecipe addRecipe);
    RecipeIngredient toRecipeIngredient(RecipeIngredientEntity recipeIngredientEntity);
    @Mapping(target = "recipeId", source = "recipeId")
    @Mapping(target = "food.id", source = "addRecipeIngredient.foodId")
    @Mapping(target = "unit.id", source = "addRecipeIngredient.foodUnitId")
    RecipeIngredientEntity toRecipeIngredientEntity(AddRecipeIngredient addRecipeIngredient, Integer recipeId);
    default void updateIngredientFromDto(UpdateRecipeIngredient dto, RecipeIngredientEntity entity) {
        if ( dto.foodUnitId() != null ) {
            entity.setUnit( new FoodUnitEntity().setId(dto.foodUnitId()) );
        }
        if ( dto.quantity() != null ) {
            entity.setQuantity( dto.quantity() );
        }
    }
}
