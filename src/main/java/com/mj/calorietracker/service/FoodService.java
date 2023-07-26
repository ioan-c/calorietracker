package com.mj.calorietracker.service;

import com.mj.calorietracker.exception.ConflictException;
import com.mj.calorietracker.exception.ExistingResourceException;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.mapper.FoodMapper;
import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.add.AddFood;
import com.mj.calorietracker.model.update.UpdateFood;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.specification.FoodSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mj.calorietracker.enums.ExceptionMessages.*;

@Service
@AllArgsConstructor
@Transactional
public class FoodService {

    private FoodRepository foodRepository;
    private FoodMapper foodMapper;

    public List<Food> getAllFoods() {
        return foodRepository.findAll().stream().map(foodMapper::toModel).toList();
    }

    public List<Food> findBySearchString(String searchString) {
        Set<String> searchValues = Set.of(searchString.split("\\s+"));
        Specification<FoodEntity> spec = FoodSpecifications.searchInFoods(searchValues);
        return foodRepository.findAll(spec).stream().map(foodMapper::toModel)
                .sorted(Comparator.comparingInt(food -> countMatchedValues((Food) food, searchValues)).reversed()).toList();
    }

    public UUID addFood(AddFood food) {
        validate(food);

        return foodRepository.save(foodMapper.toEntity(food)).getId();
    }

    public UUID updateFood(UpdateFood food) {
        softDeleteFood(food.getId());
        validate(food);

        return foodRepository.save(foodMapper.toEntity(food)).getId();
    }

    public void softDeleteFood(UUID foodId) {
        foodRepository.findById(foodId).ifPresentOrElse(
                this::processSoftDeletion,
                () -> {
                    throw new ResourceNotFoundException(FOOD_NOT_FOUND.getText());
                }
        );
    }

    private int countMatchedValues(Food food, Set<String> searchValues) {
        Set<String> concatenatedColumns = Stream.of(food.getName(), food.getBrand())
                .filter(Objects::nonNull)
                .flatMap(column -> Arrays.stream(column.split("\\s+")))
                .collect(Collectors.toSet());

        return (int) searchValues.stream()
                .filter(concatenatedColumns::contains)
                .count();
    }

    private void validate(AddFood food) {
        if(StringUtils.hasLength(food.getBarcode())) {
            foodRepository.findByBarcodeAndIsCurrentTrue(food.getBarcode()).ifPresent(f -> {
                throw new ExistingResourceException(foodMapper.toModel(f), FOOD_BARCODE_CONFLICT.getText());
            });
        }
        Specification<FoodEntity> spec = FoodSpecifications.findIfItWillDuplicate(food.getName(), food.getBrand());
        foodRepository.findOne(spec).ifPresent(f -> {
            throw new ExistingResourceException(foodMapper.toModel(f), FOOD_NAME_BRAND_CONFLICT.getText());
        });
    }

    private void processSoftDeletion(FoodEntity food) {
        Optional.of(food)
                .filter(FoodEntity::getIsCurrent)
                .ifPresentOrElse(
                        currentFood -> foodRepository.softDeleteFoodById(currentFood.getId()),
                        () -> {
                            throw new ConflictException(FOOD_ALREADY_DELETED.getText());
                        }
                );
    }
}
