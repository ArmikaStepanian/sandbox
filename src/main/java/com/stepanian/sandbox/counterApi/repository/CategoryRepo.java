package com.stepanian.sandbox.counterApi.repository;

import com.stepanian.sandbox.counterApi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
