package com.mj.calorietracker.controller;

import com.mj.calorietracker.model.add.AddDiaryEntryAndFood;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.update.UpdateDiaryEntry;
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
    public List<UpdateDiaryEntry> findAllDiaryEntries() {
        return diaryService.findAllDiaryEntries();
    }

    @GetMapping("/{entryDate}")
    public List<UpdateDiaryEntry> findDiaryEntriesByDate(@PathVariable LocalDate entryDate) {
        return diaryService.findDiaryEntriesByDate(entryDate);
    }

    @PostMapping("/add")
    public HttpStatus addEntry(@Valid @RequestBody AddDiaryEntry addDiaryEntry) {
        diaryService.addDiaryEntry(addDiaryEntry);
        return HttpStatus.OK;
    }
    @PostMapping("/add-with-food")
    public HttpStatus addDiaryEntryAndFood(@Valid @RequestBody AddDiaryEntryAndFood addDiaryEntryAndFood) {
        diaryService.addDiaryEntryAndFood(addDiaryEntryAndFood);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{diaryId}")
    public HttpStatus deleteDiaryEntry(@PathVariable UUID diaryId) {
        diaryService.deleteDiaryEntry(diaryId);
        return HttpStatus.OK;
    }

}
