package com.mj.calorietracker.service;

import com.mj.calorietracker.dto.FoodUnit;
import com.mj.calorietracker.repository.FoodUnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mj.calorietracker.mapper.UnitMapper.unitMapper;

@Service
@AllArgsConstructor
public class UnitService {

    private final FoodUnitRepository foodUnitRepository;

    public final List<FoodUnit> findUnitsForFood(final Integer foodId) {
        return foodUnitRepository.findAllByFoodIdIsNullOrFoodId(foodId).stream()
                .map(unitMapper::toDTO)
                .toList();
    }
}
