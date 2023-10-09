package com.mj.calorietracker.controller;

import com.mj.calorietracker.dto.FoodUnit;
import com.mj.calorietracker.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/units")
public class UnitController {
    private final UnitService unitService;

    @GetMapping("/food/{foodId}")
    public List<FoodUnit> getUnitsForFood(@PathVariable UUID foodId) {
        return unitService.findUnitsForFood(foodId);
    }
}
