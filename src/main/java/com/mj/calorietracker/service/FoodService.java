package com.mj.calorietracker.service;

import com.mj.calorietracker.exception.FoodAlreadyExistsException;
import com.mj.calorietracker.mapper.FoodMapper;
import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.add.AddFood;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.specification.FoodSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_BARCODE_CONFLICT;
import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_NAME_BRAND_CONFLICT;

@Service
@AllArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    public final List<Food> getAllFoods() {
        return foodRepository.findAll().stream().map(foodMapper::toModel).toList();
    }

    public final List<Food> findBySearchString(String searchString) {
        Set<String> searchValues = Set.of(searchString.split("\\s+"));
        Specification<FoodEntity> spec = FoodSpecifications.searchFoods(searchValues);
        return foodRepository.findAll(spec).stream().map(foodMapper::toModel)
                .sorted(Comparator.comparingInt(food -> countMatchedValues((Food) food, searchValues)).reversed()).toList();
    }

    public final void addFood(AddFood food) {
        FoodEntity foodEntity = foodMapper.toEntity(food);
        validate(food);

        foodRepository.save(foodEntity);
    }

    private void validate(AddFood food) {
        if(StringUtils.hasLength(food.getBarcode())) {
            foodRepository.findByBarcode(food.getBarcode()).ifPresent(f -> {
                throw new FoodAlreadyExistsException(foodMapper.toModel(f), FOOD_BARCODE_CONFLICT.getMessage());
            });
        }
        foodRepository.findByNameEqualsIgnoreCase(food.getName()).ifPresent(f -> {
            if(Objects.equals(f.getBrand(), food.getBrand())){
                throw new FoodAlreadyExistsException(foodMapper.toModel(f), FOOD_NAME_BRAND_CONFLICT.getMessage());
            }
        });
    }

    private int countMatchedValues(Food food, Set<String> searchValues) {
        Set<String> concatenatedColumns = Stream.of(food.getName(), food.getBrand())
                .flatMap(column -> Arrays.stream(column.split("\\s+")))
                .collect(Collectors.toSet());

        return (int) searchValues.stream()
                .filter(concatenatedColumns::contains)
                .count();
    }
}
