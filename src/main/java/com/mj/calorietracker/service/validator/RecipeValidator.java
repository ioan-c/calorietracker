package com.mj.calorietracker.service.validator;

import com.mj.calorietracker.dto.add.AddRecipe;
import com.mj.calorietracker.dto.add.AddRecipeIngredient;
import com.mj.calorietracker.dto.update.UpdateRecipeIngredient;
import com.mj.calorietracker.dto.update.UpdateRecipeIngredients;
import com.mj.calorietracker.exception.ExistingResourceException;
import com.mj.calorietracker.exception.model.ErrorInfoForList;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.FoodUnitRepository;
import com.mj.calorietracker.repository.RecipeIngredientRepository;
import com.mj.calorietracker.repository.RecipeRepository;
import com.mj.calorietracker.repository.dao.RecipeIngredientEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.mj.calorietracker.enums.ExceptionMessages.*;
import static com.mj.calorietracker.mapper.FoodMapper.foodMapper;
import static com.mj.calorietracker.mapper.RecipeMapper.recipeMapper;

@Component
@AllArgsConstructor
public class RecipeValidator extends Validator {

    private FoodRepository foodRepository;
    private FoodUnitRepository foodUnitRepository;
    private RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public void validateRecipe(AddRecipe addRecipe) {
        recipeRepository.findByNameEqualsIgnoreCase(addRecipe.name()).ifPresent(r -> {
            throw new ExistingResourceException(recipeMapper.toRecipe(r), RECIPE_CONFLICT.getText());
        });
        foodRepository.findByNameEqualsIgnoreCaseAndBrandIsNullAndIsCurrentTrue(addRecipe.name()).ifPresent(f -> {
            throw new ExistingResourceException(foodMapper.toFood(f), FOOD_NAME_CONFLICT.getText());
        });
        validateIngredientsForAddRecipe(addRecipe.ingredients());
    }

    private void validateIngredientsForAddRecipe(List<AddRecipeIngredient> addRecipeIngredients) {
        List<ErrorInfoForList> errorInfoList = validateAddIngredients(addRecipeIngredients, null);
        digestErrorList(errorInfoList);
    }

    private List<ErrorInfoForList> validateAddIngredients(List<AddRecipeIngredient> addRecipeIngredients, String additionalNote) {
        return IntStream.range(0, addRecipeIngredients.size())
                .mapToObj(i -> {
                    AddRecipeIngredient addRecipeIngredient = addRecipeIngredients.get(i);
                    return validateAddIngredient(addRecipeIngredient, i, additionalNote);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private ErrorInfoForList validateAddIngredient(AddRecipeIngredient addRecipeIngredient, int i, String additionalNote) {
        if(!foodRepository.existsById(addRecipeIngredient.foodId())) {
            return new ErrorInfoForList(FOOD_NOT_FOUND.getText(), i, additionalNote);
        } else {
            return validateIngredientUnit(addRecipeIngredient.foodId(), addRecipeIngredient.foodUnitId(), i, additionalNote);
        }
    }

    private ErrorInfoForList validateIngredientUnit(Integer foodId, Integer foodUnitId, int i, String additionalNote) {
        if(!foodUnitRepository.existsForFood(foodId, foodUnitId)) {
            return new ErrorInfoForList(UNIT_NOT_FOUND.getText(), i, additionalNote);
        }
        return null;
    }

    public void validateIngredientsForUpdateRecipe(UpdateRecipeIngredients updateRecipeIngredients) {
        List<AddRecipeIngredient> addRecipeIngredients = updateRecipeIngredients.addIngredients();
        List<UpdateRecipeIngredient> updateRecipeIngredientList = updateRecipeIngredients.updateIngredients();
        List<ErrorInfoForList> errorsOnAddIngredients = Optional.ofNullable(addRecipeIngredients)
                .map(addList -> validateAddIngredients(addList, ADDITIONAL_NOTE_ADD_INGREDIENTS.getText()))
                .orElse(Collections.emptyList());
        List<ErrorInfoForList> errorsOnUpdateIngredients = Optional.ofNullable(updateRecipeIngredientList)
                .map(updateList -> validateUpdateIngredients(updateList, ADDITIONAL_NOTE_UPDATE_INGREDIENTS.getText()))
                .orElse(Collections.emptyList());
        digestErrorList(Stream.concat(errorsOnAddIngredients.stream(), errorsOnUpdateIngredients.stream()).toList());
    }

    private List<ErrorInfoForList> validateUpdateIngredients(List<UpdateRecipeIngredient> updateRecipeIngredients, String additionalNote) {
        return IntStream.range(0, updateRecipeIngredients.size())
                .mapToObj(i -> {
                    UpdateRecipeIngredient updateRecipeIngredient = updateRecipeIngredients.get(i);
                    return validateUpdateIngredient(updateRecipeIngredient, i, additionalNote);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private ErrorInfoForList validateUpdateIngredient(UpdateRecipeIngredient updateRecipeIngredient, int i, String additionalNote) {
        Optional<RecipeIngredientEntity> recipeIngredientEntityOptional = recipeIngredientRepository.findById(updateRecipeIngredient.id());
        if(recipeIngredientEntityOptional.isPresent()) {
            return validateIngredientUnit(recipeIngredientEntityOptional.get().getFood().getId(), updateRecipeIngredient.foodUnitId(), i, additionalNote);
        } else {
            return new ErrorInfoForList(INGREDIENT_NOT_FOUND.getText(), i, additionalNote);
        }
    }
}
