package com.mj.calorietracker.controller;

import com.mj.calorietracker.dto.DiaryEntry;
import com.mj.calorietracker.dto.LocalResourceBridge;
import com.mj.calorietracker.dto.MealDiaryEntries;
import com.mj.calorietracker.dto.ResourceId;
import com.mj.calorietracker.dto.add.AddDiaryEntry;
import com.mj.calorietracker.dto.add.AddLocalDiaryEntry;
import com.mj.calorietracker.service.DiaryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
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
    public List<MealDiaryEntries> findDiaryEntriesByUserIdAndDate(@PathVariable String userId, @PathVariable LocalDate entryDate) {
        return diaryService.findDiaryEntriesByUserIdAndDate(userId, entryDate);
    }

    @PostMapping("/add")
    public ResourceId addEntry(@Valid @RequestBody AddDiaryEntry addDiaryEntry) {
        return new ResourceId(diaryService.addDiaryEntry(addDiaryEntry));
    }

    @PostMapping("/add/list")
    public List<LocalResourceBridge> addDiaryEntriesList(@RequestBody @NotEmpty List<@Valid AddLocalDiaryEntry> addDiaryEntryList) {
        return diaryService.addDiaryEntriesList(addDiaryEntryList);
    }

    @DeleteMapping("/delete/{diaryId}")
    public HttpStatus deleteDiaryEntry(@PathVariable Integer diaryId) {
        diaryService.deleteDiaryEntry(diaryId);
        return HttpStatus.OK;
    }

    @PostMapping("/delete/list")
    public HttpStatus deleteDiaryEntryList(@RequestBody @NotEmpty List<@NotNull Integer> deleteDiaryEntryList) {
        diaryService.deleteDiaryEntryBatch(deleteDiaryEntryList);
        return HttpStatus.OK;
    }

}
