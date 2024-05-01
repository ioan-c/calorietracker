package com.mj.calorietracker.service;

import com.mj.calorietracker.dto.Recipe;
import com.mj.calorietracker.dto.RecipeIngredient;
import com.mj.calorietracker.dto.add.AddRecipe;
import com.mj.calorietracker.dto.add.AddRecipeIngredient;
import com.mj.calorietracker.dto.update.UpdateRecipeIngredient;
import com.mj.calorietracker.dto.update.UpdateRecipeIngredients;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.repository.*;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.dao.RecipeEntity;
import com.mj.calorietracker.repository.dao.RecipeIngredientEntity;
import com.mj.calorietracker.service.validator.RecipeValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_BY_NAME_NOT_FOUND;
import static com.mj.calorietracker.enums.ExceptionMessages.RECIPE_NOT_FOUND;
import static com.mj.calorietracker.mapper.RecipeMapper.recipeMapper;
import static com.mj.calorietracker.util.NutritionValueConvertor.getRecipeFood;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Transactional
@AllArgsConstructor
public class RecipeService {
    private RecipeRepository recipeRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private FoodRepository foodRepository;
    private RecipeValidator validator;

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::toRecipe)
                .toList();
    }

    public List<Recipe> findAllRecipesByName(String name) {
        return recipeRepository.findAllByNameContainsIgnoreCase(name).stream()
                .map(recipeMapper::toRecipe)
                .toList();
    }

    public List<RecipeIngredient> findRecipeIngredients(Integer recipeId) {
        return recipeIngredientRepository.findAllByRecipeId(recipeId).stream()
                .map(recipeMapper::toRecipeIngredient)
                .toList();
    }

    public Integer addRecipe(AddRecipe addRecipe) {
        validator.validateRecipe(addRecipe);
        RecipeEntity recipe = recipeRepository.save(recipeMapper.toRecipeEntity(addRecipe));
        recipeIngredientRepository.saveAll(addRecipe.ingredients().stream().map(i -> recipeMapper.toRecipeIngredientEntity(i, recipe.getId())).toList());
        recipeIngredientRepository.clear();
        foodRepository.save(getRecipeFood(recipe.getName(), recipeIngredientRepository.findAllByRecipeId(recipe.getId()), recipe.getCookedWeight()));
        return recipe.getId();
    }

    public void updateRecipeName(Integer recipeId, String newName) {
        recipeRepository.findById(recipeId).ifPresentOrElse(
                re -> updateRecipeAndCorrespondingFoodName(recipeId, re.getName(), newName),
                () -> {
                    throw new ResourceNotFoundException(RECIPE_NOT_FOUND.getText());
                });
    }

    private void updateRecipeAndCorrespondingFoodName(Integer recipeId, String oldName, String newName) {
        if(!oldName.equals(newName)) {
            recipeRepository.updateRecipeName(recipeId, newName);
            foodRepository.findByNameEqualsIgnoreCaseAndBrandIsNullAndIsCurrentTrue(oldName)
                    .ifPresent(f -> foodRepository.updateFoodName(f.getId(), newName));
        }
    }

    public void updateRecipeDescription(Integer recipeId, String newDescription) {
        recipeRepository.findById(recipeId).ifPresentOrElse(
                re -> {
                    var oldDescription = re.getDescription();
                    if (!Objects.equals(oldDescription, newDescription)) {
                        recipeRepository.updateRecipeDescription(recipeId, newDescription);
                    }
                },
                () -> {
                    throw new ResourceNotFoundException(RECIPE_NOT_FOUND.getText());
                });
    }

    public void updateRecipeIngredients(Integer recipeId, UpdateRecipeIngredients updateRecipeIngredients) {
        validator.validateIngredientsForUpdateRecipe(updateRecipeIngredients);
        recipeRepository.findById(recipeId).ifPresentOrElse(
                re -> {
                    updateIngredients(updateRecipeIngredients.updateIngredients());
                    deleteIngredients(updateRecipeIngredients.deleteIngredients());
                    addIngredients(recipeId, updateRecipeIngredients.addIngredients());
                    if (Objects.nonNull(updateRecipeIngredients.cookedWeight())) {
                        re.setCookedWeight(updateRecipeIngredients.cookedWeight());
                    }
                    regenerateCorrespondingFood(recipeId, re);
                },
                () -> {
                    throw new ResourceNotFoundException(RECIPE_NOT_FOUND.getText());
                });
    }

    private void updateIngredients(List<UpdateRecipeIngredient> updatedIngredientsDTOs) {
        if (!isEmpty(updatedIngredientsDTOs)) {
            List<RecipeIngredientEntity> recipeIngredientEntities = recipeIngredientRepository.findAllById(updatedIngredientsDTOs.stream().map(UpdateRecipeIngredient::id).toList());
            Map<Integer, UpdateRecipeIngredient> updateIngredientDTOMap = updatedIngredientsDTOs.stream().collect(Collectors.toMap(UpdateRecipeIngredient::id, dto -> dto));
            Map<Integer, RecipeIngredientEntity> recipeIngredientEntityMap = recipeIngredientEntities.stream().collect(Collectors.toMap(RecipeIngredientEntity::getId, entity -> entity));
            updateIngredientDTOMap.forEach((k, v) -> recipeMapper.updateIngredientFromDto(v, recipeIngredientEntityMap.get(k)));
            recipeIngredientRepository.saveAll(recipeIngredientEntityMap.values());
        }
    }

    private void deleteIngredients(List<Integer> ingredientsToDelete) {
        if (!isEmpty(ingredientsToDelete)) {
            recipeIngredientRepository.deleteAllByIdInBatch(ingredientsToDelete);
        }
    }

    private void addIngredients(Integer recipeId, List<AddRecipeIngredient> addRecipeIngredients) {
        if(!isEmpty(addRecipeIngredients)) {
            recipeIngredientRepository.saveAll(addRecipeIngredients.stream().map(i -> recipeMapper.toRecipeIngredientEntity(i, recipeId)).toList());
        }
    }

    private void regenerateCorrespondingFood(Integer recipeId, RecipeEntity re) {
        List<RecipeIngredientEntity> newIngredients = recipeIngredientRepository.findAllByRecipeId(recipeId);
        FoodEntity oldFood = foodRepository.findByNameEqualsIgnoreCaseAndBrandIsNullAndIsCurrentTrue(re.getName()).orElseThrow(() -> new ResourceNotFoundException(FOOD_BY_NAME_NOT_FOUND.getText()));
        FoodEntity newFood = getRecipeFood(re.getName(), newIngredients, re.getCookedWeight());
        foodRepository.save(newFood.setId(oldFood.getId())
                .setCreatedDate(LocalDate.now()))
                .setIsCurrent(Boolean.TRUE);
    }

    public void deleteRecipe(Integer recipeId) {
        Optional<RecipeEntity> recipe = recipeRepository.findById(recipeId);
        recipe.ifPresentOrElse(recipeRepository::delete,
                () -> {
                    throw new ResourceNotFoundException(RECIPE_NOT_FOUND.getText());
                });
        recipeIngredientRepository.deleteAllByRecipeId(recipeId);
        foodRepository.findByNameEqualsIgnoreCaseAndBrandIsNullAndIsCurrentTrue(recipe.get().getName())
                .ifPresent(foodRepository::delete);
    }
}
