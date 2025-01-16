package com.ptms.ptms.repository;

import com.ptms.ptms.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
