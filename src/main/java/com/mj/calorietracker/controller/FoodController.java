package com.mj.calorietracker.controller;

import com.mj.calorietracker.dto.Food;
import com.mj.calorietracker.dto.LocalResourceBridge;
import com.mj.calorietracker.dto.ResourceId;
import com.mj.calorietracker.dto.add.AddFood;
import com.mj.calorietracker.dto.add.AddLocalFood;
import com.mj.calorietracker.dto.update.UpdateFood;
import com.mj.calorietracker.service.FoodService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping(path = "/foods")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/find-all")
    public List<Food> findAllFoods() {
        return foodService.getAllFoods();
    }

    @GetMapping("/find-by/{searchString}")
    public List<Food> findFoodsBy(@PathVariable @Valid @Size(min = 1, max = 100) String searchString){
        return foodService.findBySearchString(searchString);
    }

    @PostMapping("/add")
    public ResourceId addFood(@Valid @RequestBody AddFood food) {
        return new ResourceId(foodService.addFood(food));
    }

    @PostMapping("/add-list")
    public List<LocalResourceBridge> addFoodList(@RequestBody @NotEmpty List<@Valid AddLocalFood> foodList) {
        return foodService.addFoodList(foodList);
    }

    @PostMapping("/update")
    public ResourceId updateFood(@Valid @RequestBody UpdateFood food) {
        return new ResourceId(foodService.updateFood(food));
    }

    @DeleteMapping("/delete/{foodId}")
    public HttpStatus deleteFood(@PathVariable Integer foodId) {
        foodService.softDeleteFood(foodId);
        return HttpStatus.OK;
    }
}
