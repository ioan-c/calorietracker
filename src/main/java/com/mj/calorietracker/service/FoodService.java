package com.mj.calorietracker.service;

import com.mj.calorietracker.exception.FoodAlreadyExistsException;
import com.mj.calorietracker.mapper.FoodMapper;
import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.add.AddFood;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.dao.FoodEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public final List<Food> getAllFoods() {
        return foodRepository.findAll().stream().map(foodMapper::toModel).toList();
    }

    public final void addFood(AddFood food) {
        FoodEntity foodEntity = foodMapper.toEntity(food);
        validate(food);

        foodRepository.save(foodEntity);
    }

    private void validate(AddFood food) {
        if(StringUtils.hasLength(food.getBarcode())) {
            foodRepository.findByBarcode(food.getBarcode()).ifPresent(f -> {
                throw new FoodAlreadyExistsException(foodMapper.toModel(f), "Food with this barcode already exists!");
            });
        }
        foodRepository.findByNameEqualsIgnoreCase(food.getName()).ifPresent(f -> {
            if(Objects.equals(f.getBrand(), food.getBrand())){
                throw new FoodAlreadyExistsException(foodMapper.toModel(f), "Food with this name and brand already exists!");
            }
        });
    }
}
