package com.mj.calorietracker.controller;

import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.service.FoodService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/foods")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("find-all")
    public List<Food> findAllFoods() {
        return foodService.getAllFoods();
    }

    @PostMapping("/add")
    public HttpStatus addFood(@Valid @RequestBody Food food) {
        foodService.addFood(food);
        return HttpStatus.OK;
    }
}
