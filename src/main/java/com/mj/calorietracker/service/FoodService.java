package com.mj.calorietracker.service;

import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.dao.FoodEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final ModelMapper modelMapper;

    public final List<Food> getAllFoods() {
        return foodRepository.findAll().stream().map(this::toModel).toList();
    }

    public final void addFood(Food food) {
        FoodEntity foodEntity = toEntity(food);
        if(StringUtils.hasLength(food.getBarcode())) {
            foodRepository.findByBarcode(food.getBarcode()).ifPresent(existingFood -> foodEntity.setId(existingFood.getId()));
        }

        foodRepository.save(foodEntity);
    }

    private Food toModel(FoodEntity foodEntity) {
        return modelMapper.map(foodEntity, Food.class);
    }
    private FoodEntity toEntity(Food food) {
        return modelMapper.map(food, FoodEntity.class).setCreatedDate(LocalDate.now());
    }
}
