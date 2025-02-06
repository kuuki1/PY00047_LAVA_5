package com.asm.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.asm.ecommerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    public Boolean existsByName(String name);
}
