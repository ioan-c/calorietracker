package com.mj.calorietracker.mapper;

import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.add.AddFood;
import com.mj.calorietracker.repository.dao.FoodEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FoodMapper {

    private final ModelMapper modelMapper;

    public Food toModel(FoodEntity foodEntity) {
        return modelMapper.map(foodEntity, Food.class);
    }
    public FoodEntity toEntity(AddFood addFood) {
        return modelMapper.map(addFood, FoodEntity.class)
                .setId(null)
                .setCreatedDate(LocalDate.now());
    }
}
