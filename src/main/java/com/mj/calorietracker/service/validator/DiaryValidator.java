package com.mj.calorietracker.service.validator;

import com.mj.calorietracker.dto.add.AddDiaryEntry;
import com.mj.calorietracker.dto.add.AddLocalDiaryEntry;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.exception.model.ErrorInfoForList;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.FoodUnitRepository;
import com.mj.calorietracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static com.mj.calorietracker.enums.ExceptionMessages.FOOD_NOT_FOUND;
import static com.mj.calorietracker.enums.ExceptionMessages.UNIT_NOT_FOUND;

@Component
@AllArgsConstructor
public class DiaryValidator extends Validator {

    private UserService userService;
    private FoodRepository foodRepository;
    private FoodUnitRepository foodUnitRepository;

    public void validateDiaryEntry(AddDiaryEntry addDiaryEntry) {
        validateFoodExists(addDiaryEntry.getFoodId());
        validateUserExists(addDiaryEntry.getUserId());
        validateUnitAvailableForFood(addDiaryEntry.getFoodId(), addDiaryEntry.getFoodUnitId());
    }

    public void validateDiaryEntries(List<AddLocalDiaryEntry> addDiaryEntryList) {
        List<ErrorInfoForList> errorInfoList = IntStream.range(0, addDiaryEntryList.size())
                .mapToObj(i -> {
                    AddLocalDiaryEntry addEntry = addDiaryEntryList.get(i);
                    try {
                        validateDiaryEntry(addEntry);
                        return null;
                    } catch (ResourceNotFoundException ex) {
                        return new ErrorInfoForList(ex.getMessage(), addEntry.getLocalId(), i);
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        digestErrorList(errorInfoList);
    }

    private void validateFoodExists(Integer foodId) {
        foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException(FOOD_NOT_FOUND.getText()));
    }

    public void validateUserExists(String userId) {
        userService.findByUsername(userId);
    }

    private void validateUnitAvailableForFood(Integer foodId, Integer foodUnitId) {
        if(!foodUnitRepository.existsForFood(foodId, foodUnitId)) {
            throw new ResourceNotFoundException(UNIT_NOT_FOUND.getText());
        }
    }
}
