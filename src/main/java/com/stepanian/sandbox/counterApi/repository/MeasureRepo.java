package com.stepanian.sandbox.counterApi.repository;

import com.stepanian.sandbox.counterApi.entity.Measure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureRepo extends JpaRepository<Measure, Integer> {
}
