package com.stepanian.sandbox.counterApi.service;

import com.stepanian.sandbox.counterApi.entity.Category;
import com.stepanian.sandbox.counterApi.entity.Exercise;
import com.stepanian.sandbox.counterApi.entity.Measure;
import com.stepanian.sandbox.counterApi.repository.CategoryRepo;
import com.stepanian.sandbox.counterApi.repository.ExerciseRepo;
import com.stepanian.sandbox.counterApi.repository.MeasureRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryService {

    private final CategoryRepo categoryRepo;
    private final MeasureRepo measureRepo;
    private final ExerciseRepo exerciseRepo;

    public void saveNewCategory(Category category) {
        categoryRepo.save(category);
    }

    public void deleteCategory(Integer id) {
        categoryRepo.deleteById(id);
    }

    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    public List<Measure> findAllMeasures() {
        return measureRepo.findAll();
    }

    public void saveMeasure(Measure measure) {
        measureRepo.save(measure);
    }

    public List<Exercise> getExercisesByCategory(Integer category) {
        return exerciseRepo.findAllByCategory_Id(category);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepo.findAll();
    }

    public void saveNewExercise(Exercise exercise) {
        exerciseRepo.save(exercise);
    }
}
