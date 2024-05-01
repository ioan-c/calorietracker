package com.mj.calorietracker.service.validator;

import com.mj.calorietracker.dto.add.AddFood;
import com.mj.calorietracker.dto.add.AddLocalFood;
import com.mj.calorietracker.exception.ExistingResourceException;
import com.mj.calorietracker.exception.model.ErrorInfoForList;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.specification.FoodSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_BARCODE_CONFLICT;
import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_NAME_BRAND_CONFLICT;
import static com.mj.calorietracker.mapper.FoodMapper.foodMapper;

@Component
@AllArgsConstructor
public class FoodValidator  extends Validator {

    private FoodRepository foodRepository;

    public void validateFood(AddFood food) {
        if(StringUtils.hasLength(food.getBarcode())) {
            foodRepository.findByBarcodeAndIsCurrentTrue(food.getBarcode()).ifPresent(f -> {
                throw new ExistingResourceException(foodMapper.toFood(f), FOOD_BARCODE_CONFLICT.getText());
            });
        }
        Specification<FoodEntity> spec = FoodSpecifications.findIfItWillDuplicate(food.getName(), food.getBrand());
        foodRepository.findOne(spec).ifPresent(f -> {
            throw new ExistingResourceException(foodMapper.toFood(f), FOOD_NAME_BRAND_CONFLICT.getText());
        });
    }

    public void validateFoods(List<AddLocalFood> foods) {
        List<ErrorInfoForList> errorInfoList = IntStream.range(0, foods.size())
                .mapToObj(i -> getErrorInfoForList(foods, i))
                .filter(Objects::nonNull)
                .toList();

        digestErrorList(errorInfoList);
    }

    private ErrorInfoForList getErrorInfoForList(List<AddLocalFood> foods, int i) {
        AddLocalFood addFood = foods.get(i);
        try {
            validateFood(addFood);
            return null;
        } catch (ExistingResourceException ex) {
            return new ErrorInfoForList(ex.getMessage(), addFood.getLocalId(), i, ex.getExistingResource());
        }
    }
}
