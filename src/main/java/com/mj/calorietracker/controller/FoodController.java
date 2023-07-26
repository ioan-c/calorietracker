package com.mj.calorietracker.controller;

import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.ResourceId;
import com.mj.calorietracker.model.add.AddFood;
import com.mj.calorietracker.model.update.UpdateFood;
import com.mj.calorietracker.service.FoodService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/update")
    public ResourceId updateFood(@Valid @RequestBody UpdateFood food) {
        return new ResourceId(foodService.updateFood(food));
    }

    @DeleteMapping("/delete/{foodId}")
    public HttpStatus deleteFood(@PathVariable UUID foodId) {
        foodService.softDeleteFood(foodId);
        return HttpStatus.OK;
    }
}
