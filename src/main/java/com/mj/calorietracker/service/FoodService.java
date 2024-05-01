package com.mj.calorietracker.service;

import com.mj.calorietracker.dto.Food;
import com.mj.calorietracker.dto.LocalResourceBridge;
import com.mj.calorietracker.dto.add.AddFood;
import com.mj.calorietracker.dto.add.AddLocalFood;
import com.mj.calorietracker.dto.update.UpdateFood;
import com.mj.calorietracker.exception.ConflictException;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.specification.FoodSpecifications;
import com.mj.calorietracker.service.validator.FoodValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_ALREADY_DELETED;
import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_NOT_FOUND;
import static com.mj.calorietracker.mapper.FoodMapper.foodMapper;

@Service
@AllArgsConstructor
@Transactional
public class FoodService {

    private FoodRepository foodRepository;
    private FoodValidator validator;

    public List<Food> getAllFoods() {
        return foodRepository.findAll().stream().map(foodMapper::toFood).toList();
    }

    public List<Food> findBySearchString(String searchString) {
        Set<String> searchValues = Arrays.stream(searchString.split("\\s+")).collect(Collectors.toSet());
        Specification<FoodEntity> spec = FoodSpecifications.searchInFoods(searchValues);
        return foodRepository.findAll(spec).stream().map(foodMapper::toFood)
                .sorted(Comparator.comparingInt(food -> countMatchedValues((Food) food, searchValues)).reversed()).toList();
    }

    public Integer addFood(AddFood food) {
        validator.validateFood(food);

        return foodRepository.save(foodMapper.toEntity(food)).getId();
    }

    public List<LocalResourceBridge> addFoodList(List<AddLocalFood> foods) {
        validator.validateFoods(foods);

        Set<AddLocalFood> uniqueFoods = new TreeSet<>(AddFood.comparator());
        uniqueFoods.addAll(foods);
        return uniqueFoods.stream()
                .map(food -> {
                    Integer foodId = foodRepository.save(foodMapper.toEntity(food)).getId();
                    return new LocalResourceBridge(foodId, food.getLocalId());
                }).toList();
    }

    public Integer updateFood(UpdateFood food) {
        softDeleteFood(food.getId());
        validator.validateFood(food);

        return foodRepository.save(foodMapper.toEntity(food)).getId();
    }

    public void softDeleteFood(Integer foodId) {
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
