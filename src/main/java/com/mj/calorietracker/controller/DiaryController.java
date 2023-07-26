package com.mj.calorietracker.controller;

import com.mj.calorietracker.model.MealDiaryEntries;
import com.mj.calorietracker.model.ResourceId;
import com.mj.calorietracker.model.add.AddDiaryEntryAndFood;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.DiaryEntry;
import com.mj.calorietracker.service.DiaryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/diary-entries")
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping
    public List<DiaryEntry> findAllDiaryEntries() {
        return diaryService.findAllDiaryEntries();
    }

    @GetMapping("/{userId}/{entryDate}")
    public List<MealDiaryEntries> findDiaryEntriesByUserIdAndDate(@PathVariable UUID userId, @PathVariable LocalDate entryDate) {
        return diaryService.findDiaryEntriesByUserIdAndDate(userId, entryDate);
    }

    @PostMapping("/add")
    public ResourceId addEntry(@Valid @RequestBody AddDiaryEntry addDiaryEntry) {
        return new ResourceId(diaryService.addDiaryEntry(addDiaryEntry));
    }

    @PostMapping("/add-with-food")
    public ResourceId addDiaryEntryAndFood(@Valid @RequestBody AddDiaryEntryAndFood addDiaryEntryAndFood) {
        return new ResourceId(diaryService.addDiaryEntryAndFood(addDiaryEntryAndFood));
    }

    @DeleteMapping("/delete/{diaryId}")
    public HttpStatus deleteDiaryEntry(@PathVariable UUID diaryId) {
        diaryService.deleteDiaryEntry(diaryId);
        return HttpStatus.OK;
    }

}
