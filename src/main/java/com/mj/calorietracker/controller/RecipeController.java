package com.mj.calorietracker.controller;

import com.mj.calorietracker.dto.Recipe;
import com.mj.calorietracker.dto.RecipeIngredient;
import com.mj.calorietracker.dto.ResourceId;
import com.mj.calorietracker.dto.add.AddRecipe;
import com.mj.calorietracker.dto.update.UpdateRecipeDescription;
import com.mj.calorietracker.dto.update.UpdateRecipeIngredients;
import com.mj.calorietracker.service.RecipeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/find-all")
    public List<Recipe> getAllRecipes() {
        return recipeService.findAllRecipes();
    }

    @GetMapping("/find-by-name/{name}")
    public List<Recipe> findRecipesByName(@PathVariable @Valid @Size(min = 1, max = 100) String name) {
        return recipeService.findAllRecipesByName(name);
    }

    @GetMapping("/{recipeId}/ingredients")
    public List<RecipeIngredient> getRecipeIngredients(@PathVariable Integer recipeId) {
        return recipeService.findRecipeIngredients(recipeId);
    }

    @PostMapping("/add")
    public ResourceId addRecipe(@Valid @RequestBody AddRecipe addRecipe) {
        return new ResourceId(recipeService.addRecipe(addRecipe));
    }

    @PutMapping("/{recipeId}/update-name/{name}")
    public ResponseEntity<Object> updateRecipeName(@PathVariable Integer recipeId, @Size(min = 1, max = 100) @PathVariable String name) {
        recipeService.updateRecipeName(recipeId, name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{recipeId}/update-description")
    public ResponseEntity<Object> updateRecipeDescription(@PathVariable Integer recipeId, @Valid @RequestBody UpdateRecipeDescription updateRecipeDescription) {
        recipeService.updateRecipeDescription(recipeId, updateRecipeDescription.description());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{recipeId}/update-ingredients")
    public ResponseEntity<Object> updateRecipeIngredients(@PathVariable Integer recipeId, @Valid @RequestBody UpdateRecipeIngredients updateRecipeIngredients) {
        recipeService.updateRecipeIngredients(recipeId, updateRecipeIngredients);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}/delete")
    public HttpStatus deleteRecipe(@PathVariable Integer recipeId) {
        recipeService.deleteRecipe(recipeId);
        return HttpStatus.OK;
    }
}
