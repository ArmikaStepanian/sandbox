package com.stepanian.sandbox.counterApi.repository;

import com.stepanian.sandbox.counterApi.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepo extends JpaRepository<Exercise, Integer> {

    List<Exercise> findAllByCategory_Id(Integer category);
}
