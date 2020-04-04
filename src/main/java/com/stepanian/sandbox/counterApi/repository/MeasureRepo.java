package com.stepanian.sandbox.counterApi.repository;

import com.stepanian.sandbox.counterApi.entity.Measure;
import org.springframework.data.repository.CrudRepository;

public interface MeasureRepo extends CrudRepository<Measure, Integer> {
}
